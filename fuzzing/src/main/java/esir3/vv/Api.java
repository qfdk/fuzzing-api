package esir3.vv;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import esir3.vv.Tools.OperationType;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;
import io.swagger.parser.SwaggerParser;

/**
 * Created by qfdk on 19/10/2016.
 */
@Path("v1")
public class Api {

	private FuzzingData data;
	private String base_url = Tools.readConf("conf/conf.xml").getProperty("base_url");

	private static Logger logger = LoggerFactory.getLogger(Api.class);


	public Api() {
		data = FuzzingData.getInstance();
		logger.info("[+] Fuzzing api init...");
	}

	/**
	 * http://localhost:8080/api/v1/getStatus
	 *
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("getStatus")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStatus() throws Exception {
		return Response.status(200).entity("{status:ok,msg:api works!}").build();
	}

	/**
	 * http://localhost:8080/api/v1/analyse
	 *
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("analyse")
	@Produces(MediaType.APPLICATION_JSON)
	public Response analyse() throws Exception {

		List<UrlInfo> urls = data.getUrlInfos();

		String hostname = data.getHostname();
		String contentType = data.getContentType();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("hostname", hostname);
		jsonObject.put("contentType", contentType);

		// add del chang post put ..
		for (UrlInfo url : urls) {
			if(url.getOperationType().equals(Tools.OperationType.GET.toString()))
			{
				url.setReponseCode(Tools.sendGet(url.getLink()).get(0));
				url.setReponseBody(Tools.sendGet(url.getLink()).get(1));
			}
			if(url.getOperationType().equals(Tools.OperationType.POST.toString()))
			{
				url.setReponseCode(Tools.sendPost(url.getLink(), url.getParameters()));
			}
			if(url.getOperationType().equals(Tools.OperationType.DELETE.toString()))
			{
				url.setReponseCode(Tools.sendDel(url.getLink()));
			}
			if(url.getOperationType().equals(Tools.OperationType.PUT.toString()))
			{
				url.setReponseCode(Tools.sendPut(url.getLink(),url.getParameters()));
			}

			if(url.getCodes().contains("default"))
			{
				url.setValided(true);
			}
		}

		jsonObject.put("urls", urls);

		logger.info("Result analyse + \n\t"+jsonObject);

		return Response.status(200).entity(jsonObject.toString()).build();
	}

	@GET
	@Path("saveConf")
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveConf(@QueryParam("path") String path) throws Exception {
		Map<String, String> map = new TreeMap<>();
		map.put("base_url", "http://localhost:8080");
		Tools.saveConf(map, path);
		JSONObject ret = new JSONObject();
		ret.put("msg", Tools.readConf(path).getProperty("base_url"));
		return Response.status(200).entity(ret.toString()).build();
	}

	/**
	 * http://localhost:8080/api/v1/getPath
	 *
	 * @throws Exception
	 */
	@GET
	@Path("getPath")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPath(@QueryParam("url") String url) throws Exception {
		Swagger swagger = new SwaggerParser().read(url);
		List<UrlInfo> urlInfos = new ArrayList<>();

		if (swagger != null) {
			Map<String, io.swagger.models.Path> paths = swagger.getPaths();

			for (String currentPathName : paths.keySet()) {
				io.swagger.models.Path currentPath = paths.get(currentPathName);
				logger.debug("getPath : current path ==>"+currentPathName);

				UrlInfo dataCurrentPath = new UrlInfo(); 

				if (currentPath.getGet() != null) {
					dataCurrentPath.setOperationType(OperationType.GET.toString());
					dataCurrentPath.setCodes(paths.get(currentPathName).getGet().getResponses().keySet());

					dataCurrentPath.setLink(swagger.getSchemes().get(0).toString().toLowerCase() + "://" + swagger.getHost() + swagger.getBasePath() + currentPathName);

					if (!currentPath.getGet().getParameters().isEmpty()) {
						for (Parameter parameter : currentPath.getGet().getParameters()) {
							String linkWithParam = dataCurrentPath.getLink(); 
							dataCurrentPath.setLink( linkWithParam.replace("{" + parameter.getName() + "}", Tools.generateTestData(parameter)));
						}
					} 
					urlInfos.add(dataCurrentPath);
				}

				if (currentPath.getPost() != null) {
					dataCurrentPath = new UrlInfo();

					dataCurrentPath.setOperationType(OperationType.POST.toString());
					dataCurrentPath.setCodes(paths.get(currentPathName).getPost().getResponses().keySet());
					String linkBase = swagger.getSchemes().get(0).toString().toLowerCase() + "://" + swagger.getHost() + swagger.getBasePath() + currentPathName;


					Map<String,String> postParamsContent=new TreeMap<>();
					
					List<Parameter> listParams = currentPath.getPost().getParameters();
					for (Parameter param : listParams){
						linkBase = linkBase.replace("{" + param.getName() + "}", Tools.generateTestData(param));
						if(param.getIn().equals("formData"))
						{
							postParamsContent.put(param.getName(),Tools.generateTestData(param));
						}
						logger.debug("POST :  "+postParamsContent.toString()+ " for path "+linkBase);
					}

					dataCurrentPath.setLink(linkBase);
					dataCurrentPath.setParameters(postParamsContent);
					urlInfos.add(dataCurrentPath);
				}


				if (currentPath.getDelete() != null) {
					dataCurrentPath = new UrlInfo();
					dataCurrentPath.setOperationType(OperationType.DELETE.toString());
					dataCurrentPath.setCodes(paths.get(currentPathName).getGet().getResponses().keySet());

					dataCurrentPath.setLink(swagger.getSchemes().get(0).toString().toLowerCase() + "://" + swagger.getHost() + swagger.getBasePath() + currentPathName);

					if (!currentPath.getDelete().getParameters().isEmpty()) {
						for (Parameter parameter : currentPath.getDelete().getParameters()) {
							String linkBase = dataCurrentPath.getLink().replace("{" + parameter.getName() + "}", Tools.generateTestData(parameter));
							dataCurrentPath.setLink(linkBase);
						}
					} 
					urlInfos.add(dataCurrentPath);
				}

				if (currentPath.getPut() != null) {
					dataCurrentPath = new UrlInfo();
					dataCurrentPath.setOperationType(OperationType.PUT.toString());
					dataCurrentPath.setCodes(paths.get(currentPathName).getPut().getResponses().keySet());
					
					String linkBase = swagger.getSchemes().get(0).toString().toLowerCase() + "://" + swagger.getHost() + swagger.getBasePath() + currentPathName;

					List<Parameter> listParams = currentPath.getPut().getParameters();
					Map<String,String> params=new TreeMap<>();

					for (Parameter param : listParams){
						linkBase = linkBase.replace("{" + param.getName() + "}", Tools.generateTestData(param));
						params.put(param.getName(),Tools.generateTestData(param));
						logger.debug("PUT :  "+params.toString()+ " for path "+linkBase);
					}

					dataCurrentPath.setLink(linkBase);
					dataCurrentPath.setParameters(params);
					urlInfos.add(dataCurrentPath);
				}

			}
			data.setHostname(swagger.getHost());
			data.setUrls(urlInfos);
		} else {
			JSONObject ret = new JSONObject();
			ret.put("msg", "SWAGGER FILE IS INVALID");
			return Response.status(200).entity(ret.toString()).build();
		}
		logger.debug("getPath result : \n"+ urlInfos);
		logger.info("END of getPath :  "+urlInfos.size()+ " url send to analyse function");
		return Response.temporaryRedirect(new URI(this.base_url + "/api/v1/analyse")).build();
	}

	@GET
	@Path("testCode")
	@Produces(MediaType.APPLICATION_JSON)
	public Response testReponseCode(@QueryParam("code") String code) throws Exception {
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("code",code);
		return Response.status(Integer.parseInt(code)).entity(jsonObject.toString()).build();
	}
}

package esir3.vv;


import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;
import io.swagger.parser.SwaggerParser;

import org.apache.http.impl.execchain.MainClientExec;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import esir3.vv.Tools.OperationType;

import javax.tools.Tool;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URL;
import java.util.*;

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

		for (UrlInfo url : urls) {
			if(url.getOperationType().equals(OperationType.GET.toString()))
			{
				url.setReponseCode(Tools.sendGet(url.getLink()));
			}
			if(url.getOperationType().equals(OperationType.POST.toString()))
			{
				url.setReponseCode(Tools.sendPost(url.getLink(), url.getPostParameters()));
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
		Tools.saveConf(map, "conf/conf.xml");

		JSONObject ret = new JSONObject();
		ret.put("msg", Tools.readConf("conf/conf.xml").getProperty("base_url"));
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
		URI uri = new URI(url);
		Swagger swagger = new SwaggerParser().read(url);
		List<UrlInfo> urlInfos = new ArrayList<>();

		if (swagger != null) {
			Map<String, io.swagger.models.Path> paths = swagger.getPaths();

			for (String currentPathName : paths.keySet()) {
				io.swagger.models.Path currentPath = paths.get(currentPathName);
				
				logger.debug("getPath : current path ==>"+currentPathName);
				
				if (currentPath.getGet() != null) {
					UrlInfo dataCurrentPath = new UrlInfo();

					dataCurrentPath.setOperationType(OperationType.GET.toString());

					//	Get all responses codes
					dataCurrentPath.setCodes(paths.get(currentPathName).getGet().getResponses().keySet());

					// Get all link with data
					String linkBase = swagger.getSchemes().get(0).toString().toLowerCase() + "://" + swagger.getHost() + swagger.getBasePath() + currentPathName;

					if (!currentPath.getGet().getParameters().isEmpty()) {
						for (Parameter parameter : currentPath.getGet().getParameters()) {
							linkBase = linkBase.replace("{" + parameter.getName() + "}", Tools.generateTestData());
							dataCurrentPath.setLink(linkBase);
						}
					} else {
						dataCurrentPath.setLink(linkBase);
					}

					urlInfos.add(dataCurrentPath);
				}

				if (currentPath.getPost() != null) {
					UrlInfo dataCurrentPath = new UrlInfo();

					dataCurrentPath.setOperationType(OperationType.POST.toString());
					dataCurrentPath.setCodes(paths.get(currentPathName).getPost().getResponses().keySet());
					String linkBase = swagger.getSchemes().get(0).toString().toLowerCase() + "://" + swagger.getHost() + swagger.getBasePath() + currentPathName;
					List<Parameter> param = currentPath.getPost().getParameters();
					StringBuilder postParameters = new StringBuilder();
					for(int i=0;i<param.size();i++)
					{
						linkBase = linkBase.replace("{" + param.get(i).getName() + "}", Tools.generateTestData());
						postParameters.append(param.get(i).getName())
						.append("=")
						.append(Tools.generateTestData());

						if(i<param.size()-1)
						{
							postParameters.append("&");
						}
						logger.debug("POST :  "+postParameters+ " for path "+linkBase);
					}
					dataCurrentPath.setLink(linkBase);
					dataCurrentPath.setPostParameters(postParameters.toString());
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
}

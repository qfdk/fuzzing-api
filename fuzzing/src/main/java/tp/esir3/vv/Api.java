package tp.esir3.vv;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.parser.SwaggerParser;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
				url.setReponseCode(Tools.sendPost(url.getLink(),url.getParameters()).get(0));
				url.setReponseBody(Tools.sendPost(url.getLink(),url.getParameters()).get(1));

			}
			if(url.getOperationType().equals(Tools.OperationType.DELETE.toString()))
			{
				url.setReponseCode(Tools.sendDel(url.getLink()).get(0));
				url.setReponseBody(Tools.sendDel(url.getLink()).get(1));
			}
			if(url.getOperationType().equals(Tools.OperationType.PUT.toString()))
			{
				url.setReponseCode(Tools.sendPut(url.getLink(),url.getParameters()).get(0));
				url.setReponseBody(Tools.sendPut(url.getLink(),url.getParameters()).get(1));
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
					dataCurrentPath.setOperationType(Tools.OperationType.GET.toString());
					dataCurrentPath.setCodes(paths.get(currentPathName).getGet().getResponses().keySet());

					List<Parameter> listParams = currentPath.getGet().getParameters();
					fillData(dataCurrentPath,listParams,currentPathName,swagger );

					urlInfos.add(dataCurrentPath);
				}

				if (currentPath.getPost() != null) {
					dataCurrentPath = new UrlInfo();
					dataCurrentPath.setOperationType(Tools.OperationType.POST.toString());

					List<Parameter> listParams = currentPath.getPost().getParameters();
					fillDataPost(dataCurrentPath, listParams,currentPathName,swagger );
					urlInfos.add(dataCurrentPath);
				}


				if (currentPath.getDelete() != null) {
					dataCurrentPath = new UrlInfo();
					dataCurrentPath.setOperationType(Tools.OperationType.DELETE.toString());
					List<Parameter> listParams = currentPath.getDelete().getParameters();
					fillData(dataCurrentPath, listParams,currentPathName,swagger );
					urlInfos.add(dataCurrentPath);
				}

				if (currentPath.getPut() != null) {
					dataCurrentPath = new UrlInfo();
					dataCurrentPath.setOperationType(Tools.OperationType.PUT.toString());
					dataCurrentPath.setCodes(paths.get(currentPathName).getPut().getResponses().keySet());

					List<Parameter> listParams = currentPath.getPut().getParameters();
					fillData(dataCurrentPath, listParams,currentPathName,swagger );
					urlInfos.add(dataCurrentPath);					
				}
			} // foreach
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

	@POST
	@Path("testPost")
	@Produces(MediaType.APPLICATION_JSON)
	public Response testPost(@FormParam("name") String name,@FormParam("age")String age)  {
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("name",name);
		jsonObject.put("age",age);
		return Response.status(200).entity(jsonObject.toString()).build();
	}

	@POST
	@Path("/{param}")
	public Response postMsg(@PathParam("param") String msg) {
		String output = "POST:Jersey say : " + msg;
		return Response.status(200).entity(output).build();
	}

	/**
	 * Fill params map according to the operationType
	 * @param currentPath :
	 * @param dataCurrentPath
	 */
	public static void fillParamsMap(UrlInfo dataCurrentPath,io.swagger.models.Path currentPath) {


		List<Parameter> parameters;

		switch (dataCurrentPath.getOperationType()) {
		case "GET":
			parameters = currentPath.getGet().getParameters();
			break;
		case "POST":
			parameters = currentPath.getPost().getParameters();
			break;
		case "PUT":
			parameters = currentPath.getPut().getParameters();
			break;
		case "DELETE":
			parameters = currentPath.getDelete().getParameters();
			break;

		default:
			return ;
		}


		for (Parameter param : parameters) {
			String currentparaType = "";

			if (param instanceof io.swagger.models.parameters.PathParameter) {
				currentparaType = ((io.swagger.models.parameters.PathParameter) param).getType();

			} else if (param instanceof io.swagger.models.parameters.BodyParameter) {
				currentparaType = "body";
			}

			switch (currentparaType) {

			case "integer":
				dataCurrentPath.getParamNameAndType().put(param.getName(),"integer");
				break;

			case "number":
				dataCurrentPath.getParamNameAndType().put(param.getName(),"float");
				break;

			case "boolean":
				dataCurrentPath.getParamNameAndType().put(param.getName(),"boolean");
				break;

			case "string":
				dataCurrentPath.getParamNameAndType().put(param.getName(),"string");
				break;

			case "body":
				if(((BodyParameter) param).getSchema() !=null && ((BodyParameter) param).getSchema().getReference() != null)
				{
					dataCurrentPath.getParamNameAndType().put(param.getName(),((BodyParameter) param).getSchema().getReference().replace("#/definitions/", ""));
				}
				else
				{
					dataCurrentPath.getParamNameAndType().put(param.getName(), "null");
				}
				break;

			default:
				return;
			}
		}
	}

	/**
	 * Fill url info with generated data
	 * @param urlInfo : 
	 * @param listParam : list of parameters for the current operation type
	 * @param currentPathName : name of the path to process
	 * @param swagger
	 */
	void fillData(UrlInfo urlInfo, List<Parameter> listParam,String currentPathName,Swagger swagger)
	{
		io.swagger.models.Path path = swagger.getPaths().get(currentPathName);
		Map<String,String> params=new TreeMap<>();

		String linkBase = (swagger.getSchemes().get(0).toString().toLowerCase() + "://" + swagger.getHost() + swagger.getBasePath() + currentPathName);

		for (Parameter param : listParam){
			linkBase = linkBase.replace("{" + param.getName() + "}", Tools.generateTestData(param));
			if(param.getIn().equals("formData") || param.getIn().equals("body"))
			{
				params.put(param.getName(),Tools.generateTestData(param));
			}
		}
		urlInfo.setLink(linkBase);
		urlInfo.setParameters(params);

		fillParamsMap(urlInfo, path);
	}

	private void fillDataPost(UrlInfo dataCurrentPath, List<Parameter> listParam, String currentPathName,
			Swagger swagger) {
		io.swagger.models.Path path = swagger.getPaths().get(currentPathName);
		Map<String,String> params=new TreeMap<>();

		String linkBase = (swagger.getSchemes().get(0).toString().toLowerCase() + "://" + swagger.getHost() + swagger.getBasePath() + currentPathName);

		for (Parameter param : listParam){
			linkBase = linkBase.replace("{" + param.getName() + "}", "1");
			if("formData".equals(param.getIn()))
			{
				params.put(param.getName(),Tools.generateTestData(param));
			}
			else if("body".equals(param.getIn()))
			{
				params.put(param.getName(),Tools.generateTestData(param));
			}
		}
		dataCurrentPath.setLink(linkBase);
		dataCurrentPath.setParameters(params);

		fillParamsMap(dataCurrentPath, path);	
	}
}

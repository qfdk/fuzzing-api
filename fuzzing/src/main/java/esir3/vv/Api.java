package esir3.vv;


import esir3.vv.Tools.OperationType;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;
import io.swagger.parser.SwaggerParser;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
			if(url.getOperationType().equals(OperationType.GET.toString()))
			{
				url.setReponseCode(Tools.sendGet(url.getLink()));
			}
			if(url.getOperationType().equals(OperationType.POST.toString()))
			{
				url.setReponseCode(Tools.sendPost(url.getLink(), url.getParameters()));
			}
			if(url.getOperationType().equals(OperationType.DELETE.toString()))
			{
				url.setReponseCode(Tools.sendDel(url.getLink()));
			}
			if(url.getOperationType().equals(OperationType.PUT.toString()))
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
		Tools.saveConf(map, "conf/conf.xml");
		JSONObject ret = new JSONObject();
		ret.put("msg", Tools.readConf("conf/conf.xml").getProperty("base_url"));
		return Response.status(200).entity(ret.toString()).build();
	}

	
}

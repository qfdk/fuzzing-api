package esir3.vv;


import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.json.JSONObject;

/**
 * Created by qfdk on 19/10/2016.
 */
@Path("v1")
public class Api {

	private FuzzingData data;

	public Api() {
		System.out.println("[+] Fuzzing api init...");
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

		Swagger swagger = new SwaggerParser().read("./src/main/resources/swagger.json");

		Map<String, io.swagger.models.Path> paths = swagger.getPaths();

		System.out.println("------- List of all paths ----------");
		for (Map.Entry<String, io.swagger.models.Path> entry : paths.entrySet()) {
			System.out.println(entry.getKey());
		}

		return Response.status(200).entity("{status:ok,msg:api works!}").build();
	}

	/**
	 * http://localhost:8080/api/v1/getUrl
	 *
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("analyse")
	@Produces(MediaType.APPLICATION_JSON)
	public Response analyse() throws Exception {
		List<String> urls=new ArrayList<>();
		urls.add("http://qfdk.me");
		urls.add("http://google.com");
		data = new FuzzingData("localhost","html",urls);

		List<String> paths=data.getPaths();
		List<String> pathsValided= new ArrayList<>();

		String hostname=data.getHostname();
		String contentType= data.getContentType();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("hostname",hostname);
		jsonObject.put("contentType",contentType);

		for(String path:paths)
		{
			pathsValided.add(Tools.sendGet(path).split("#")[0]+"#"+path);
		}
		jsonObject.put("paths",pathsValided);
		return Response.status(200).entity(jsonObject.toString()).build();
	}

	/**
	 * http://localhost:8080/api/v1/getPath
	 *
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("getPath")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPath(@PathParam("url") String url) throws Exception {

		FuzzingData data = new FuzzingData();
		
		String specSwagger = Tools.sendGet(url);
		if(specSwagger.split("#")[0].equals("200"))
		{
			Swagger swagger = new SwaggerParser().parse(specSwagger.split("#")[1]);
			URI uri = new URI(url);

			JSONObject ret = new JSONObject();

			List<String> pathList = new ArrayList<>();
			Map<String, io.swagger.models.Path> paths = swagger.getPaths();
			for (String path : paths.keySet()) {
				pathList.add(uri.getScheme()+"://"+uri.getHost()+path);
			}

			ret.put("hostname",uri.getHost());
			ret.put("paths",pathList);
			
			data.setHostname(uri.getHost());
			data.setPaths(pathList);

			System.out.println(ret);
			//		return Response.status(200).entity("{status:ok,msg:api works!}").build();
			return Response.status(200).entity(ret).build(); 
		}
		else
		{
			return Response.status(520).entity("{status:no,msg:bad link}").build();
		}
	}
}

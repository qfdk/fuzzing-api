package esir3.vv;


import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;
import io.swagger.parser.SwaggerParser;
import org.json.JSONObject;

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
    private String base_url=Tools.readConf("conf/conf.xml").getProperty("base_url");

    public Api() {
        data = FuzzingData.getInstance();
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

        List<String> paths = data.getLink();
        List<String> pathsValid = new ArrayList<>();
        String hostname = data.getHostname();
        String contentType = data.getContentType();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hostname", hostname);
        jsonObject.put("contentType", contentType);

        for (String path : paths) {
            String tmp = null;
            try {
                tmp = Tools.sendGet(path);
                pathsValid.add(tmp.split("#")[0] + "#" + path);
            } catch (Exception e) {
                pathsValid.add("FFF#" + path);
            }
        }
        jsonObject.put("paths", pathsValid);
        System.out.println(jsonObject);
        return Response.status(200).entity(jsonObject.toString()).build();
    }

    @GET
    @Path("saveConf")
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveConf(@QueryParam("path") String path) throws Exception {
        Map<String,String> map= new TreeMap<>();
        map.put("base_url","http://localhost:8080");
        Tools.saveConf(map,"conf/conf.xml");

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
        List<DataPath> dataPaths = new ArrayList<>();

        if (swagger != null) {
            Map<String, io.swagger.models.Path> paths = swagger.getPaths();

            for (String currentPathName : paths.keySet()) {
                io.swagger.models.Path currentPath = paths.get(currentPathName);

                if (currentPath.getGet() != null) {
                    DataPath dataCurrentPath = new DataPath();

                    //	Get all responses codes
                    dataCurrentPath.setCode(paths.get(currentPathName).getGet().getResponses().keySet());

                    // Get all link with data
                    String linkBase = swagger.getSchemes().get(0).toString().toLowerCase() + "://" + swagger.getHost() + swagger.getBasePath() + currentPathName;

                    if (!currentPath.getGet().getParameters().isEmpty()) {
                        for (Parameter parameter : currentPath.getGet().getParameters()) {
                            // todo
                            linkBase = linkBase.replace("{" + parameter.getName() + "}", Tools.generateTestData());
                            dataCurrentPath.setLink(linkBase);
                        }
                    } else {
                        dataCurrentPath.setLink(linkBase);
                    }

                    dataPaths.add(dataCurrentPath);
                }
            }
            data.setHostname(swagger.getHost());
            data.setPaths(dataPaths);
        } else {
            JSONObject ret = new JSONObject();
            ret.put("msg", "SWAGGER FILE IS INVALID");
            return Response.status(200).entity(ret.toString()).build();
        }
        System.out.print(dataPaths);
        return Response.temporaryRedirect(new URI(this.base_url+"/api/v1/analyse")).build();
    }
}

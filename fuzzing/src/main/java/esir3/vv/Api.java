package esir3.vv;


import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

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

//        List<String> paths=data.getPaths();
//        String hostname=data.getHostname();
//        String contentType= data.getContentType();
//
//        for(String path:paths)
//        {
//
//        }
        System.out.println(Tools.sendGet("http://qfdk.me"));

        return Response.status(200).entity("{status:ok,msg:sasa}").build();
    }
}

package esir3.vv;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by qfdk on 19/10/2016.
 */
@Path("v1")
public class Api {


    public Api() {
        System.out.println("api init...");
    }
    /**
     * http://localhost:8080/api/v1/getUrl
     *
     * @return
     * @throws Exception
     */
    @GET
    @Path("getStatus")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUrl() throws Exception {
        return Response.status(200).entity("api works").build();
    }

}

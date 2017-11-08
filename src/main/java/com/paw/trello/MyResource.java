package com.paw.trello;

import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJSON() {
        JSONObject object = null;
        Response response = null;
        try {
            object = new JSONObject();
            object.put("Name", "Bryan");
            object.put("Age", "27");
            response = Response.status(Response.Status.OK).entity(object.toString()).build();
        } catch (Exception e) {
            System.out.println("error=" + e.getMessage());
        }
        return response;
    }
}
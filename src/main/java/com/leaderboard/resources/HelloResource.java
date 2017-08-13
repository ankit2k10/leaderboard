package com.leaderboard.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by ankit.chaudhury on 13/08/17.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class HelloResource {

    @GET
    public Response hello() {
        return Response.status(Response.Status.OK).entity("Application is up").build();
    }
}

package com.leaderboard.resources;

import com.google.inject.Inject;
import com.leaderboard.dao.entity.User;
import com.leaderboard.dto.ErrorResponse;
import com.leaderboard.dto.user.UserCreateDto;
import com.leaderboard.exceptions.ClientException;
import com.leaderboard.mapper.ObjectMapper;
import com.leaderboard.mapper.UserMapper;
import com.leaderboard.service.UserService;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Created by ankit.chaudhury on 13/08/17.
 */
@Path("/user")
public class UserResource {

    @Inject
    private UserService userService;

    private ObjectMapper<UserCreateDto, User> userMapper = new UserMapper();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@Valid UserCreateDto userCreateDto) {
        User user = new User();
        userMapper.from(userCreateDto, user);
        try {
            int userId = userService.saveEntity(user);
            return Response.status(Response.Status.CREATED).entity(userId).build();
        } catch (ClientException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), Response.Status.BAD_REQUEST.getStatusCode());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
    }
}

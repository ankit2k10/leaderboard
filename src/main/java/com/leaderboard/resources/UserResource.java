package com.leaderboard.resources;

import com.google.inject.Inject;
import com.leaderboard.dao.entity.User;
import com.leaderboard.dto.user.UserCreateDto;
import com.leaderboard.mapper.ObjectMapper;
import com.leaderboard.mapper.annotations.MyUserMapper;
import com.leaderboard.service.UserService;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by ankit.chaudhury on 13/08/17.
 */
@Path("/user")
public class UserResource {

    @Inject
    private UserService userService;

    @Inject
    @MyUserMapper
    private ObjectMapper<UserCreateDto, User> userMapper;

    @POST
    public Response createUser(@Valid UserCreateDto userCreateDto) {
        User user = new User();
        userMapper.from(userCreateDto, user);
        int userId = userService.saveEntity(user);
        return Response.status(Response.Status.CREATED).entity(userId).build();
    }
}

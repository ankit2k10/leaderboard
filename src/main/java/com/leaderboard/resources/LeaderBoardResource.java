package com.leaderboard.resources;

import com.google.inject.Inject;
import com.leaderboard.dao.entity.Board;
import com.leaderboard.dto.board.BoardCreateDto;
import com.leaderboard.mapper.ObjectMapper;
import com.leaderboard.mapper.annotations.MyBoardMapper;
import com.leaderboard.service.LeaderBoardService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ankit.chaudhury on 14/08/17.
 */
@Path("/board")
public class LeaderBoardResource {

    @Inject
    @MyBoardMapper
    private ObjectMapper<BoardCreateDto, Board> mapper;

    @Inject
    private LeaderBoardService boardService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBoard(@Valid BoardCreateDto createDto) {
        Board board = new Board();
        mapper.from(createDto, board);
        int id = boardService.saveEntity(board);
        return Response.status(Response.Status.CREATED).entity(id).build();
    }

    @PUT
    @Path("/{boardId}/{userId}/{score}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateScore(@PathParam("boardId") Integer boardId,
                                @PathParam("userId") Integer userId,
                                @Min(0) @PathParam("score") Integer score) {
        boardService.updateScore(boardId, userId, score, 0);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{boardId}/top")
    @Produces(MediaType.APPLICATION_JSON)
    public Response top(@PathParam("boardId") Integer boardId,
                        @NotNull @Min(0) @QueryParam("n") Integer n) {
        if(boardService == null) {
            throw new RuntimeException("Boardservice is null");
        }
        List<Integer> userIds = boardService.top(boardId, n);
        Map<String, List<Integer>> output = new HashMap<String, List<Integer>>();
        if(userIds!=null)
            output.put("data", userIds);
        else
            output.put("data", Collections.EMPTY_LIST);

        return Response.status(Response.Status.OK).entity(output).build();
    }

    @GET
    @Path("/{boardId}/relative/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response relative(@PathParam("boardId") Integer boardId,
                             @PathParam("userId") Integer userId,
                        @NotNull @Min(0) @QueryParam("n1") Integer n1,
                        @NotNull @Min(0) @QueryParam("n1") Integer n2) {
        List<Integer> userIds = boardService.relative(boardId, userId, n1, n2);
        Map<String, List<Integer>> output = new HashMap<String, List<Integer>>();
        if(userIds!=null)
            output.put("data", userIds);
        else
            output.put("data", Collections.EMPTY_LIST);

        return Response.status(Response.Status.OK).entity(output).build();
    }
}

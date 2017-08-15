package com.leaderboard.resources;

import com.google.inject.Inject;
import com.leaderboard.dao.entity.Board;
import com.leaderboard.dao.exceptions.BoardNotExistsException;
import com.leaderboard.dao.exceptions.EntityNotExistsException;
import com.leaderboard.dto.ErrorResponse;
import com.leaderboard.dto.board.BoardCreateDto;
import com.leaderboard.exceptions.ClientException;
import com.leaderboard.mapper.BoardMapper;
import com.leaderboard.mapper.ObjectMapper;
import com.leaderboard.service.LeaderBoardService;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

/**
 * Created by ankit.chaudhury on 14/08/17.
 */
@Slf4j
@Path("/board")
public class LeaderBoardResource {

    private ObjectMapper<BoardCreateDto, Board> mapper = new BoardMapper();

    @Inject
    private LeaderBoardService boardService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBoard(@Valid BoardCreateDto createDto) {
        Board board = new Board();
        mapper.from(createDto, board);
        try {
            int id = boardService.saveEntity(board);
            log.info("Created board with id " + id);
            return Response.status(Response.Status.CREATED).entity(id).build();
        } catch (ClientException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), Response.Status.BAD_REQUEST.getStatusCode());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
    }

    @PUT
    @Path("/{boardId}/user/{userId}/{score}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateScore(@PathParam("boardId") Integer boardId,
                                @PathParam("userId") Integer userId,
                                @Min(0) @PathParam("score") Integer score) {
        try {
            log.info("Updating user {} score to {}", userId, score);
            boardService.updateScore(boardId, userId, score, 0);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (EntityNotExistsException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), Response.Status.NOT_FOUND.getStatusCode());
            return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
        } catch (ClientException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), Response.Status.BAD_REQUEST.getStatusCode());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
    }

    @GET
    @Path("/{boardId}/top")
    @Produces(MediaType.APPLICATION_JSON)
    public Response top(@PathParam("boardId") Integer boardId,
                        @NotNull @Min(0) @QueryParam("n") Integer n) {
        try {
            log.info("Fetching top {} users", n);
            List<Integer> userIds = boardService.top(boardId, n);
            if(userIds == null)
                userIds = Collections.EMPTY_LIST;

            return Response.status(Response.Status.OK).entity(userIds).build();
        } catch (BoardNotExistsException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), Response.Status.NOT_FOUND.getStatusCode());
            return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
        } catch (ClientException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), Response.Status.BAD_REQUEST.getStatusCode());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
    }

    @GET
    @Path("/{boardId}/relative/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response relative(@PathParam("boardId") Integer boardId,
                             @PathParam("userId") Integer userId,
                        @NotNull @Min(0) @QueryParam("n1") Integer n1,
                        @NotNull @Min(0) @QueryParam("n2") Integer n2) {
        try {
            log.info("Fetching relative users to {}", userId);
            List<Integer> userIds = boardService.relative(boardId, userId, n1, n2);
            if(userIds == null)
                userIds = Collections.EMPTY_LIST;

            return Response.status(Response.Status.OK).entity(userIds).build();
        } catch (EntityNotExistsException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), Response.Status.NOT_FOUND.getStatusCode());
            return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
        } catch (ClientException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), Response.Status.BAD_REQUEST.getStatusCode());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        } catch (Exception e) {
            log.error("Error in relative", e);
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
    }
}

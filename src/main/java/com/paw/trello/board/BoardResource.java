package com.paw.trello.board;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/boards")
public class BoardResource {

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public List<Board> getBoards() {
        return BoardRepository.getInstance().getBoards();
    }

    @GET
    @Path("board/{id}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Board getBoard(@PathParam("id") Long id) {
        return BoardRepository.getInstance().getBoard(id);
    }

    @POST
    @Path("board")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBoard(Board board) {
        BoardRepository.getInstance().createBoard(board);
        return Response.status(201).build();
    }

    @PUT
    @Path("board")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBoard(Board board) {
        if (BoardRepository.getInstance().existItem(board.getId())) {
            BoardRepository.getInstance().updateBoard(board);
        } else {
            BoardRepository.getInstance().createBoard(board);
        }
        return Response.status(201).build();
    }

    @DELETE
    @Path("board/{id}")
    public void removeBoard(@PathParam("id") Long id) {
        if (BoardRepository.getInstance().existItem(id)) {
            BoardRepository.getInstance().removeBoard(id);
        } else {
            //throw some exception || return http status
        }
    }

}

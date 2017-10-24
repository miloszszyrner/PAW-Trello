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
    public List<BoardData> getBoards() {
        return BoardRepository.getInstance().getItems();
    }

    @GET
    @Path("board/{id}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public BoardData getBoard(@PathParam("id") Long id) {
        return BoardRepository.getInstance().getItem(id);
    }

    @POST
    @Path("board")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBoard(BoardData board) {
        BoardRepository.getInstance().createItem(board);
        return Response.status(201).build();
    }

    @PUT
    @Path("board")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBoard(BoardData board) {
        if (BoardRepository.getInstance().existItem(board.getId(), "BOARD")) {
            BoardRepository.getInstance().updateItem(board);
        } else {
            BoardRepository.getInstance().createItem(board);
        }
        return Response.status(201).build();
    }

    @DELETE
    @Path("board/{id}")
    public void removeBoard(@PathParam("id") Long id) {
        if (BoardRepository.getInstance().existItem(id, "BOARD")) {
            BoardRepository.getInstance().removeItem(id, "BOARD");
        } else {
            //throw some exception || return http status
        }
    }

}

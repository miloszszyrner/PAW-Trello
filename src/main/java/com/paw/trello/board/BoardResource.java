package com.paw.trello.board;

import com.paw.trello.roll.RollData;
import com.paw.trello.roll.RollRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/boards")
public class BoardResource {

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<BoardData> getBoards() {
        return BoardRepository.getInstance().getItems();
    }

    @GET
    @Path("board/{bId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public BoardData getBoard(@PathParam("bId") Long bId) {
        return BoardRepository.getInstance().getItem(bId);
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
    @Path("board/{bId}")
    public void removeBoard(@PathParam("bId") Long bId) {
        if (BoardRepository.getInstance().existItem(bId, "BOARD")) {
            BoardRepository.getInstance().removeItem(bId, "BOARD");
        } else {
            //throw some exception || return http status
        }
    }

    @GET
    @Path("board/{bId}/rolls/all")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<RollData> getRolls(@PathParam("bId") Long bId) {
        return RollRepository.getInstance().getItems(bId);
    }

    @GET
    @Path("board/{bId}/rolls/roll/{rId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public RollData getRoll(@PathParam("bId") Long bId, @PathParam("rId") Long rId) {
        return RollRepository.getInstance().getItem(bId, rId);
    }

    @POST
    @Path("board/{bId}/rolls/roll")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBoard(@PathParam("bId") Long bId, RollData roll) {
        RollRepository.getInstance().createItem(roll, bId);
        return Response.status(201).build();
    }

    @PUT
    @Path("board/{bId}/rolls/roll")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBoard(@PathParam("bId") Long bId, RollData roll) {
        if (RollRepository.getInstance().existItem(roll.getId(), "ROLL")) {
            RollRepository.getInstance().updateItem(roll);
        } else {
            RollRepository.getInstance().createItem(roll);
        }
        return Response.status(201).build();
    }

    @DELETE
    @Path("board/{bId}/rolls/roll/{rId}")
    public void removeBoard(@PathParam("bId") Long bId, @PathParam("rId") Long rId) {
        if (RollRepository.getInstance().existItem(rId, "ROLL")) {
            RollRepository.getInstance().removeItem(rId, "ROLL");
        } else {
            //throw some exception || return http status
        }
    }
}
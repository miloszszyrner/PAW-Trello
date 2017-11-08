package com.paw.trello.board;

import com.paw.trello.roll.RollData;
import com.paw.trello.roll.RollRepository;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/boards")
public class BoardResource {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<BoardData> getBoards() throws SQLException, NamingException {
        return BoardRepository.getInstance().getItems();
    }

    @GET
    @Path("/{bId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public BoardData getBoard(@PathParam("bId") Long bId) throws NamingException {
        return BoardRepository.getInstance().getItem(bId).get(0);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBoard(BoardData board) throws SQLException, NamingException {
        BoardRepository.getInstance().createItem(board);
        return Response.status(201).build();
    }

    @PUT
    @Path("/{bId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBoard(@PathParam("bId") Long bId, BoardData board) throws SQLException, NamingException {
        if (BoardRepository.getInstance().existItem(board.getId())) {
            BoardRepository.getInstance().updateItem(board, bId);
        } else {
            BoardRepository.getInstance().createItem(board);
        }
        return Response.status(201).build();
    }

    @DELETE
    @Path("/{bId}")
    public void removeBoard(@PathParam("bId") Long bId) throws NamingException, SQLException {
        if (BoardRepository.getInstance().existItem(bId)) {
            BoardRepository.getInstance().removeItem(bId);
        } else {
            //throw some exception || return http status
        }
    }

    @GET
    @Path("/{bId}/rolls")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<RollData> getRolls(@PathParam("bId") Long bId) throws SQLException, NamingException {
        return RollRepository.getInstance().getItems(bId);
    }

    @GET
    @Path("/{bId}/rolls/{rId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public RollData getRoll(@PathParam("bId") Long bId, @PathParam("rId") Long rId) throws SQLException, NamingException {
        return RollRepository.getInstance().getItem(bId, rId).get(0);
    }

    @POST
    @Path("/{bId}/rolls")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBoard(@PathParam("bId") Long bId, RollData roll) throws SQLException, NamingException {
        roll.setBoardId(bId);
        RollRepository.getInstance().createItem(roll);
        return Response.status(201).build();
    }

    @PUT
    @Path("/{bId}/rolls/{rId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBoard(@PathParam("bId") Long bId, @PathParam("rId") Long rId, RollData roll) throws NamingException, SQLException {
        if (RollRepository.getInstance().existItem(bId, rId)) {
            RollRepository.getInstance().updateItem(roll, rId);
        } else {
            RollRepository.getInstance().createItem(roll);
        }
        return Response.status(201).build();
    }

    @DELETE
    @Path("/{bId}/rolls/{rId}")
    public void removeBoard(@PathParam("bId") Long bId, @PathParam("rId") Long rId) throws NamingException, SQLException {
        if (RollRepository.getInstance().existItem(bId, rId)) {
            RollRepository.getInstance().removeItem(rId);
        } else {
            //throw some exception || return http status
        }
    }
}
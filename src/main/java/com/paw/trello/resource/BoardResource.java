package com.paw.trello.resource;

import com.paw.trello.board.BoardData;
import com.paw.trello.board.BoardRepository;
import com.paw.trello.card.CardData;
import com.paw.trello.card.CardRepository;
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
    public Response createrRoll(@PathParam("bId") Long bId, RollData roll) throws SQLException, NamingException {
        roll.setBoardId(bId);
        RollRepository.getInstance().createItem(roll);
        return Response.status(201).build();
    }

    @PUT
    @Path("/{bId}/rolls/{rId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRoll(@PathParam("bId") Long bId, @PathParam("rId") Long rId, RollData roll) throws NamingException, SQLException {
        if (RollRepository.getInstance().existItem(bId, rId)) {
            RollRepository.getInstance().updateItem(roll, rId);
        } else {
            RollRepository.getInstance().createItem(roll);
        }
        return Response.status(201).build();
    }

    @DELETE
    @Path("/{bId}/rolls/{rId}")
    public void removeRoll(@PathParam("bId") Long bId, @PathParam("rId") Long rId) throws NamingException, SQLException {
        if (RollRepository.getInstance().existItem(bId, rId)) {
            RollRepository.getInstance().removeItem(rId);
        } else {
            //throw some exception || return http status
        }
    }

    @GET
    @Path("/{bId}/rolls/{rId}/cards")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<CardData> getCards(@PathParam("rId") Long rId) throws SQLException, NamingException {
        return CardRepository.getInstance().getItems(rId);
    }

    @GET
    @Path("/{bId}/rolls/{rId}/cards/{cId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public CardData getCard(@PathParam("rId") Long rId, @PathParam("cId") Long cId) throws SQLException, NamingException {
        return CardRepository.getInstance().getItem(rId, cId).get(0);
    }

    @POST
    @Path("/{bId}/rolls/{rId}/cards")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createrCard(@PathParam("rId") Long rId, CardData card) throws SQLException, NamingException {
        card.setRollId(rId);
        CardRepository.getInstance().createItem(card);
        return Response.status(201).build();
    }

    @PUT
    @Path("/{bId}/rolls/{rId}/cards/{cId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCard(@PathParam("rId") Long rId, @PathParam("cId") Long cId, CardData card) throws NamingException, SQLException {
        if (CardRepository.getInstance().existItem(rId, cId)) {
            CardRepository.getInstance().updateItem(card, cId);
        } else {
            CardRepository.getInstance().createItem(card);
        }
        return Response.status(201).build();
    }

    @DELETE
    @Path("/{bId}/rolls/{rId}/cards/{cId}")
    public void removeCard(@PathParam("rId") Long rId, @PathParam("cId") Long cId) throws NamingException, SQLException {
        if (CardRepository.getInstance().existItem(rId, cId)) {
            CardRepository.getInstance().removeItem(cId);
        } else {
            //throw some exception || return http status
        }
    }

}
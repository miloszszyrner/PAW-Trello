package com.paw.trello.resources;

import com.paw.trello.User.UserData;
import com.paw.trello.User.UserRepository;
import com.paw.trello.annotations.*;
import com.paw.trello.board.BoardData;
import com.paw.trello.board.BoardRepository;
import com.paw.trello.card.CardData;
import com.paw.trello.card.CardRepository;
import com.paw.trello.roll.LaneData;
import com.paw.trello.roll.LaneRepository;

import javax.naming.NamingException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@Path("/")
public class BoardResource {

    @GET
    @Path("{id}/boards")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<BoardData> getBoards(@PathParam("id") Long userId) throws SQLException, NamingException {
        return BoardRepository.getInstance().getItems(userId);
    }

    @GET
    @Path("{id}boards/{bId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getBoard(@PathParam("id") Long userId, @PathParam("bId") Long bId) throws NamingException {
        BoardData boardById = BoardRepository.getInstance().getItem(userId,bId).get(0);
        return Response.status(Response.Status.OK).entity(boardById).build();
    }

    @POST
    @Path("{id}/boards")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response createBoard(@PathParam("id") Long userId, @Valid @ValidBoard BoardData board) throws SQLException, NamingException {
        board.setUserId(userId);
        BoardRepository.getInstance().createItem(board);
        return Response.status(Response.Status.CREATED).entity("The board has benn successfully cretead").build();
    }

    @PUT
    @Path("{id}/boards/{bId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response updateBoard(@PathParam("id") Long userId, @PathParam("bId") Long bId, BoardData board) throws SQLException, NamingException, InvocationTargetException, IllegalAccessException {
        if (BoardRepository.getInstance().existItem(userId, bId)) {
            board.setUserId(userId);
            BoardRepository.getInstance().updateItem(board, bId);
            return Response.status(Response.Status.OK).entity("The board has been fully updated").build();
        } else {
            BoardRepository.getInstance().createItem(board);
            return Response.status(Response.Status.CREATED).entity("The board has benn successfully cretead").build();
        }

    }

    @DELETE
    @Path("{id}/boards/{bId}")
    @Produces(MediaType.TEXT_HTML)
    public Response removeBoard(@PathParam("id") Long userId, @PathParam("bId") Long bId) throws NamingException, SQLException {
        if (BoardRepository.getInstance().existItem(userId, bId)) {
            BoardRepository.getInstance().removeItem(bId);
            return Response.status(Response.Status.NO_CONTENT).entity("Board has been successfully removed").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Board not exist in database").build();
        }
    }

    @GET
    @Path("{id}/boards/{bId}/rolls")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<LaneData> getRolls(@PathParam("bId") Long bId) throws SQLException, NamingException {
        return LaneRepository.getInstance().getItems(bId);
    }

    @GET
    @Path("{id}/boards/{bId}/rolls/{rId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getRoll(@PathParam("bId") Long bId, @PathParam("rId") Long rId) throws SQLException, NamingException {
        LaneData laneById = LaneRepository.getInstance().getItem(bId, rId).get(0);
        return Response.status(Response.Status.OK).entity(laneById).build();
    }

    @POST
    @Path("{id}/boards/{bId}/rolls")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response createrRoll(@PathParam("bId") Long bId, @Valid @ValidLane LaneData roll) throws SQLException, NamingException {
        roll.setBoardId(bId);
        LaneRepository.getInstance().createItem(roll);
        return Response.status(Response.Status.CREATED).entity("The lane has benn successfully cretead").build();
    }

    @PUT
    @Path("{id}/boards/{bId}/rolls/{rId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response updateRoll(@PathParam("bId") Long bId, @PathParam("rId") Long rId, LaneData roll) throws NamingException, SQLException, InvocationTargetException, IllegalAccessException {
        if (LaneRepository.getInstance().existItem(bId, rId)) {
            LaneRepository.getInstance().updateItem(roll, rId);
            return Response.status(Response.Status.OK).entity("The lane has been fully updated").build();
        } else {
            LaneRepository.getInstance().createItem(roll);
            return Response.status(Response.Status.CREATED).entity("The lane has benn successfully cretead").build();
        }
    }

    @DELETE
    @Path("{id}/boards/{bId}/rolls/{rId}")
    @Produces(MediaType.TEXT_HTML)
    public Response removeRoll(@PathParam("bId") Long bId, @PathParam("rId") Long rId) throws NamingException, SQLException {
        if (LaneRepository.getInstance().existItem(bId, rId)) {
            LaneRepository.getInstance().removeItem(rId);
            return Response.status(Response.Status.NO_CONTENT).entity("Lane has been successfully removed").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Lane not exist in database").build();
        }
    }

    @GET
    @Path("{id}/boards/{bId}/rolls/{rId}/cards")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<CardData> getCards(@PathParam("rId") Long rId) throws SQLException, NamingException {
        return CardRepository.getInstance().getItems(rId);
    }

    @GET
    @Path("{id}/boards/{bId}/rolls/{rId}/cards/{cId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getCard(@PathParam("rId") Long rId, @PathParam("cId") Long cId) throws SQLException, NamingException {
        CardData cardById = CardRepository.getInstance().getItem(rId, cId).get(0);
        return Response.status(Response.Status.OK).entity(cardById).build();
    }

    @POST
    @Path("{id}/boards/{bId}/rolls/{rId}/cards")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response createrCard(@PathParam("rId") Long rId, @Valid @ValidCard CardData card) throws SQLException, NamingException {
        card.setRollId(rId);
        CardRepository.getInstance().createItem(card);
        return Response.status(Response.Status.CREATED).entity("The card has benn successfully cretead").build();
    }

    @PUT
    @Path("{id}/boards/{bId}/rolls/{rId}/cards/{cId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response updateCard(@PathParam("rId") Long rId, @PathParam("cId") Long cId, CardData card) throws NamingException, SQLException, InvocationTargetException, IllegalAccessException {
        if (CardRepository.getInstance().existItem(rId, cId)) {
            CardRepository.getInstance().updateItem(card, cId);
            return Response.status(Response.Status.OK).entity("The card has been fully updated").build();
        } else {
            CardRepository.getInstance().createItem(card);
            return Response.status(Response.Status.CREATED).entity("The lane has benn successfully cretead").build();
        }
    }

    @DELETE
    @Path("{id}/boards/{bId}/rolls/{rId}/cards/{cId}")
    @Produces(MediaType.TEXT_HTML)
    public Response removeCard(@PathParam("rId") Long rId, @PathParam("cId") Long cId) throws NamingException, SQLException {
        if (CardRepository.getInstance().existItem(rId, cId)) {
            CardRepository.getInstance().removeItem(cId);
            return Response.status(Response.Status.NO_CONTENT).entity("Card has been successfully removed").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Card not exist in database").build();
        }
    }

}
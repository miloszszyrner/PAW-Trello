package com.paw.trello.resources;

import com.paw.trello.annotations.*;
import com.paw.trello.board.BoardData;
import com.paw.trello.board.BoardRepository;
import com.paw.trello.card.CardData;
import com.paw.trello.card.CardRepository;
import com.paw.trello.filter.JWTTokenNeeded;
import com.paw.trello.remark.RemarkData;
import com.paw.trello.remark.RemarkRepository;
import com.paw.trello.lane.LaneData;
import com.paw.trello.lane.LaneRepository;

import javax.naming.NamingException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@Path("/boards")
@JWTTokenNeeded
public class BoardResource {

    public static Long userId = null;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<BoardData> getBoards() throws SQLException, NamingException {
        return BoardRepository.getInstance().getItems(userId);
    }

    @GET
    @Path("/{bId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getBoard(@PathParam("bId") Long bId) throws NamingException {
        BoardData boardById = BoardRepository.getInstance().getItem(userId,bId).get(0);
        return Response.status(Response.Status.OK).entity(boardById).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response createBoard(@Valid @ValidBoard BoardData board) throws SQLException, NamingException {
        board.setUserId(userId);
        BoardRepository.getInstance().createItem(board);
        return Response.status(Response.Status.CREATED).entity("The board has benn successfully cretead").build();
    }

    @PUT
    @Path("/{bId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response updateBoard(@PathParam("bId") Long bId, BoardData board) throws SQLException, NamingException, InvocationTargetException, IllegalAccessException {
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
    @Path("/{bId}")
    @Produces(MediaType.TEXT_HTML)
    public Response removeBoard(@PathParam("bId") Long bId) throws NamingException, SQLException {
        if (BoardRepository.getInstance().existItem(userId, bId)) {
            BoardRepository.getInstance().removeItem(bId);
            return Response.status(Response.Status.NO_CONTENT).entity("Board has been successfully removed").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Board not exist in database").build();
        }
    }

    @GET
    @Path("/{bId}/lanes")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<LaneData> getLanes(@PathParam("bId") Long bId) throws SQLException, NamingException {
        return LaneRepository.getInstance().getItems(bId);
    }

    @GET
    @Path("/{bId}/lanes/{lId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getLanes(@PathParam("bId") Long bId, @PathParam("lId") Long lId) throws SQLException, NamingException {
        LaneData laneById = LaneRepository.getInstance().getItem(bId, lId).get(0);
        return Response.status(Response.Status.OK).entity(laneById).build();
    }

    @POST
    @Path("/{bId}/lanes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response createrLane(@PathParam("bId") Long bId, @Valid @ValidLane LaneData roll) throws SQLException, NamingException {
        roll.setBoardId(bId);
        LaneRepository.getInstance().createItem(roll);
        return Response.status(Response.Status.CREATED).entity("The lane has benn successfully cretead").build();
    }

    @PUT
    @Path("/{bId}/lanes/{lId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response updateLane(@PathParam("bId") Long bId, @PathParam("lId") Long lId, LaneData roll) throws NamingException, SQLException, InvocationTargetException, IllegalAccessException {
        if (LaneRepository.getInstance().existItem(bId, lId)) {
            LaneRepository.getInstance().updateItem(roll, lId);
            return Response.status(Response.Status.OK).entity("The lane has been fully updated").build();
        } else {
            LaneRepository.getInstance().createItem(roll);
            return Response.status(Response.Status.CREATED).entity("The lane has benn successfully cretead").build();
        }
    }

    @DELETE
    @Path("/{bId}/lanes/{lId}")
    @Produces(MediaType.TEXT_HTML)
    public Response removeLane(@PathParam("bId") Long bId, @PathParam("lId") Long lId) throws NamingException, SQLException {
        if (LaneRepository.getInstance().existItem(bId, lId)) {
            LaneRepository.getInstance().removeItem(lId);
            return Response.status(Response.Status.NO_CONTENT).entity("Lane has been successfully removed").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Lane not exist in database").build();
        }
    }

    @GET
    @Path("/{bId}/lanes/{lId}/cards")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<CardData> getCards(@PathParam("lId") Long lId) throws SQLException, NamingException {
        return CardRepository.getInstance().getItems(lId);
    }

    @GET
    @Path("/{bId}/lanes/{lId}/cards/{cId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getCard(@PathParam("lId") Long lId, @PathParam("cId") Long cId) throws SQLException, NamingException {
        CardData cardById = CardRepository.getInstance().getItem(lId, cId).get(0);
        return Response.status(Response.Status.OK).entity(cardById).build();
    }

    @POST
    @Path("/{bId}/lanes/{lId}/cards")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response createrCard(@PathParam("lId") Long lId, @Valid @ValidCard CardData card) throws SQLException, NamingException {
        card.setLaneId(lId);
        CardRepository.getInstance().createItem(card);
        return Response.status(Response.Status.CREATED).entity("The card has benn successfully cretead").build();
    }

    @PUT
    @Path("/{bId}/lanes/{lId}/cards/{cId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response updateCard(@PathParam("lId") Long lId, @PathParam("cId") Long cId, CardData card) throws NamingException, SQLException, InvocationTargetException, IllegalAccessException {
        if (CardRepository.getInstance().existItem(lId, cId)) {
            CardRepository.getInstance().updateItem(card, cId);
            return Response.status(Response.Status.OK).entity("The card has been fully updated").build();
        } else {
            CardRepository.getInstance().createItem(card);
            return Response.status(Response.Status.CREATED).entity("The card has benn successfully cretead").build();
        }
    }

    @DELETE
    @Path("/{bId}/lanes/{lId}/cards/{cId}")
    @Produces(MediaType.TEXT_HTML)
    public Response removeCard(@PathParam("lId") Long lId, @PathParam("cId") Long cId) throws NamingException, SQLException {
        if (CardRepository.getInstance().existItem(lId, cId)) {
            CardRepository.getInstance().removeItem(cId);
            return Response.status(Response.Status.NO_CONTENT).entity("Card has been successfully removed").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Card not exist in database").build();
        }
    }

    @GET
    @Path("/{bId}/lanes/{lId}/cards/{cId}/remarks")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<RemarkData> getRemarks(@PathParam("cId") Long cId) throws SQLException, NamingException {
        return RemarkRepository.getInstance().getItems(cId);
    }

    @GET
    @Path("/{bId}/lanes/{lId}/cards/{cId}/remarks/{rmId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getRemark(@PathParam("cId") Long cId, @PathParam("rmId") Long rmId) throws SQLException, NamingException {
        RemarkData remarkById = RemarkRepository.getInstance().getItem(cId, rmId).get(0);
        return Response.status(Response.Status.OK).entity(remarkById).build();
    }

    @POST
    @Path("/{bId}/lanes/{lId}/cards/{cId}/remarks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response createRemark(@PathParam("cId") Long cId, @Valid @ValidRemark RemarkData remark) throws SQLException, NamingException {
        remark.setCardId(cId);
        RemarkRepository.getInstance().createItem(remark);
        return Response.status(Response.Status.CREATED).entity("The remark has benn successfully cretead").build();
    }

    @PUT
    @Path("/{bId}/lanes/{lId}/cards/{cId}/remarks/{rmId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response updateRemark(@PathParam("cId") Long cId, @PathParam("rmId") Long rmId, RemarkData remark) throws NamingException, SQLException, InvocationTargetException, IllegalAccessException {
        if (RemarkRepository.getInstance().existItem(cId, rmId)) {
            RemarkRepository.getInstance().updateItem(remark, cId);
            return Response.status(Response.Status.OK).entity("The remark has been fully updated").build();
        } else {
            RemarkRepository.getInstance().createItem(remark);
            return Response.status(Response.Status.CREATED).entity("The remark has benn successfully cretead").build();
        }
    }

    @DELETE
    @Path("/{bId}/lanes/{lId}/cards/{cId}/remarks/{rmId}")
    @Produces(MediaType.TEXT_HTML)
    public Response removeRemark(@PathParam("cId") Long cId, @PathParam("rmId") Long rmId) throws NamingException, SQLException {
        if (RemarkRepository.getInstance().existItem(cId, rmId)) {
            RemarkRepository.getInstance().removeItem(rmId);
            return Response.status(Response.Status.NO_CONTENT).entity("Remark has been successfully removed").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Remark not exist in database").build();
        }
    }
}

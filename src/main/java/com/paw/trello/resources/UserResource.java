package com.paw.trello.resources;

import com.paw.trello.User.UserData;
import com.paw.trello.User.UserRepository;
import com.paw.trello.annotations.ValidLoggingUser;
import com.paw.trello.annotations.ValidUser;
import org.mindrot.jbcrypt.BCrypt;

import javax.naming.NamingException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("user/")
public class UserResource {

    @POST
    @Path("registration")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser(@Valid @ValidUser UserData user) throws ValidationException,SQLException, NamingException {
        UserRepository.getInstance().createItem(user);
        return Response.status(Response.Status.CREATED).entity("The user has benn successfully cretead").build();
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(@Valid @ValidLoggingUser UserData user) throws ValidationException,SQLException, NamingException {
        UserData loggedUser = UserRepository.getInstance().getByUsername(user.getUsername()).get(0);
        if(loggedUser == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not exist in database").build();
        }
        if (BCrypt.checkpw(user.getPassword(), loggedUser.getPassword()))
            return Response.status(Response.Status.OK).entity(loggedUser).build();
        return Response.status(Response.Status.FORBIDDEN).entity("Wrong password").build();
    }
}

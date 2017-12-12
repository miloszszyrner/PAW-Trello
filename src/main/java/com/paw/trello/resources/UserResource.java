package com.paw.trello.resources;

import com.paw.trello.User.UserData;
import com.paw.trello.User.UserRepository;
import com.paw.trello.annotations.ValidLoggingUser;
import com.paw.trello.annotations.ValidUser;
import com.paw.trello.util.KeyGenerator;
import com.paw.trello.util.SimpleKeyGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.mindrot.jbcrypt.BCrypt;

import javax.inject.Inject;
import javax.naming.NamingException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

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
        if (BCrypt.checkpw(user.getPassword(), loggedUser.getPassword())) {
            String token = issueToken(loggedUser.getId().toString());

            return Response.ok().header("Access-Control-Expose-Headers", AUTHORIZATION).header(AUTHORIZATION, "Bearer " + token).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity("Wrong password").build();
    }
    private String issueToken(String login) {
        Key key = SimpleKeyGenerator.getInstance().generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusHours(1L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return jwtToken;
    }
    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}

package com.paw.trello;

import com.paw.trello.User.UserData;
import com.paw.trello.resources.UserResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;


public class UserResourceTest extends JerseyTest {

    private UserData user;

    @Override
    protected Application configure() {
        return new ResourceConfig(UserResource.class);
    }

    @Before
    public void initialize() {
        user = new UserData();
        user.setUsername("tester");
        user.setPassword("testoweHaslo123");
        user.setPasswordConfirmation("testoweHaslo123");
    }

    @Test
    public void registrationCorrectDataTest() {
        Entity<UserData> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);

        Response response = target("user/registration").request().post(userEntity);

        assertThat(response.getStatus(), is(Response.Status.CREATED.getStatusCode()));
    }

    @Test
    public void registrationNullPasswordDataTest() {
        user.setPassword(null);
        Entity<UserData> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);

        Response response = target("user/registration").request().post(userEntity);

        assertThat(response.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
    }

    @Test
    public void loginWithCorrectDataTest() {
        user.setPassword("testoweHaslo123");
        Entity<UserData> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);

        Response response = target("user/login").request().post(userEntity);

        assertThat(response.getStringHeaders().get(AUTHORIZATION).get(0), startsWith("Bearer"));
    }
}

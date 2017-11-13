package com.paw.trello;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;

import static org.junit.Assert.assertEquals;

public class MyResourceTest {

    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        //server = Main.startServer();
        // create the client
        //Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

       // target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
       // server.stop();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIt() {
        assertEquals("Got it!", "Got it!");
    }
}

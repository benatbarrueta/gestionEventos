package es.deusto.spq.server;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import categories.IntegrationTest;
import es.deusto.spq.server.jdo.Evento;
import es.deusto.spq.server.jdo.TipoUsuario;
import es.deusto.spq.server.jdo.Usuario;

/**
 * This class represents the integration tests for the server.
 * It tests the functionality of the server by sending HTTP requests and verifying the responses.
 */
@Category(IntegrationTest.class)
public class ServerIntegrationTest {
    private static final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");

    private static HttpServer server;
    private WebTarget target;

    private static Date fecha = new Date(System.currentTimeMillis());

    @BeforeClass
    public static void prepareTests() throws Exception {
        server = Main.startServer();

        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            pm.makePersistent(new Usuario("test", "test", "test", "test", "test", "test", "test", TipoUsuario.CLIENTE, fecha, "test"));
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @Before
    public void setUp() {
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI).path("resource");
    }

    /**
     * This method is used to tear down the server after running the integration tests.
     * It shuts down the server, clears the database, and closes the PersistenceManager.
     *
     * @throws Exception if an error occurs during the tear down process
     */
    @AfterClass
    public static void tearDownServer() throws Exception {
        server.shutdown();

        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            pm.newQuery(Usuario.class).deletePersistentAll();
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }

        server.shutdown();
    }

    /**
     * This method tests the login functionality for a user.
     * It creates a new Usuario object with the username "test" and password "test".
     * Then, it sends a POST request to the "login" endpoint with the user object as JSON.
     * The response is expected to have a successful status code.
     */
    @Test
    public void testLoginUser(){
        Usuario user = new Usuario("test", "test");
        
        Response response = target.path("login")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(user, MediaType.APPLICATION_JSON));

        assertEquals(Family.SUCCESSFUL, response.getStatusInfo().getFamily());
    }

    /**
     * Test case for creating a new Evento.
     */
    @Test
    public void testNewEvento(){
        Evento event = new Evento("testEvent", "testEvent", fecha, "testEvent", 0, 0, "testEvent", null, null, null);

        Response response = target.path("crearEvento")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(event, MediaType.APPLICATION_JSON));

        assertEquals(Family.SUCCESSFUL, response.getStatusInfo().getFamily());
    }

    /**
     * Test case for the method newEntrada().
     * 
     * This test verifies that a new entrada (ticket) can be successfully purchased for an event.
     * It creates a new Evento object with test data and sends a GET request to the server to purchase an entrada for the event.
     * The test asserts that the response status is successful.
     */
    @Test
    public void testNewEntrada(){
        Evento event = new Evento("testEvent", "testEvent", fecha, "testEvent", 0, 0, "testEvent", null, null, null);

        Response response = target.path("comprarEntrada/" + event.getId() + "/VIP/1")
            .request(MediaType.APPLICATION_JSON)
            .get();

        assertEquals(Family.SUCCESSFUL, response.getStatusInfo().getFamily());
    }
}

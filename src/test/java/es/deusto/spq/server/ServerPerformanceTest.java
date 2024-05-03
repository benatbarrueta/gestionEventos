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
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;

import categories.PerformanceTest;
import es.deusto.spq.server.jdo.Evento;
import es.deusto.spq.server.jdo.TipoUsuario;
import es.deusto.spq.server.jdo.Usuario;

/**
* This class represents a performance test for the server.
* It includes test methods for login, creating a new event, and buying a ticket.
*/

@Category(PerformanceTest.class)
public class ServerPerformanceTest {
    
    private static final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");

    private static HttpServer server;
    private WebTarget target;

    private static Date fecha = new Date(System.currentTimeMillis());

    @Rule
    public JUnitPerfRule perfTestRule = new JUnitPerfRule(new HtmlReportGenerator("target/junitperf/report.html"));

    //prepara las clases para poder hacer luego los test de performance
    @BeforeClass
    public static void prepareTests() throws Exception {
        server = Main.startServer();

        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            pm.makePersistent(new Usuario("testUser", "testUser", "testUser", "testUser", "testUser", "testUser", "testUser", TipoUsuario.CLIENTE, fecha, "testUser"));
            pm.makePersistent(new Evento("testEvent", "testEvent", fecha, "testEvent", 0, 0, "testEvent", null, null, null));
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
     * This method is responsible for tearing down the server and cleaning up the database after running the tests.
     * It shuts down the server, deletes all the persistent instances of the Usuario class from the database,
     * and closes the PersistenceManager.
     *
     * @throws Exception if an error occurs during the teardown process
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
    }

    /**
     * Test case for the login functionality of the server.
     * It creates a test user and sends a login request to the server.
     * The test checks if the response status is successful.
     */
    @Test
    @JUnitPerfTest(threads = 10, durationMs = 1000)
    public void testLoginUser(){
        Usuario user = new Usuario("testUser", "testUser");
        
        Response response = target.path("login")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(user, MediaType.APPLICATION_JSON));

        assertEquals(Family.SUCCESSFUL, response.getStatusInfo().getFamily());
    }

    /**
     * Test case for the method testNewEvento.
     * 
     * This method tests the functionality of creating a new event.
     * It creates a new Evento object with test data and sends a POST request to the server to create the event.
     * The method then asserts that the response status is successful.
     */
    @Test
    @JUnitPerfTest(threads = 10, durationMs = 1000)
    public void testNewEvento(){
        Evento event = new Evento("testEvent", "testEvent", fecha, "testEvent", 0, 0, "testEvent", null, null, null);

        Response response = target.path("crearEvento")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(event, MediaType.APPLICATION_JSON));

        assertEquals(Family.SUCCESSFUL, response.getStatusInfo().getFamily());
    }

    /**
     * This method is a unit test for the "testNewEntrada" functionality.
     * It tests the behavior of creating a new entry for an event.
     * 
     * The test performs the following steps:
     * 1. Creates a new Evento object with test data.
     * 2. Sends a GET request to the server to buy an entry for the event.
     * 3. Asserts that the response status is successful.
     */
    @Test
    @JUnitPerfTest(threads = 10, durationMs = 1000)
    public void testNewEntrada(){
        Evento event = new Evento("testEvent", "testEvent", fecha, "testEvent", 0, 0, "testEvent", null, null, null);

        Response response = target.path("comprarEntrada/" + event.getId() + "/VIP/1")
            .request(MediaType.APPLICATION_JSON)
            .get();

        assertEquals(Family.SUCCESSFUL, response.getStatusInfo().getFamily());
    }
}

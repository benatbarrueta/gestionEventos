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

import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;

import es.deusto.spq.server.jdo.Evento;
import es.deusto.spq.server.jdo.TipoUsuario;
import es.deusto.spq.server.jdo.Usuario;

/**
 * This class represents a performance test for the server.
 * It includes test methods for login, creating a new event, and buying a ticket.
 */
public class ServerPerformanceTest {
    
    private static final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");

    private static HttpServer server;
    private WebTarget target;

    private static Date fecha = new Date(System.currentTimeMillis());

    @Rule
    public JUnitPerfRule perfTestRule = new JUnitPerfRule(new HtmlReportGenerator("target/junitperf/report.html"));

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

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 1000)
    public void testLoginUser(){
        Usuario user = new Usuario("testUser", "testUser");
        
        Response response = target.path("login")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(user, MediaType.APPLICATION_JSON));

        assertEquals(Family.SUCCESSFUL, response.getStatusInfo().getFamily());
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 1000)
    public void testNewEvento(){
        Evento event = new Evento("testEvent", "testEvent", fecha, "testEvent", 0, 0, "testEvent", null, null, null);

        Response response = target.path("crearEvento")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(event, MediaType.APPLICATION_JSON));

        assertEquals(Family.SUCCESSFUL, response.getStatusInfo().getFamily());
    }

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

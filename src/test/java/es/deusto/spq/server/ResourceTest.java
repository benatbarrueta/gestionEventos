package es.deusto.spq.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Date;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import es.deusto.spq.server.jdo.Entrada;
import es.deusto.spq.server.jdo.Evento;
import es.deusto.spq.server.jdo.TipoUsuario;
import es.deusto.spq.server.jdo.Usuario;

public class ResourceTest {
    Usuario u;

    @Mock
    private Usuario usuario;

    @Mock
    private Evento evento;

    @Mock
    private Entrada entrada;

    private Resource resource;

    @Mock
    private Response response;

    Date fecha = new Date(System.currentTimeMillis());

    @Before
    public void setUp() {
        usuario = mock(Usuario.class);
        evento = mock(Evento.class);
        entrada = mock(Entrada.class);
        response = mock(Response.class);

        resource = new Resource();

        u = new Usuario("test","test", "test","test","test","test","test",TipoUsuario.CLIENTE,fecha, "test");
    }

    @Test
    public void testLoginUserFails() throws Exception{
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), resource.loginUser(usuario).getStatus());
    }

    @Test
    public void testRegisterUser() throws Exception{
        resource.registerUser(u);
        assertEquals(Response.Status.OK.getStatusCode(), resource.loginUser(u).getStatus());
    }

    @Test
    public void testLogout() throws Exception{
        assertEquals(Response.Status.OK.getStatusCode(), resource.logout().getStatus());
    }

    @Test
    public void testEliminarCuenta() throws Exception{
        assertEquals(Response.Status.OK.getStatusCode(), resource.eliminarCuenta(u.getDni()).getStatus());
    }

    @Test
    public void testCrearEventoTest() throws Exception{
        assertEquals(Response.Status.OK.getStatusCode(), resource.crearEvento(evento).getStatus());
    }

    @Test
    public void testGetEventos() throws Exception{
        assertEquals(Response.Status.OK.getStatusCode(), resource.getEventos().getStatus());
    }

    /*@Test
    public void testGetEventosId() throws Exception{
        assertEquals(Response.Status.OK.getStatusCode(), resource.getEventos("" + evento.getId()).getStatus());
    }

    @Test
    public void testEliminarEvento() throws Exception{
        assertEquals(Response.Status.OK.getStatusCode(), resource.eliminarEvento("" + evento.getId()).getStatus());
    }

    @Test
    public void testActualizarEvento() throws Exception{
        assertEquals(Response.Status.OK.getStatusCode(), resource.actualizarEvento(evento).getStatus());
    }

    @Test
    public void testComprarEntrada() throws Exception{
        assertEquals(Response.Status.OK.getStatusCode(), resource.comprarEntrada("" + entrada.getEvento().getId(), entrada.getSector().toString(), "2").getStatus());
    }*/

    @Test
    public void testGetEntradas() throws Exception{
        assertEquals(Response.Status.OK.getStatusCode(), resource.getEntradas().getStatus());
    }

    /*@Test
    public void testEliminarEntrada() throws Exception{
        assertEquals(Response.Status.OK.getStatusCode(), resource.eliminarEntrada("" + entrada.getId()).getStatus());
    }*/
}
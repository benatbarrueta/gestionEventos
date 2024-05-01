package es.deusto.spq.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Date;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import es.deusto.spq.server.jdo.Evento;
import es.deusto.spq.server.jdo.Resenya;
import es.deusto.spq.server.jdo.TipoUsuario;
import es.deusto.spq.server.jdo.Usuario;

/**
 * This class contains unit tests for the Resource class.
 * It tests various methods related to user registration, login, logout, account deletion,
 * and other operations related to users and events.
 */
public class ResourceTest {
    private Resource resource;

    @Mock
    private PersistenceManager persistenceManager;

    @Mock
    private Transaction transaction;

    Date fecha = new Date(System.currentTimeMillis());

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        //inicializar objeto estatico mocK PersistenceManagerFactory
        try (MockedStatic<JDOHelper> jdoHelper = Mockito.mockStatic(JDOHelper.class)) {
            PersistenceManagerFactory pmf = mock(PersistenceManagerFactory.class);
            jdoHelper.when(() -> JDOHelper.getPersistenceManagerFactory("datanucleus.properties")).thenReturn(pmf);
            
            when(pmf.getPersistenceManager()).thenReturn(persistenceManager);
            when(persistenceManager.currentTransaction()).thenReturn(transaction);

            //inicializar objeto testado con dependencias mock
            resource = new Resource();
        }
    }

    @Test
    public void testRegisterUser() throws Exception{
        // Preparar objeto mock query para ser devuelto por mock persistenceManager
        Usuario usuario = new Usuario();
        usuario.setNombre("test");
        usuario.setApellidos("test");
        usuario.setDni("test");
        usuario.setContrasenya("test");
        usuario.setRol(TipoUsuario.ADMINISTRADOR);
        usuario.setFechaNacimiento(fecha);
        usuario.setEmail("test");
        usuario.setTelefono("test");
        usuario.setDireccion("test");

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Usuario user = spy(Usuario.class);
        when(persistenceManager.getObjectById(Usuario.class, usuario.getDni())).thenReturn(user);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.registerUser(user);

        // Comprobar response esperada        
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testLoginUserFailes() throws Exception{
        // Preparar objeto mock query para ser devuelto por mock persistenceManager
        Usuario usuario = new Usuario();
        usuario.setNombre("test");
        usuario.setApellidos("test");
        usuario.setDni("test");
        usuario.setContrasenya("test");
        usuario.setRol(TipoUsuario.ADMINISTRADOR);
        usuario.setFechaNacimiento(fecha);
        usuario.setEmail("test");
        usuario.setTelefono("test");
        usuario.setDireccion("test");

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Usuario user = spy(Usuario.class);
        when(persistenceManager.getObjectById(Usuario.class, usuario.getDni())).thenReturn(user);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.loginUser(user);

        // Comprobar response esperada        
        assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());
    }

    @Test
    public void testLogout() throws Exception{
        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.logout();

        // Comprobar response esperada        
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    public void testEliminarCuenta() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setNombre("test");
        usuario.setApellidos("test");
        usuario.setDni("test");
        usuario.setContrasenya("test");
        usuario.setRol(TipoUsuario.ADMINISTRADOR);
        usuario.setFechaNacimiento(fecha);
        usuario.setEmail("test");
        usuario.setTelefono("test");
        usuario.setDireccion("test");

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Usuario user = spy(Usuario.class);
        when(persistenceManager.getObjectById(Usuario.class, usuario.getDni())).thenReturn(user);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.eliminarCuenta(usuario.getDni());

        // Comprobar response esperada        
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    /*@Test
    public void testGetUsuarios(){
        Usuario usuario = new Usuario();
        usuario.setNombre("test");
        usuario.setApellidos("test");
        usuario.setDni("test");
        usuario.setContrasenya("test");
        usuario.setRol(TipoUsuario.ADMINISTRADOR);
        usuario.setFechaNacimiento(fecha);
        usuario.setEmail("test");
        usuario.setTelefono("test");
        usuario.setDireccion("test");

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Usuario user = spy(Usuario.class);
        when(persistenceManager.getObjectById(Usuario.class, usuario.getDni())).thenReturn(user);
        
        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        // Llamar metodo test
        Response response = resource.getUsuarios();

        // Comprobar response esperada
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    */

    @Test
    public void testGetUsuarioId(){
        Usuario usuario = new Usuario();
        usuario.setNombre("test");
        usuario.setApellidos("test");
        usuario.setDni("test");
        usuario.setContrasenya("test");
        usuario.setRol(TipoUsuario.ADMINISTRADOR);
        usuario.setFechaNacimiento(fecha);
        usuario.setEmail("test");
        usuario.setTelefono("test");
        usuario.setDireccion("test");

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Usuario user = spy(Usuario.class);
        when(persistenceManager.getObjectById(Usuario.class, usuario.getDni())).thenReturn(user);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.getUsuarioId(usuario.getDni());

        // Comprobar response esperada        
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    @Test
    public void testActualizarRolUsuario() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setNombre("test");
        usuario.setApellidos("test");
        usuario.setDni("test");
        usuario.setContrasenya("test");
        usuario.setRol(TipoUsuario.ADMINISTRADOR);
        usuario.setFechaNacimiento(fecha);
        usuario.setEmail("test");
        usuario.setTelefono("test");
        usuario.setDireccion("test");

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Usuario user = spy(Usuario.class);
        when(persistenceManager.getObjectById(Usuario.class, usuario.getDni())).thenReturn(user);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        user.setRol(TipoUsuario.USUARIO);
        Response response = resource.actualizarRolUsuario("test", TipoUsuario.USUARIO);

        //Comprobar response esperada
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    public void testCrearEventoTest() throws Exception{
        Evento evento = new Evento();
        evento.setNombre("test");
        evento.setDescripcion("test");
        evento.setAforo(0);
        evento.setAforoTotal(10000);
        evento.setLugar("test");
        evento.setOrganizador("test");

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Evento event = spy(Evento.class);
        when(persistenceManager.getObjectById(Evento.class, evento.getId())).thenReturn(event);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.crearEvento(event);

        // Comprobar response esperada        
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    /*@Test
    public void testGetEventos() throws Exception{
        Evento evento = new Evento();
        evento.setNombre("test");
        evento.setDescripcion("test");
        evento.setAforo(0);
        evento.setAforoTotal(10000);
        evento.setLugar("test");
        evento.setOrganizador("test");

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Evento event = spy(Evento.class);
        when(persistenceManager.getObjectById(Evento.class, evento.getId())).thenReturn(event);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.getEventos();

        // Comprobar response esperada        
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }*/

    @Test
    public void testGetEventosId() throws Exception{
        Evento evento = new Evento();
        evento.setNombre("test");
        evento.setDescripcion("test");
        evento.setAforo(0);
        evento.setAforoTotal(10000);
        evento.setLugar("test");
        evento.setOrganizador("test");

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Evento event = spy(Evento.class);
        when(persistenceManager.getObjectById(Evento.class, "0")).thenReturn(event);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.getEventos("0");

        // Comprobar response esperada        
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testEliminarEvento() throws Exception{
        Evento evento = new Evento();
        evento.setNombre("test");
        evento.setDescripcion("test");
        evento.setAforo(0);
        evento.setAforoTotal(10000);
        evento.setLugar("test");
        evento.setOrganizador("test");

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Evento event = spy(Evento.class);
        when(persistenceManager.getObjectById(Evento.class, "0")).thenReturn(event);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.eliminarEvento("0");

        // Comprobar response esperada        
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    public void testActualizarEvento() throws Exception{
        Evento evento = new Evento();
        evento.setNombre("test");
        evento.setDescripcion("test");
        evento.setAforo(0);
        evento.setAforoTotal(10000);
        evento.setLugar("test");
        evento.setOrganizador("test");

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Evento event = spy(Evento.class);
        when(persistenceManager.getObjectById(Evento.class, "0")).thenReturn(event);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        event.setNombre("test2");
        event.setLugar("test2");

        Response response = resource.actualizarEvento(evento);

        //Comprobar response esperada
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());

    }

    @Test
    public void testComprarEntrada() throws Exception{
        Evento evento = new Evento();
        evento.setNombre("test");
        evento.setDescripcion("test");
        evento.setAforo(0);
        evento.setAforoTotal(10000);
        evento.setLugar("test");
        evento.setOrganizador("test");

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Evento event = spy(Evento.class);
        when(persistenceManager.getObjectById(Evento.class, "0")).thenReturn(event);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.comprarEntrada("0", "PISTA", "2");

        // Comprobar response esperada
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    /*
    @Test
    public void testGetEntradas() throws Exception{
        Evento evento = new Evento();
        evento.setNombre("test");
        evento.setDescripcion("test");
        evento.setAforo(0);
        evento.setAforoTotal(10000);
        evento.setLugar("test");
        evento.setOrganizador("test");

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Evento event = spy(Evento.class);
        when(persistenceManager.getObjectById(Evento.class, "0")).thenReturn(event);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.getEntradas();

        // Comprobar response esperada
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
    */

    @Test
    public void testEliminarEntrada() throws Exception{
        Evento evento = new Evento();
        evento.setNombre("test");
        evento.setDescripcion("test");
        evento.setAforo(0);
        evento.setAforoTotal(10000);
        evento.setLugar("test");
        evento.setOrganizador("test");

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Evento event = spy(Evento.class);
        when(persistenceManager.getObjectById(Evento.class, "0")).thenReturn(event);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.eliminarEntrada("0");

        // Comprobar response esperada
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testCrearResenya() throws Exception{
        Resenya resenya = new Resenya();
        resenya.setComentario("test");
        resenya.setPuntuacion(5);

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Resenya res = spy(Resenya.class);
        when(persistenceManager.getObjectById(Resenya.class, "0")).thenReturn(res);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.crearResenya(resenya);

        // Comprobar response esperada
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetResenya() throws Exception{
        Resenya resenya = new Resenya();
        resenya.setComentario("test");
        resenya.setPuntuacion(5);

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Resenya res = spy(Resenya.class);
        when(persistenceManager.getObjectById(Resenya.class, "0")).thenReturn(res);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.getResenyasEvento("0");

        // Comprobar response esperada
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    /*@Test
    public void testGetResenyas() throws Exception{
        Resenya resenya = new Resenya();
        resenya.setComentario("test");
        resenya.setPuntuacion(5);

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Resenya res = spy(Resenya.class);
        when(persistenceManager.getObjectById(Resenya.class, "0")).thenReturn(res);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.getResenyas();

        // Comprobar response esperada
        assertEquals(401, response.getStatus());
    }*/
}
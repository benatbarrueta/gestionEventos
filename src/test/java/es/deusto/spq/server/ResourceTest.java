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

import es.deusto.spq.server.jdo.TipoUsuario;
import es.deusto.spq.server.jdo.Usuario;

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

        /*
        // Comprobar que el usuario tiene el dni que se está indicando
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        verify(user).setDni(passwordCaptor.capture());
        assertEquals("test", passwordCaptor.getValue());
        */

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

        /*
        // Comprobar que el usuario tiene el dni que se está indicando
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        verify(user).setDni(passwordCaptor.capture());
        assertEquals("test", passwordCaptor.getValue());
        */

        // Comprobar response esperada        
        assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());
    }

    @Test
    public void testLogout() throws Exception{
        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.logout();

        /*
        // Comprobar que el usuario tiene el dni que se está indicando
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        verify(user).setDni(passwordCaptor.capture());
        assertEquals("test", passwordCaptor.getValue());
        */

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

        /*
        // Comprobar que el usuario tiene el dni que se está indicando
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        verify(user).setDni(passwordCaptor.capture());
        assertEquals("test", passwordCaptor.getValue());
        */

        // Comprobar response esperada        
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    /*@Test
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
    }

    @Test
    public void testGetEntradas() throws Exception{
        assertEquals(Response.Status.OK.getStatusCode(), resource.getEntradas().getStatus());
    }

    /*@Test
    public void testEliminarEntrada() throws Exception{
        assertEquals(Response.Status.OK.getStatusCode(), resource.eliminarEntrada("" + entrada.getId()).getStatus());
    }*/
}
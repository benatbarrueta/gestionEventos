package es.deusto.spq.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import es.deusto.spq.server.jdo.Entrada;
import es.deusto.spq.server.jdo.Evento;
import es.deusto.spq.server.jdo.Resenya;
import es.deusto.spq.server.jdo.SectoresEvento;
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
    public void testRegisterUserAlreadyExists() throws Exception {
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

        //llamar metodo test
        Response response = resource.registerUser(usuario);

        // Comprobar response esperada        
        assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());
    }
    @Test
    public void testRegisterUserNotFound() throws Exception {
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
    
        when(persistenceManager.getObjectById(any(), anyString())).thenThrow(new JDOObjectNotFoundException());

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.registerUser(usuario);

        ArgumentCaptor<Usuario> userCaptor = ArgumentCaptor.forClass(Usuario.class);
        verify(persistenceManager).makePersistent(userCaptor.capture());
        assertEquals("test", userCaptor.getValue().getNombre());
        assertEquals("test", userCaptor.getValue().getApellidos());
        assertEquals("test", userCaptor.getValue().getDni());
        assertEquals("test", userCaptor.getValue().getContrasenya());
        assertEquals(TipoUsuario.CLIENTE, userCaptor.getValue().getRol());
        assertEquals(fecha, userCaptor.getValue().getFechaNacimiento());
        assertEquals("test", userCaptor.getValue().getEmail());
        assertEquals("test", userCaptor.getValue().getTelefono());
        assertEquals("test", userCaptor.getValue().getDireccion());

        // Comprobar response esperada        
        assertEquals(Response.Status.OK, response.getStatusInfo());
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

        //llamar metodo test
        Response response = resource.registerUser(user);

        // Comprobar response esperada        
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testLoginUser() throws Exception{
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
        @SuppressWarnings("rawtypes")
        Query query = spy(Query.class);
        when(persistenceManager.newQuery(Usuario.class)).thenReturn(query);

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

    @Test
    public void testEliminarNotFound() throws Exception{
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
        when(persistenceManager.getObjectById(Usuario.class, "test1")).thenReturn(user);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.eliminarCuenta(usuario.getDni());

        // Comprobar response esperada        
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }

    @Test
    public void testEliminarCuentaFails() throws Exception{
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
        when(persistenceManager.getObjectById(any(), anyString())).thenThrow(new JDOObjectNotFoundException());

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.eliminarCuenta(usuario.getDni());

        // Comprobar response esperada        
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
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
        Query query = spy(Query.class);
        when(persistenceManager.newQuery(Usuario.class)).thenReturn(query);
        
        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        // Llamar metodo test y comprobar response esperada

        try (Response response = resource.getUsuarios()){
            assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());
        } catch (Exception e) {

        }
    }

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
    public void testGetUsuarioIdNotFound(){
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
        when(persistenceManager.getObjectById(Usuario.class, usuario.getDni())).thenThrow(new JDOObjectNotFoundException());

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.getUsuarioId(usuario.getDni());

        // Comprobar response esperada        
        assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());
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
    public void testActualizarRolUsuarioNotFound() throws Exception{
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
        when(persistenceManager.getObjectById(Usuario.class, "test2")).thenReturn(user);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        user.setRol(TipoUsuario.USUARIO);
        Response response = resource.actualizarRolUsuario("test", TipoUsuario.USUARIO);

        //Comprobar response esperada
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }

    @Test
    public void testActualizarRolUsuarioFails() throws Exception{
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
        when(persistenceManager.getObjectById(any(), anyString())).thenThrow(new JDOObjectNotFoundException());

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.actualizarRolUsuario("test", TipoUsuario.USUARIO);

        //Comprobar response esperada
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
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

    @Test
    public void testCrearEventoNotFound() throws Exception{
        Evento evento = new Evento();
        evento.setNombre("test");
        evento.setDescripcion("test");
        evento.setAforo(0);
        evento.setAforoTotal(10000);
        evento.setLugar("test");
        evento.setOrganizador("test");

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        when(persistenceManager.getObjectById(any(), anyString())).thenThrow(new JDOObjectNotFoundException());

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.crearEvento(evento);

        ArgumentCaptor<Evento> eventCaptor = ArgumentCaptor.forClass(Evento.class);
        verify(persistenceManager).makePersistent(eventCaptor.capture());
        assertEquals("test", eventCaptor.getValue().getNombre());
        assertEquals("test", eventCaptor.getValue().getDescripcion());
        assertEquals(0, eventCaptor.getValue().getAforo());
        assertEquals(10000, eventCaptor.getValue().getAforoTotal());
        assertEquals("test", eventCaptor.getValue().getLugar());
        assertEquals("test", eventCaptor.getValue().getOrganizador());

        // Comprobar response esperada
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
   
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testGetEventos() throws Exception{
        Evento evento = new Evento();
        evento.setNombre("test");
        evento.setDescripcion("test");
        evento.setAforo(0);
        evento.setAforoTotal(10000);
        evento.setLugar("test");
        evento.setOrganizador("test");

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Query query = spy(Query.class);
        when(persistenceManager.newQuery(Evento.class)).thenReturn(query);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        // Llamar metodo test y comprobar response esperada
        try (Response response = resource.getEventos()){
            assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
        } catch (Exception e) {
        }
    }

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
    public void testGetEventoIdNotFound() throws Exception{
        Evento evento = new Evento();
        evento.setNombre("test");
        evento.setDescripcion("test");
        evento.setAforo(0);
        evento.setAforoTotal(10000);
        evento.setLugar("test");
        evento.setOrganizador("test");

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        when(persistenceManager.getObjectById(Evento.class, "0")).thenThrow(new JDOObjectNotFoundException());

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.getEventos("0");

        // Comprobar response esperada        
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
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
    public void testEliminarEventoFails() throws Exception{
        Evento evento = new Evento();
        evento.setNombre("test");
        evento.setDescripcion("test");
        evento.setAforo(0);
        evento.setAforoTotal(10000);
        evento.setLugar("test");
        evento.setOrganizador("test");

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        when(persistenceManager.getObjectById(any(), anyString())).thenThrow(new JDOObjectNotFoundException());

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.eliminarEvento("0");

        // Comprobar response esperada        
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }

    @Test
    public void testActualizarEventoNotFound() throws Exception{
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
        event.setNombre("test2");
        event.setLugar("test2");

        Response response = resource.actualizarEvento(evento);

        //Comprobar response esperada
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());

    }

    @Test
    public void testActualizarEventoFails() throws Exception{
        Evento evento = new Evento();
        evento.setNombre("test");
        evento.setDescripcion("test");
        evento.setAforo(0);
        evento.setAforoTotal(10000);
        evento.setLugar("test");
        evento.setOrganizador("test");

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        when(persistenceManager.getObjectById(any(), anyString())).thenThrow(new JDOObjectNotFoundException());

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.actualizarEvento(evento);

        //Comprobar response esperada
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());

    }

    @SuppressWarnings("static-access")
    @Test
    public void testComprarEntradaPista34() throws Exception{
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

        Usuario usuario = new Usuario();
        usuario.setNombre("test");
        usuario.setApellidos("test");
        usuario.setDni("test");
        usuario.setContrasenya("test");
        usuario.setRol(TipoUsuario.CLIENTE);
        usuario.setFechaNacimiento(fecha);
        usuario.setEmail("test");
        usuario.setTelefono("+34test");
        usuario.setDireccion("test");

        Usuario user = spy(Usuario.class);
        when(persistenceManager.getObjectById(Usuario.class, usuario.getDni())).thenReturn(user);

        resource.usuario = usuario;
        
        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.comprarEntrada("0", "PISTA", "2");

        // Comprobar response esperada
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testComprarEntradaFront_stage34() throws Exception{
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

        Usuario usuario = new Usuario();
        usuario.setNombre("test");
        usuario.setApellidos("test");
        usuario.setDni("test");
        usuario.setContrasenya("test");
        usuario.setRol(TipoUsuario.CLIENTE);
        usuario.setFechaNacimiento(fecha);
        usuario.setEmail("test");
        usuario.setTelefono("+34688812275");
        usuario.setDireccion("test");

        Usuario user = spy(Usuario.class);
        when(persistenceManager.getObjectById(Usuario.class, usuario.getDni())).thenReturn(user);

        resource.usuario = usuario;
        
        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.comprarEntrada("0", "FRONT_STAGE", "2");

        // Comprobar response esperada
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testComprarEntradaGrada_alta34() throws Exception{
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

        Usuario usuario = new Usuario();
        usuario.setNombre("test");
        usuario.setApellidos("test");
        usuario.setDni("test");
        usuario.setContrasenya("test");
        usuario.setRol(TipoUsuario.CLIENTE);
        usuario.setFechaNacimiento(fecha);
        usuario.setEmail("test");
        usuario.setTelefono("+34test");
        usuario.setDireccion("test");

        Usuario user = spy(Usuario.class);
        when(persistenceManager.getObjectById(Usuario.class, usuario.getDni())).thenReturn(user);

        resource.usuario = usuario;
        
        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.comprarEntrada("0", "GRADA_ALTA", "2");

        // Comprobar response esperada
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testComprarEntradaGrada_media34() throws Exception{
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

        Usuario usuario = new Usuario();
        usuario.setNombre("test");
        usuario.setApellidos("test");
        usuario.setDni("test");
        usuario.setContrasenya("test");
        usuario.setRol(TipoUsuario.CLIENTE);
        usuario.setFechaNacimiento(fecha);
        usuario.setEmail("test");
        usuario.setTelefono("+34test");
        usuario.setDireccion("test");

        Usuario user = spy(Usuario.class);
        when(persistenceManager.getObjectById(Usuario.class, usuario.getDni())).thenReturn(user);

        resource.usuario = usuario;
        
        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.comprarEntrada("0", "GRADA_MEDIA", "2");

        // Comprobar response esperada
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testComprarEntradaVip34() throws Exception{
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

        Usuario usuario = new Usuario();
        usuario.setNombre("test");
        usuario.setApellidos("test");
        usuario.setDni("test");
        usuario.setContrasenya("test");
        usuario.setRol(TipoUsuario.CLIENTE);
        usuario.setFechaNacimiento(fecha);
        usuario.setEmail("test");
        usuario.setTelefono("+34test");
        usuario.setDireccion("test");

        Usuario user = spy(Usuario.class);
        when(persistenceManager.getObjectById(Usuario.class, usuario.getDni())).thenReturn(user);

        resource.usuario = usuario;
        
        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.comprarEntrada("0", "VIP", "2");

        // Comprobar response esperada
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testComprarEntradaGrada_baja34() throws Exception{
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

        Usuario usuario = new Usuario();
        usuario.setNombre("test");
        usuario.setApellidos("test");
        usuario.setDni("test");
        usuario.setContrasenya("test");
        usuario.setRol(TipoUsuario.CLIENTE);
        usuario.setFechaNacimiento(fecha);
        usuario.setEmail("test");
        usuario.setTelefono("+34test");
        usuario.setDireccion("test");

        Usuario user = spy(Usuario.class);
        when(persistenceManager.getObjectById(Usuario.class, usuario.getDni())).thenReturn(user);

        resource.usuario = usuario;
        
        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.comprarEntrada("0", "GRADA_BAJA", "2");

        // Comprobar response esperada
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testComprarEntradaPista() throws Exception{
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

        Usuario usuario = new Usuario();
        usuario.setNombre("test");
        usuario.setApellidos("test");
        usuario.setDni("test");
        usuario.setContrasenya("test");
        usuario.setRol(TipoUsuario.CLIENTE);
        usuario.setFechaNacimiento(fecha);
        usuario.setEmail("test");
        usuario.setTelefono("test");
        usuario.setDireccion("test");

        Usuario user = spy(Usuario.class);
        when(persistenceManager.getObjectById(Usuario.class, usuario.getDni())).thenReturn(user);

        resource.usuario = usuario;
        
        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.comprarEntrada("0", "PISTA", "2");

        // Comprobar response esperada
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testComprarEntradaFront_stage() throws Exception{
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

        Usuario usuario = new Usuario();
        usuario.setNombre("test");
        usuario.setApellidos("test");
        usuario.setDni("test");
        usuario.setContrasenya("test");
        usuario.setRol(TipoUsuario.CLIENTE);
        usuario.setFechaNacimiento(fecha);
        usuario.setEmail("test");
        usuario.setTelefono("test");
        usuario.setDireccion("test");

        Usuario user = spy(Usuario.class);
        when(persistenceManager.getObjectById(Usuario.class, usuario.getDni())).thenReturn(user);

        resource.usuario = usuario;
        
        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.comprarEntrada("0", "FRONT_STAGE", "2");

        // Comprobar response esperada
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testComprarEntradaGrada_alta() throws Exception{
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

        Usuario usuario = new Usuario();
        usuario.setNombre("test");
        usuario.setApellidos("test");
        usuario.setDni("test");
        usuario.setContrasenya("test");
        usuario.setRol(TipoUsuario.CLIENTE);
        usuario.setFechaNacimiento(fecha);
        usuario.setEmail("test");
        usuario.setTelefono("test");
        usuario.setDireccion("test");

        Usuario user = spy(Usuario.class);
        when(persistenceManager.getObjectById(Usuario.class, usuario.getDni())).thenReturn(user);

        resource.usuario = usuario;
        
        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.comprarEntrada("0", "GRADA_ALTA", "2");

        // Comprobar response esperada
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testComprarEntradaGrada_media() throws Exception{
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

        Usuario usuario = new Usuario();
        usuario.setNombre("test");
        usuario.setApellidos("test");
        usuario.setDni("test");
        usuario.setContrasenya("test");
        usuario.setRol(TipoUsuario.CLIENTE);
        usuario.setFechaNacimiento(fecha);
        usuario.setEmail("test");
        usuario.setTelefono("test");
        usuario.setDireccion("test");

        Usuario user = spy(Usuario.class);
        when(persistenceManager.getObjectById(Usuario.class, usuario.getDni())).thenReturn(user);

        resource.usuario = usuario;
        
        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.comprarEntrada("0", "GRADA_MEDIA", "2");

        // Comprobar response esperada
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testComprarEntradaVip() throws Exception{
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

        Usuario usuario = new Usuario();
        usuario.setNombre("test");
        usuario.setApellidos("test");
        usuario.setDni("test");
        usuario.setContrasenya("test");
        usuario.setRol(TipoUsuario.CLIENTE);
        usuario.setFechaNacimiento(fecha);
        usuario.setEmail("test");
        usuario.setTelefono("test");
        usuario.setDireccion("test");

        Usuario user = spy(Usuario.class);
        when(persistenceManager.getObjectById(Usuario.class, usuario.getDni())).thenReturn(user);

        resource.usuario = usuario;
        
        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.comprarEntrada("0", "VIP", "2");

        // Comprobar response esperada
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testComprarEntradaGrada_baja() throws Exception{
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

        Usuario usuario = new Usuario();
        usuario.setNombre("test");
        usuario.setApellidos("test");
        usuario.setDni("test");
        usuario.setContrasenya("test");
        usuario.setRol(TipoUsuario.CLIENTE);
        usuario.setFechaNacimiento(fecha);
        usuario.setEmail("test");
        usuario.setTelefono("test");
        usuario.setDireccion("test");

        Usuario user = spy(Usuario.class);
        when(persistenceManager.getObjectById(Usuario.class, usuario.getDni())).thenReturn(user);

        resource.usuario = usuario;
        
        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.comprarEntrada("0", "GRADA_BAJA", "2");

        // Comprobar response esperada
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings({ "unused", "static-access" })
    @Test
    public void testComprarEntradaNotFound() throws Exception{
        Evento evento = new Evento();
        evento.setNombre("test");
        evento.setDescripcion("test");
        evento.setAforo(0);
        evento.setAforoTotal(10000);
        evento.setLugar("test");
        evento.setOrganizador("test");

        Usuario usuario = new Usuario();
        usuario.setNombre("test");
        usuario.setApellidos("test");
        usuario.setDni("test");
        usuario.setContrasenya("test");
        usuario.setRol(TipoUsuario.CLIENTE);
        usuario.setFechaNacimiento(fecha);
        usuario.setEmail("test");
        usuario.setTelefono("test");
        usuario.setDireccion("test");

        Usuario user = spy(Usuario.class);
        when(persistenceManager.getObjectById(Usuario.class, usuario.getDni())).thenReturn(user);

        resource.usuario = usuario;
        
        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Evento event = spy(Evento.class);
        when(persistenceManager.getObjectById(Evento.class, "0")).thenReturn(event);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response1 = resource.comprarEntrada("0", "PISTA", "2");
        Response response = resource.comprarEntrada("0", "PISTA", "2");

        // Comprobar response esperada
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
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
        Query query = spy(Query.class);
        when(persistenceManager.newQuery(Entrada.class)).thenReturn(query);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);
     
        // Llamar metodo test y  comprobar response esperada
        try (Response response = resource.getEntradas()){
            assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
        } catch (Exception e) {
        }
    }

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
        when(persistenceManager.getObjectById(Evento.class, evento.getId())).thenReturn(event);

        Entrada entrada = new Entrada();
        entrada.setUsuario(new Usuario("test", "test", "test","test","test", "test", "test", TipoUsuario.CLIENTE, fecha, "test"));
        entrada.setEvento(evento);
        entrada.setPrecio(2);
        entrada.setSector(SectoresEvento.GRADA_ALTA);

        Entrada ticket = spy(Entrada.class);
        when(persistenceManager.getObjectById(Entrada.class, entrada.getId())).thenReturn(ticket);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.eliminarEntrada("" + ticket.getId());

        // Comprobar response esperada
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testEliminarEntradaNotFound() throws Exception{
        Evento evento = new Evento();
        evento.setNombre("test");
        evento.setDescripcion("test");
        evento.setAforo(0);
        evento.setAforoTotal(10000);
        evento.setLugar("test");
        evento.setOrganizador("test");

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        when(persistenceManager.getObjectById(any(), anyString())).thenThrow(new JDOObjectNotFoundException());

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
    public void testCrearResenyaExists() throws Exception{
        Resenya resenya = new Resenya();
        resenya.setComentario("test");
        resenya.setPuntuacion(5);

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Resenya res = spy(Resenya.class);
        when(persistenceManager.getObjectById(Resenya.class, resenya.getId())).thenReturn(res);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);
    }

    @Test
    public void testCrearResenyaFails() throws Exception{
        Resenya resenya = new Resenya();
        resenya.setComentario("test");
        resenya.setPuntuacion(5);

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        when(persistenceManager.getObjectById(any(), anyString())).thenThrow(new JDOObjectNotFoundException());

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.crearResenya(resenya);

        // Comprobar response esperada
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    
    

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testGetResenya() throws Exception{
        Resenya resenya = new Resenya();
        resenya.setComentario("test");
        resenya.setPuntuacion(5);

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Query query = spy(Query.class);
        when(persistenceManager.newQuery(Resenya.class)).thenReturn(query);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.getResenyasEvento(""+resenya.getId());

        // Comprobar response esperada
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetResenyafails() throws Exception{
        Resenya resenya = new Resenya();
        resenya.setComentario("test");
        resenya.setPuntuacion(5);

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Resenya res = spy(Resenya.class);
        when(persistenceManager.getObjectById(Resenya.class, "0")).thenReturn(res);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.getResenyasEvento("5");

        // Comprobar response esperada
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetResenyaNotFound() throws Exception{
        Resenya resenya = new Resenya();
        resenya.setComentario("test");
        resenya.setPuntuacion(5);

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        when(persistenceManager.getObjectById(any(), anyString())).thenThrow(new JDOObjectNotFoundException());

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        //llamar metodo test
        Response response = resource.getResenyasEvento("0");

        // Comprobar response esperada
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testGetResenyas() throws Exception{
        Resenya resenya = new Resenya();
        resenya.setComentario("test");
        resenya.setPuntuacion(5);

        //preparar response para cuando metodo mock Query es llamado con los parametros esperados
        Query query = spy(Query.class);
        when(persistenceManager.newQuery(Resenya.class)).thenReturn(query);

        // preparar comportamiento de transaccion mock
        when(transaction.isActive()).thenReturn(false);

        
        // Llamar metodo test y comprobar response esperada
        try (Response response = resource.getResenyas()){
            assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
        } catch (Exception e) {
        }
    }

}
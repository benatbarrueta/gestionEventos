package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

public class UsuarioTest {
    
    private Usuario usuario;

    @Before
    public void setUp() {
        usuario = new Usuario("test", "test", "test", "test", "test", "test", "test", TipoUsuario.CLIENTE, new Date(System.currentTimeMillis()), "1");
    }

    @Test
    public void testGetNombre() {
        assertEquals("test", usuario.getNombre());
    }

    @Test
    public void testSetNombre() {
        usuario.setNombre("test2");
        assertEquals("test2", usuario.getNombre());
    }

    @Test
    public void testGetApellidos() {
        assertEquals("test", usuario.getApellidos());
    }

    @Test
    public void testSetApellidos() {
        usuario.setApellidos("test2");
        assertEquals("test2", usuario.getApellidos());
    }

    @Test
    public void testGetEmail() {
        assertEquals("test", usuario.getEmail());
    }

    @Test
    public void testSetEmail() {
        usuario.setEmail("test2");
        assertEquals("test2", usuario.getEmail());
    }

    @Test
    public void testGetContrasenya() {
        assertEquals("test", usuario.getContrasenya());
    }

    @Test
    public void testSetContrasenya() {
        usuario.setContrasenya("test2");
        assertEquals("test2", usuario.getContrasenya());
    }

    @Test
    public void testGetDireccion() {
        assertEquals("test", usuario.getDireccion());
    }

    @Test
    public void testSetDireccion() {
        usuario.setDireccion("test2");
        assertEquals("test2", usuario.getDireccion());
    }

    @Test
    public void testGetTelefono() {
        assertEquals("test", usuario.getTelefono());
    }

    @Test
    public void testSetTelefono() {
        usuario.setTelefono("test2");
        assertEquals("test2", usuario.getTelefono());
    }

    @Test
    public void testGetTipoUsuario() {
        assertEquals(TipoUsuario.CLIENTE, usuario.getRol());
    }

    @Test
    public void testSetTipoUsuario() {
        usuario.setRol(TipoUsuario.ADMINISTRADOR);
        assertEquals(TipoUsuario.ADMINISTRADOR, usuario.getRol());
    }

    @Test
    public void testGetFechaNacimiento() {
        assertEquals(new Date(System.currentTimeMillis()), usuario.getFechaNacimiento());
    }

    @Test
    public void testSetFechaNacimiento() {
        Date date = new Date(System.currentTimeMillis());
        usuario.setFechaNacimiento(date);
        assertEquals(date, usuario.getFechaNacimiento());
    }

    @Test
    public void testGetDni() {
        assertEquals("1", usuario.getDni());
    }

    @Test
    public void testSetDni() {
        usuario.setDni("2");
        assertEquals("2", usuario.getDni());
    }
}

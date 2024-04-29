package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * This class contains unit tests for the Usuario class.
 */
public class UsuarioTest {
    
    private Usuario usuario;
    private Date fecha = new Date(System.currentTimeMillis());

    @Before
    public void setUp() {
        usuario = new Usuario("test", "test", "test", "test", "test", "test", "test", TipoUsuario.CLIENTE, fecha, "1");
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
        assertEquals(fecha, usuario.getFechaNacimiento());
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

    @Test
    public void testGetNombreUsuario() {
        assertEquals("test", usuario.getNombreUsuario());
    }

    @Test
    public void testSetNombreUsuario() {
        usuario.setNombreUsuario("test2");
        assertEquals("test2", usuario.getNombreUsuario());
    }

    @Test
    public void testToString() {
        assertEquals("Usuario -> nombre=test, apellidos=test, nombreUsuario=test, contrasenya=test, email=test, direccion=test, telefono=test, rol=CLIENTE, fechaNacimiento=" + fecha + ", dni=1", usuario.toString());
    }
}

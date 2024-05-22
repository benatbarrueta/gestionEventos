package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MensajeTest {
    private Mensaje mensaje;

    @Before
    public void setUp() {
        mensaje = new Mensaje("test", "test");
    }

    @Test
    public void testGetMensaje() {
        assertEquals("test", mensaje.getMensaje());
    }

    @Test
    public void testSetMensaje() {
        mensaje.setMensaje("Test2");
        assertEquals("Test2", mensaje.getMensaje());
    }

    @Test
    public void testGetTelefono() {
        assertEquals("test", mensaje.getTelefono());
    }

    @Test
    public void testSetTelefono() {
        mensaje.setTelefono("Test2");
        assertEquals("Test2", mensaje.getTelefono());
    }

    @Test
    public void testToString() {
        assertEquals("Mensaje: test para telefono: test", mensaje.toString());
    }
    
}

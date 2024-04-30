package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * This class contains unit tests for the Entrada class.
 * It tests the various methods and functionalities of the Entrada class.
 */
public class EntradaTest {
    private Entrada entrada;
    private Entrada entrada2;

    private Date fecha = new Date(System.currentTimeMillis());

    @Before
    public void setUp() {
        Usuario user = new Usuario("test", "test", "test", "test", "test", "test", "test", TipoUsuario.CLIENTE, fecha, "test");
        Evento event = new Evento("test", "test", fecha, "test", 0, 0, "test", null, null, null);
        entrada = new Entrada(user, event, 0, SectoresEvento.PISTA);
        entrada2 = new Entrada();
    }

    @Test
    public void testGetUsuario() {
        assertEquals("test", entrada.getUsuario().getDni());
    }

    @Test
    public void testSetUsuario() {
        Usuario user = new Usuario("test", "test", "test", "test", "test", "test", "test", TipoUsuario.CLIENTE, fecha, "test");
        entrada.setUsuario(user);
        assertEquals("test", entrada.getUsuario().getDni());
    }

    @Test
    public void testGetEvento() {
        assertEquals("test", entrada.getEvento().getNombre());
    }

    @Test
    public void testSetEvento() {
        Evento event = new Evento("test", "test", fecha, "test", 0, 0, "test", null, null, null);
        entrada.setEvento(event);
        assertEquals("test", entrada.getEvento().getNombre());
    }

    @Test
    public void testGetPrecio() {
        assertEquals(0, entrada.getPrecio());
    }

    @Test
    public void testSetPrecio() {
        entrada2.setPrecio(2);
        assertEquals(2, entrada2.getPrecio());
    }

    @Test
    public void testGetSector() {
        assertEquals(SectoresEvento.PISTA, entrada.getSector());
    }

    @Test
    public void testSetSector() {
        entrada.setSector(SectoresEvento.PISTA);
    }

    @Test
    public void testToString() {
        assertEquals("Entrada 0 usuario: test, evento: test", entrada.toString());
    }

    @Test
    public void testToStringCorto() {
        assertEquals("Entrada 0 para evento -> test", entrada.toStringCorto());
    }
}

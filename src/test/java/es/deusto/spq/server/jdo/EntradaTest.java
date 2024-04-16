package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class EntradaTest {
    private Entrada entrada;

    @Before
    public void setUp() {
        entrada = new Entrada(new Usuario(), new Evento(), 0, SectoresEvento.PISTA);
    }

    @Test
    public void testGetUsuario() {
        assertEquals(new Usuario().getDni(), entrada.getUsuario().getDni());
    }

    @Test
    public void testSetUsuario() {
        entrada.getUsuario().setDni("2");;
        assertEquals("2", entrada.getUsuario().getDni());
    }

    @Test
    public void testGetEvento() {
        assertEquals(new Evento().getNombre(), entrada.getEvento().getNombre());
    }

    @Test
    public void testSetEvento() {
        entrada.getEvento().setNombre("2");
        assertEquals("2", entrada.getEvento().getNombre());
    }

    @Test
    public void testGetPrecio() {
        assertEquals(0, entrada.getPrecio());
    }

    @Test
    public void testSetPrecio() {
        entrada.setPrecio(2);
        assertEquals(2, entrada.getPrecio());
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
        assertEquals("Entrada 0 usuario: , evento: null", entrada.toString());
    }

    @Test
    public void testToString2() {
        assertEquals("Entrada 0 usuario: , evento: null", entrada.toString());
    }
}

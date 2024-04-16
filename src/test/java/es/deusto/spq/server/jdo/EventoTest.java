package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class EventoTest {
    
    private Evento evento;
    private Date fecha = new Date(System.currentTimeMillis());

    @Before
    public void setUp() {
        evento = new Evento("test", "test", fecha, "test", 0, 0, "test",new ArrayList<SectoresEvento>(), new HashMap<SectoresEvento, Integer>(), new HashMap<SectoresEvento, Integer>());
    }

    @Test
    public void testGetNombre() {
        assertEquals("test", evento.getNombre());
    }
    
    @Test
    public void testSetNombre() {
        evento.setNombre("Test2");
        assertEquals("Test2", evento.getNombre());
    }

    @Test
    public void testGetLugar() {
        assertEquals("test", evento.getLugar());
    }

    @Test
    public void testSetLugar() {
        evento.setLugar("Test2");
        assertEquals("Test2", evento.getLugar());
    }

    @Test
    public void testGetFecha() {
        assertEquals(fecha, evento.getFecha());
    }

    @Test
    public void testSetFecha() {
        Date date = new Date(System.currentTimeMillis());
        evento.setFecha(date);
        assertEquals(date, evento.getFecha());
    }

    @Test
    public void testGetDescripcion() {
        assertEquals("test", evento.getDescripcion());
    }

    @Test
    public void testSetDescripcion() {
        evento.setDescripcion("Test2");
        assertEquals("Test2", evento.getDescripcion());
    }

    @Test
    public void testGetAforo() {
        assertEquals(0, evento.getAforo());
    }

    @Test
    public void testSetAforo() {
        evento.setAforo(1);
        assertEquals(1, evento.getAforo());
    }   

    @Test
    public void testGetAforoTotal() {
        assertEquals(0, evento.getAforoTotal());
    }

    @Test
    public void testSetAforoTotal() {
        evento.setAforoTotal(1);
        assertEquals(1, evento.getAforoTotal());
    }

    @Test
    public void testGetOrganizador() {
        assertEquals("test", evento.getOrganizador());
    }

    @Test
    public void testSetOrganizador() {
        evento.setOrganizador("Test2");
        assertEquals("Test2", evento.getOrganizador());
    }

    @Test
    public void testGetSectores() {
        assertEquals(new ArrayList<SectoresEvento>(), evento.getSector());
    }

    @Test
    public void testSetSectores() {
        ArrayList<SectoresEvento> sectores = new ArrayList<SectoresEvento>();
        evento.setSector(sectores);
        assertEquals(sectores, evento.getSector());
    }

    @Test
    public void testGetSectoresDisponibles() {
        assertEquals(new HashMap<SectoresEvento, Integer>(), evento.getEntradasSector());
    }

    @Test
    public void testSetSectoresDisponibles() {
        HashMap<SectoresEvento, Integer> sectores = new HashMap<SectoresEvento, Integer>();
        evento.setEntradasSector(sectores);
        assertEquals(sectores, evento.getEntradasSector());
    }

    @Test
    public void testGetSectoresComprados() {
        assertEquals(new HashMap<SectoresEvento, Integer>(), evento.getPrecioSector());
    }

    @Test
    public void testSetSectoresComprados() {
        HashMap<SectoresEvento, Integer> sectores = new HashMap<SectoresEvento, Integer>();
        evento.setPrecioSector(sectores);
        assertEquals(sectores, evento.getPrecioSector());
    }

    @Test
    public void testToString() {
        assertEquals("Evento -> nombre: test, lugar: test, fecha: " + fecha + ", descripcion: test, aforo: 0, organizador: test, sectores disponibles: [], precios disponibles: {}, entradas disponibles: {}", evento.toString());
    }
}

package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class ReseñaTest {
    
    private Usuario user;
    private Evento event;
    private Resenya reseña;
    private Date fecha = new Date(System.currentTimeMillis());

    @Before
    public void setUp() {
        user = new Usuario("test", "test", "test", "test", "test", "test", "test", TipoUsuario.CLIENTE, fecha, "test");
        event = new Evento("test", "test", fecha, "test", 10000, 10000, "test", null, null, null);
        reseña = new Resenya("test", 0, user, event);
    }

    @Test
    public void testGetComentario() {
        assertEquals("test", reseña.getComentario());
    }

    @Test
    public void testSetComentario() {
        reseña.setComentario("Test2");
        assertEquals("Test2", reseña.getComentario());
    }

    @Test
    public void testGetPuntuacion() {
        assertEquals(0, reseña.getPuntuacion());
    }

    @Test
    public void testSetPuntuacion() {
        reseña.setPuntuacion(5);
        assertEquals(5, reseña.getPuntuacion());
    }

    @Test
    public void testGetUsuario() {
        assertEquals(user, reseña.getUsuario());
    }

    @Test
    public void testSetUsuario() {
        Usuario user2 = new Usuario("test2", "test2", "test2", "test2", "test2", "test2", "test2", TipoUsuario.CLIENTE, fecha, "test2");
        reseña.setUsuario(user2);
        assertEquals(user2, reseña.getUsuario());
    }

    @Test
    public void testGetEvento() {
        assertEquals(event, reseña.getEvento());
    }

    @Test
    public void testSetEvento() {
        Evento event2 = new Evento("test2", "test2", fecha, "test2", 10000, 10000, "test2", null, null, null);
        reseña.setEvento(event2);
        assertEquals(event2, reseña.getEvento());
    }
}

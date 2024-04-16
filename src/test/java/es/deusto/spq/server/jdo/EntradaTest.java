package es.deusto.spq.server.jdo;

import org.junit.Before;

public class EntradaTest {
    private Entrada entrada;

    @Before
    public void setUp() {
        entrada = new Entrada(null, null, 0, null);
    }
}

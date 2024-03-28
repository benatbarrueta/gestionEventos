package es.deusto.spq.server.jdo;

import java.util.Arrays;

public enum SectoresEvento {
    PISTA, FRONT_STAGE, GRADA_ALTA, GRADA_MEDIA, GRADA_BAJA, VIP;

    public static SectoresEvento fromString(String sector) {
        return Arrays.stream(SectoresEvento.values())
                .filter(s -> s.toString().equals(sector))
                .findFirst()
                .orElse(null);
    }
}

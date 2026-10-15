package fr.greenmove.location.infrastructure;

import java.time.Instant;

import fr.greenmove.location.domaine.Ports.Horloge;

public class HorlogeFixe implements Horloge {
    private final Instant instant;

    public HorlogeFixe(Instant instant) {
        this.instant = instant;
    }

    @Override
    public Instant maintenant() {
        return this.instant;
    }
}
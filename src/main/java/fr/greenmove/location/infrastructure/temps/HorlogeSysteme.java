package fr.greenmove.location.infrastructure.temps;

import java.time.Instant;

import fr.greenmove.location.domaine.Ports.Horloge;

public class HorlogeSysteme implements Horloge {

    @Override
    public Instant maintenant() {
        return Instant.now();
    }
}
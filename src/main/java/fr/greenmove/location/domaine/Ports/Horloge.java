package fr.greenmove.location.domaine.Ports;

import java.time.Instant;

public interface Horloge {
    Instant maintenant();
}

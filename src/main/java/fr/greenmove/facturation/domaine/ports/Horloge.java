package fr.greenmove.facturation.domaine.ports;

import java.time.Instant;

public interface Horloge {
    Instant maintenant();
}

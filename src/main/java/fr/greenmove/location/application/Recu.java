package fr.greenmove.location.application;

import java.time.Duration;
import java.time.Instant;

import fr.greenmove.location.domaine.IdLocation;

public record Recu(
        IdLocation idLocation,
        Instant debut,
        Instant fin,
        Duration duree) {
    public Recu {
        if (idLocation == null)
            throw new IllegalArgumentException("idLocation obligatoire");
        if (debut == null || fin == null)
            throw new IllegalArgumentException("dates obligatoires");
    }
}
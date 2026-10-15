package fr.greenmove.location.infrastructure.rest.dto;

import java.time.Instant;

import fr.greenmove.location.application.Recu;

public record RecuDto(
        String idLocation,
        Instant debut,
        Instant fin,
        long dureeMinutes) {
    public RecuDto(Recu recu) {
        this(
                recu.idLocation().valeur(),
                recu.debut(),
                recu.fin(),
                recu.duree().toMinutes() // ou recu.dureeMinutes() selon ton Recu
        );
    }
}

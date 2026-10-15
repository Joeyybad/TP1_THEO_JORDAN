package fr.greenmove.location.infrastructure.rest.dto;

import java.time.Instant;

import fr.greenmove.location.domaine.Location;

public record LocationDto(
        String id,
        String idUsager,
        String idVehicule,
        String typeVehicule,
        Instant debut,
        Instant fin,
        String statut) {
    public LocationDto(Location location) {
        this(
                location.id().valeur(),
                location.usager().valeur(),
                location.vehicule().valeur(),
                location.type().name(),
                location.debut(),
                location.fin(),
                location.statut().name());
    }
}

package fr.greenmove.location.infrastructure.persistance;

import fr.greenmove.location.domaine.*;

final class LocationMapper {

    private LocationMapper() {
    }

    static LocationEntity versEntite(Location location) {
        return new LocationEntity(
                location.id().valeur(),
                location.usager().valeur(),
                location.vehicule().valeur(),
                location.type().name(),
                location.debut(),
                location.fin(),
                location.statut().name());
    }

    static Location versDomaine(LocationEntity entity) {
        return Location.reconstituer(
                new IdLocation(entity.getId()),
                new IdUsager(entity.getUsagerId()),
                new IdVehicule(entity.getVehiculeId()),
                TypeVehicule.valueOf(entity.getTypeVehicule()),
                entity.getDebut(),
                entity.getFin(),
                Statut.valueOf(entity.getStatut()));
    }
}
package fr.greenmove.location.infrastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import fr.greenmove.location.domaine.IdLocation;
import fr.greenmove.location.domaine.IdUsager;
import fr.greenmove.location.domaine.Location;
import fr.greenmove.location.domaine.Statut;
import fr.greenmove.location.domaine.Ports.Locations;

public class LocationsEnMemoire implements Locations {
    private final Map<IdLocation, Location> donnees = new HashMap<>();

    @Override
    public Optional<Location> parId(IdLocation id) {
        return Optional.ofNullable(donnees.get(id));
    }

    @Override
    public Optional<Location> enCoursPour(IdUsager usager) {
        return donnees.values().stream()
                .filter(l -> l.usager().equals(usager) && l.statut() == Statut.EN_COURS)
                .findFirst();
    }

    @Override
    public void enregistrer(Location location) {
        donnees.put(location.id(), location);
    }
}
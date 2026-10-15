package fr.greenmove.location.domaine.Ports;

import java.util.Optional;

import fr.greenmove.location.domaine.IdLocation;
import fr.greenmove.location.domaine.IdUsager;
import fr.greenmove.location.domaine.Location;

public interface Locations {
    void enregistrer(Location location);

    Optional<Location> enCoursPour(IdUsager usagerId);

    Optional<Location> parId(IdLocation id);
}

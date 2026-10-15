package fr.greenmove.location.application;

import fr.greenmove.location.domaine.IdLocation;
import fr.greenmove.location.domaine.IdUsager;
import fr.greenmove.location.domaine.IdVehicule;

public interface DemarrerLocation {

    IdLocation demarrer(IdUsager usagerId, IdVehicule vehiculeId);

}

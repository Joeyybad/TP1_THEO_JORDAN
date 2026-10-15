package fr.greenmove.location.domaine.erreurs;

import fr.greenmove.location.domaine.IdVehicule;

public class VehiculeIndisponible extends RuntimeException {

    public VehiculeIndisponible(IdVehicule idVehicule) {
        super("Le véhicule " + idVehicule + " est indisponible.");
    }

}

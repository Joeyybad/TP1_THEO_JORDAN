package fr.greenmove.location.domaine.erreurs;

import fr.greenmove.location.domaine.IdVehicule;

public class VehiculeInconnu extends RuntimeException {

    public VehiculeInconnu(IdVehicule idVehicule) {
        super("Le véhicule " + idVehicule + " est inconnu.");
    }

}

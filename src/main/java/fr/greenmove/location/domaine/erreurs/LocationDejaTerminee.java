package fr.greenmove.location.domaine.erreurs;

import fr.greenmove.location.domaine.IdLocation;

public class LocationDejaTerminee extends RuntimeException {

    public LocationDejaTerminee(IdLocation id) {
        super("La location avec l'id " + id + " est déjà terminée.");
    }

}

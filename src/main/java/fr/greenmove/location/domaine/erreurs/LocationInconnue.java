package fr.greenmove.location.domaine.erreurs;

import fr.greenmove.location.domaine.IdLocation;

public class LocationInconnue extends RuntimeException {

    public LocationInconnue(IdLocation id) {
        super("La location  " + id + " est inconnue.");
    }

}

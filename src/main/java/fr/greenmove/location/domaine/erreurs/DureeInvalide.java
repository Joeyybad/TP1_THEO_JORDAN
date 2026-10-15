package fr.greenmove.location.domaine.erreurs;

import fr.greenmove.location.domaine.IdLocation;

public class DureeInvalide extends RuntimeException {

    public DureeInvalide(IdLocation idLocation) {
        super("Durée invalide pour la location " + idLocation);
    }
}

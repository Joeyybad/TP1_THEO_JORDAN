package fr.greenmove.location.domaine.erreurs;

import fr.greenmove.location.domaine.IdUsager;

public class LocationDejaEnCours extends RuntimeException {

    public LocationDejaEnCours(IdUsager id) {
        super("La location de l'usager  " + id + " est déjà en cours.");
    }

}

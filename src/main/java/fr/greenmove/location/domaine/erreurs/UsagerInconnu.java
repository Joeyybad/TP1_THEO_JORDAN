package fr.greenmove.location.domaine.erreurs;

import fr.greenmove.location.domaine.IdUsager;

public class UsagerInconnu extends RuntimeException {

    public UsagerInconnu(IdUsager idUsager) {
        super("L'usager " + idUsager + " est inconnu.");
    }

}

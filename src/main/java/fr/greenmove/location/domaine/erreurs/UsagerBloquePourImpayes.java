package fr.greenmove.location.domaine.erreurs;

import fr.greenmove.location.domaine.IdUsager;

public class UsagerBloquePourImpayes extends RuntimeException {

    public UsagerBloquePourImpayes(IdUsager id) {
        super("L'usager " + id + " est bloqué pour des impayés.");
    }

}

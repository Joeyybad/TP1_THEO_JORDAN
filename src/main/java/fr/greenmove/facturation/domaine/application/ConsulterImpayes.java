package fr.greenmove.facturation.domaine.application;

import fr.greenmove.facturation.domaine.IdUsager;

public interface ConsulterImpayes {

    // List<Facture> impayesDe(IdUsager idUsager);

    boolean aDesImpayes(IdUsager idUsager);
}

package fr.greenmove.facturation.application;

import java.util.List;

import fr.greenmove.facturation.domaine.IdUsager;
import fr.greenmove.facturation.domaine.Facture;

public interface ConsulterImpayes {

    List<Facture> impayesDe(IdUsager idUsager);

    boolean aDesImpayes(IdUsager idUsager);
}

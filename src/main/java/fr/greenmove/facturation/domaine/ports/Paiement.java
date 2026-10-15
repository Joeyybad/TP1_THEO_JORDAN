package fr.greenmove.facturation.domaine.ports;

import fr.greenmove.facturation.domaine.IdUsager;
import fr.greenmove.facturation.domaine.Montant;

public interface Paiement {

    boolean debiter(IdUsager usager, Montant montant);
}
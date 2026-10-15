package fr.greenmove.facturation.domaine.ports;

import java.util.Optional;

import fr.greenmove.facturation.domaine.IdUsager;
import fr.greenmove.facturation.domaine.Montant;
import fr.greenmove.facturation.domaine.ReferencePaiement;

public interface Paiement {

    Optional<ReferencePaiement> debiter(IdUsager usager, Montant montant);
}
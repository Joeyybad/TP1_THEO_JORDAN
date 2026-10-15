package fr.greenmove.facturation.domaine.ports;

import java.util.Optional;

import fr.greenmove.facturation.domaine.Facture;
import fr.greenmove.facturation.domaine.IdFacture;
import fr.greenmove.facturation.domaine.IdLocation;

public interface Factures {

    void enregistrer(Facture facture);

    Optional<Facture> parId(IdFacture id);

    Optional<Facture> parIdLocation(IdLocation idLocation);
}


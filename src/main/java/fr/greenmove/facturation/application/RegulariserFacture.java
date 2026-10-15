package fr.greenmove.facturation.application;

import fr.greenmove.facturation.domaine.Facture;
import fr.greenmove.facturation.domaine.IdFacture;

public interface RegulariserFacture {
    Facture regulariser(IdFacture idFacture);
}

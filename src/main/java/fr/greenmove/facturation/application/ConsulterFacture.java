package fr.greenmove.facturation.application;

import java.util.Optional;

import fr.greenmove.facturation.domaine.Facture;
import fr.greenmove.facturation.domaine.IdFacture;

public interface ConsulterFacture {
    Optional<Facture> consulter(IdFacture id);
}

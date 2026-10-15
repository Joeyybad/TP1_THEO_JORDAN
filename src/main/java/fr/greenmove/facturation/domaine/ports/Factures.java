package fr.greenmove.facturation.domaine.ports;

import java.util.List;
import java.util.Optional;
import fr.greenmove.facturation.domaine.*;

public interface Factures {
    void enregistrer(Facture facture);
    Optional<Facture> parId(IdFacture id);
    Optional<Facture> parLocation(IdLocation idLocation);
    List<Facture> impayeesDe(IdUsager usager);
}

package fr.greenmove.facturation.infrastructure.paiement;

import java.util.Optional;

import fr.greenmove.exterieur.PaiementSimule;
import fr.greenmove.facturation.domaine.IdUsager;
import fr.greenmove.facturation.domaine.Montant;
import fr.greenmove.facturation.domaine.ReferencePaiement;
import fr.greenmove.facturation.domaine.ports.Paiement;

public class PaiementParSimulation implements Paiement {
    private final PaiementSimule simulateur;

    public PaiementParSimulation(PaiementSimule simulateur) {
        this.simulateur = simulateur;
    }

    @Override
    public Optional<ReferencePaiement> debiter(IdUsager usager, Montant montant) {
        String reference = simulateur.debiter(usager.valeur(), montant.getCentimes());

        return Optional.ofNullable(reference)
                .filter(ref -> !ref.isBlank())
                .map(ReferencePaiement::new);
    }
}
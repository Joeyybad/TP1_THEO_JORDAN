package fr.greenmove.facturation.infrastructure.rest.dto;

import fr.greenmove.facturation.domaine.Facture;
import fr.greenmove.facturation.domaine.ReferencePaiement;

public record FactureDto(
        String id,
        String location,
        String usager,
        long montantCentimes,
        String statut,
        String referencePaiement) {
    public static FactureDto depuis(Facture facture) {
        String refPaiement = facture.referencePaiement()
                .map(ReferencePaiement::valeur)
                .orElse(null);

        return new FactureDto(
                facture.id().valeur(),
                facture.idLocation().valeur(),
                facture.idUsager().valeur(),
                facture.montant().getCentimes(),
                facture.statut().name(),
                refPaiement);
    }
}
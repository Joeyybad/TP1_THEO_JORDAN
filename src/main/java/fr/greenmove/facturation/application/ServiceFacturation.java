package fr.greenmove.facturation.application;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import fr.greenmove.facturation.domaine.*;
import fr.greenmove.facturation.domaine.erreurs.FactureDejaPayee;
import fr.greenmove.facturation.domaine.erreurs.FactureInconnue;
import fr.greenmove.facturation.domaine.erreurs.PaiementEncoreRefuse;
import fr.greenmove.facturation.domaine.ports.Abonnements;
import fr.greenmove.facturation.domaine.ports.Factures;
import fr.greenmove.facturation.domaine.ports.Paiement;

public class ServiceFacturation implements EmettreFacture, ConsulterFacture, RegulariserFacture, ConsulterImpayes {

    private final Factures factures;
    private final Paiement paiement;
    private final Abonnements abonnements;
    private final GrilleTarifaire grilleTarifaire;

    public ServiceFacturation(
            Factures factures,
            Paiement paiement,
            Abonnements abonnements,
            GrilleTarifaire grilleTarifaire) {
        this.factures = factures;
        this.paiement = paiement;
        this.abonnements = abonnements;
        this.grilleTarifaire = grilleTarifaire;
    }

    @Override
    public Facture emettre(IdLocation location, IdUsager usager, TypeVehicule type, Duration duree) {

        // Vérification de l'existence de la facture pour la location (idempotence)
        Optional<Facture> factureExistante = factures.parLocation(location);
        if (factureExistante.isPresent()) {
            return factureExistante.get();
        }

        // Calcul du montant en fonction de la grille et de l'abonnement
        boolean abonne = abonnements.estAbonne(usager);
        Montant montant = grilleTarifaire.calculer(duree, type, abonne);

        // Débit chez le prestataire
        Optional<ReferencePaiement> refPaiement = paiement.debiter(usager, montant);

        IdFacture nouvelId = new IdFacture(UUID.randomUUID().toString());
        Instant dateEmission = Instant.now();

        Facture facture = refPaiement
                .map(ref -> Facture.emettrePayee(nouvelId, usager, location, montant, dateEmission, ref))
                .orElseGet(() -> Facture.emettreImpayee(nouvelId, usager, location, montant, dateEmission));

        factures.enregistrer(facture);
        return facture;
    }

    @Override
    public Optional<Facture> consulter(IdFacture id) {
        return factures.parId(id);
    }

    @Override
    public Facture regulariser(IdFacture idFacture) {
        Facture facture = factures.parId(idFacture)
                .orElseThrow(() -> new FactureInconnue(idFacture.valeur()));

        if (facture.statut() == Statut.PAYEE) {
            throw new FactureDejaPayee();
        }

        Optional<ReferencePaiement> refPaiement = paiement.debiter(facture.idUsager(), facture.montant());
        if (refPaiement.isPresent()) {
            facture.regulariser(refPaiement.get());
            factures.enregistrer(facture);
            return facture;
        }

        throw new PaiementEncoreRefuse(idFacture.valeur());
    }

    @Override
    public boolean aDesImpayes(String idUsager) {
        IdUsager usager = new IdUsager(idUsager);
        return !factures.impayeesDe(usager).isEmpty();
    }
}
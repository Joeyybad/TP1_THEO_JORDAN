package fr.greenmove.facturation.application;

import static org.assertj.core.api.Assertions.*;

import java.time.Duration;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.greenmove.facturation.domaine.*;
import fr.greenmove.facturation.domaine.ports.Abonnements;
import fr.greenmove.facturation.domaine.ports.Factures;
import fr.greenmove.facturation.domaine.ports.Paiement;
import fr.greenmove.facturation.infrastructure.persistance.FacturesEnMemoire;

class ServiceFacturationTest {

    private Factures factures;
    private GrilleTarifaire grille;
    private ServiceFacturation service;

    private final IdLocation idLoc = new IdLocation("loc-100");
    private final IdUsager idUsr = new IdUsager("usr-1");

    @BeforeEach
    void setUp() {
        factures = new FacturesEnMemoire();
        grille = new GrilleTarifaire();
    }

    @Test
    @DisplayName("Débit accepté -> la facture est PAYEE avec sa référence")
    void debit_accepte_donne_facture_payee() {
        Paiement paiementOk = (usager, montant) -> Optional.of(new ReferencePaiement("ref-pay-123"));
        Abonnements abonnements = usager -> false;

        service = new ServiceFacturation(factures, paiementOk, abonnements, grille);

        Facture facture = service.emettre(idLoc, idUsr, TypeVehicule.VELO, Duration.ofMinutes(15));

        assertThat(facture.statut()).isEqualTo(Statut.PAYEE);
        assertThat(facture.referencePaiement()).isPresent();
        assertThat(facture.referencePaiement().get().valeur()).isEqualTo("ref-pay-123");
        assertThat(factures.parLocation(idLoc)).isPresent();
    }

    @Test
    @DisplayName("Débit refusé -> facture IMPAYEE sans lever d'exception")
    void debit_refuse_donne_facture_impayee_sans_exception() {
        Paiement paiementRefuse = (usager, montant) -> Optional.empty(); // refus nominal
        Abonnements abonnements = usager -> false;

        service = new ServiceFacturation(factures, paiementRefuse, abonnements, grille);

        Facture facture = service.emettre(idLoc, idUsr, TypeVehicule.VELO, Duration.ofMinutes(15));

        assertThat(facture.statut()).isEqualTo(Statut.IMPAYEE);
        assertThat(facture.referencePaiement()).isEmpty();
        assertThat(factures.impayeesDe(idUsr)).contains(facture);
    }

    @Test
    @DisplayName("Idempotence -> émettre deux fois renvoie la même facture sans re-débiter")
    void emission_multiple_renvoie_meme_facture() {
        Paiement paiementOk = (usager, montant) -> Optional.of(new ReferencePaiement("ref-unique"));
        Abonnements abonnements = usager -> false;

        service = new ServiceFacturation(factures, paiementOk, abonnements, grille);

        Facture f1 = service.emettre(idLoc, idUsr, TypeVehicule.VELO, Duration.ofMinutes(15));
        Facture f2 = service.emettre(idLoc, idUsr, TypeVehicule.VELO, Duration.ofMinutes(15));

        assertThat(f1.id()).isEqualTo(f2.id());
        assertThat(f1.statut()).isEqualTo(f2.statut());
    }
}
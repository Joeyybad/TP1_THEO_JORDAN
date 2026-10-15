package fr.greenmove.facturation.domaine;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

public class Facture {

    private final IdFacture id;
    private final IdUsager idUsager;
    private final IdLocation idLocation;
    private final Montant montant;
    private final Instant dateEmission;
    private Statut statut;
    private ReferencePaiement referencePaiement;

    private Facture(
            IdFacture id,
            IdUsager idUsager,
            IdLocation idLocation,
            Montant montant,
            Instant dateEmission,
            Statut statut,
            ReferencePaiement referencePaiement) {
        this.id = Objects.requireNonNull(id);
        this.idUsager = Objects.requireNonNull(idUsager);
        this.idLocation = Objects.requireNonNull(idLocation);
        this.montant = Objects.requireNonNull(montant);
        this.dateEmission = Objects.requireNonNull(dateEmission);
        this.statut = Objects.requireNonNull(statut);
        this.referencePaiement = referencePaiement;
    }

    public static Facture emettrePayee(
            IdFacture id,
            IdUsager usager,
            IdLocation location,
            Montant montant,
            Instant dateEmission,
            ReferencePaiement reference) {
        return new Facture(id, usager, location, montant, dateEmission, Statut.PAYEE,
                Objects.requireNonNull(reference));
    }

    public static Facture emettreImpayee(
            IdFacture id,
            IdUsager usager,
            IdLocation location,
            Montant montant,
            Instant dateEmission) {
        return new Facture(id, usager, location, montant, dateEmission, Statut.IMPAYEE, null);
    }

    public void regulariser(ReferencePaiement reference) {
        if (this.statut == Statut.PAYEE) {
            throw new IllegalStateException("Facture déjà payée");
        }
        this.statut = Statut.PAYEE;
        this.referencePaiement = Objects.requireNonNull(reference);
    }

    public static Facture reconstituer(
            IdFacture id,
            IdUsager usager,
            IdLocation location,
            Montant montant,
            Instant dateEmission,
            Statut statut,
            ReferencePaiement referencePaiement) {
        return new Facture(id, usager, location, montant, dateEmission, statut, referencePaiement);
    }

    public IdFacture id() {
        return id;
    }

    public IdUsager idUsager() {
        return idUsager;
    }

    public IdLocation idLocation() {
        return idLocation;
    }

    public Montant montant() {
        return montant;
    }

    public Instant dateEmission() {
        return dateEmission;
    }

    public Statut statut() {
        return statut;
    }

    public Optional<ReferencePaiement> referencePaiement() {
        return Optional.ofNullable(referencePaiement);
    }
}
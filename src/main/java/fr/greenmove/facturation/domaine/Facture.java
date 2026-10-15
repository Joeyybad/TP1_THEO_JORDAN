package fr.greenmove.facturation.domaine;

import java.time.Instant;
import java.util.Objects;

public class Facture {

    private final IdFacture id;
    private final IdUsager idUsager;
    private final IdLocation idLocation;
    private final Montant montant;
    private final Instant dateEmission;
    private Statut statut;

    private Facture(
            IdFacture id,
            IdUsager idUsager,
            IdLocation idLocation,
            Montant montant,
            Instant dateEmission,
            Statut statut) {
        this.id = Objects.requireNonNull(id, "L'identifiant de facture est obligatoire");
        this.idUsager = Objects.requireNonNull(idUsager, "L'identifiant usager est obligatoire");
        this.idLocation = Objects.requireNonNull(idLocation, "L'identifiant location est obligatoire");
        this.montant = Objects.requireNonNull(montant, "Le montant est obligatoire");
        this.dateEmission = Objects.requireNonNull(dateEmission, "La date d'émission est obligatoire");
        this.statut = Objects.requireNonNull(statut, "Le statut est obligatoire");
    }

    public static Facture emettre(
            IdFacture id,
            IdUsager idUsager,
            IdLocation idLocation,
            Montant montant,
            Instant dateEmission) {
        return new Facture(id, idUsager, idLocation, montant, dateEmission, Statut.IMPAYEE);
    }

    public static Facture reconstituer(
            IdFacture id,
            IdUsager idUsager,
            IdLocation idLocation,
            Montant montant,
            Instant dateEmission,
            Statut statut) {
        return new Facture(id, idUsager, idLocation, montant, dateEmission, statut);
    }

    public void marquerCommePayee() {
        if (this.statut == Statut.PAYEE) {
            throw new IllegalStateException("Facture déjà payée : " + id.valeur());
        }
        this.statut = Statut.PAYEE;
    }

    public void marquerCommeImpayee() {
        this.statut = Statut.IMPAYEE;
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
}

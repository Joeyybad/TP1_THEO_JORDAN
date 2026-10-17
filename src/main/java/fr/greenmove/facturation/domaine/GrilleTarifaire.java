package fr.greenmove.facturation.domaine;

import java.time.Duration;

public class GrilleTarifaire {

    private static final int PRISE_EN_CHARGE_VELO = 100;
    private static final int PRIX_MINUTE_VELO = 15;

    private static final int PRISE_EN_CHARGE_TROTTINETTE = 100;
    private static final int PRIX_MINUTE_TROTTINETTE = 22;

    private static final int PLAFOND_JOURNALIER = 1500;
    private static final int REDUCTION_ABONNE_POURCENT = 20;

    public Montant calculer(
            Duration duree,
            TypeVehicule typeVehicule,
            boolean abonne) {

        Montant montant = montantDeBase(duree, typeVehicule);

        montant = montant.plafonneA(
                new Montant(PLAFOND_JOURNALIER)
        );

        if (abonne) {
            montant = montant.reduitDe(
                    REDUCTION_ABONNE_POURCENT
            );
        }

        return montant;
    }

    private Montant montantDeBase(
            Duration duree,
            TypeVehicule typeVehicule) {

        return switch (typeVehicule) {
            case VELO -> new Montant(
                    (int) (PRISE_EN_CHARGE_VELO
                            + duree.toMinutes() * PRIX_MINUTE_VELO)
            );

            case TROTTINETTE -> new Montant(
                    (int) (PRISE_EN_CHARGE_TROTTINETTE
                            + duree.toMinutes() * PRIX_MINUTE_TROTTINETTE)
            );
        };
    }
}
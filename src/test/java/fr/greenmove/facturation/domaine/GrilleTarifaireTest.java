package fr.greenmove.facturation.domaine;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.Test;

class GrilleTarifaireTest {

    private final GrilleTarifaire grille = new GrilleTarifaire();

    @Test
    void calcule_le_tarif_d_un_velo_pour_un_non_abonne() {
        Duration duree = Duration.ofMinutes(10);

        Montant montant = grille.calculer(
                duree,
                TypeVehicule.VELO,
                false
        );

        // 100 + (10 * 15) = 250 centimes
        assertThat(montant.getCentimes()).isEqualTo(250);
    }

    @Test
    void calcule_le_tarif_d_une_trottinette_pour_un_abonne() {
        Duration duree = Duration.ofMinutes(10);

        Montant montant = grille.calculer(
                duree,
                TypeVehicule.TROTTINETTE,
                true
        );

        // 100 + (10 * 22) = 320
        // réduction abonné de 20 % : 320 - 64 = 256
        assertThat(montant.getCentimes()).isEqualTo(256);
    }

    @Test
    void applique_le_plafond_journalier() {
        Duration duree = Duration.ofMinutes(200);

        Montant montant = grille.calculer(
                duree,
                TypeVehicule.VELO,
                false
        );

        // 100 + (200 * 15) = 3100
        // plafonné à 1500
        assertThat(montant.getCentimes()).isEqualTo(1500);
    }
}
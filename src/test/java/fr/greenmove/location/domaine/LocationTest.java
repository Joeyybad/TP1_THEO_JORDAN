package fr.greenmove.location.domaine;

import static org.assertj.core.api.Assertions.*;

import java.time.Instant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.greenmove.location.domaine.erreurs.DureeInvalide;
import fr.greenmove.location.domaine.erreurs.LocationDejaTerminee;

class LocationTest {

    private final IdLocation id = new IdLocation("loc-1");
    private final IdUsager usager = new IdUsager("usr-1");
    private final IdVehicule vehicule = new IdVehicule("veh-1");
    private final Instant debut = Instant.parse("2026-03-30T10:00:00Z");

    @Test
    @DisplayName("Démarrer une location initialise son statut à EN_COURS")
    void demarrage_nominal() {
        Location location = Location.demarrer(id, usager, vehicule, TypeVehicule.VELO, debut);

        assertThat(location.statut()).isEqualTo(Statut.EN_COURS);
        assertThat(location.debut()).isEqualTo(debut);
        assertThat(location.fin()).isNull();
    }

    @Test
    @DisplayName("Terminer une location enregistre la fin et émet l'événement")
    void terminer_nominal() {
        Location location = Location.demarrer(id, usager, vehicule, TypeVehicule.VELO, debut);
        Instant fin = debut.plusSeconds(1800); // +30 minutes

        location.terminer(fin);

        assertThat(location.statut()).isEqualTo(Statut.TERMINEE);
        assertThat(location.fin()).isEqualTo(fin);
        assertThat(location.evenementsEmis()).hasSize(1);
    }

    @Test
    @DisplayName("Invariant : interdire de terminer deux fois la même location")
    void terminer_deux_fois_interdit() {
        Location location = Location.demarrer(id, usager, vehicule, TypeVehicule.VELO, debut);
        location.terminer(debut.plusSeconds(600));

        assertThatThrownBy(() -> location.terminer(debut.plusSeconds(1200)))
                .isInstanceOf(LocationDejaTerminee.class);
    }

    @Test
    @DisplayName("Invariant : la fin ne peut pas être antérieure au début")
    void fin_avant_debut_interdit() {
        Location location = Location.demarrer(id, usager, vehicule, TypeVehicule.VELO, debut);
        Instant avantDebut = debut.minusSeconds(60);

        assertThatThrownBy(() -> location.terminer(avantDebut))
                .isInstanceOf(DureeInvalide.class);
    }

    @Test
    @DisplayName("La durée est calculée en minutes entières arrondies vers le bas")
    void duree_minutes_entieres() {
        Location location = Location.demarrer(
                id, usager, vehicule, TypeVehicule.VELO, debut);

        location.terminer(debut.plusSeconds(299)); // 4 min 59 sec

        assertThat(location.duree()).isEqualTo(4);
    }
}
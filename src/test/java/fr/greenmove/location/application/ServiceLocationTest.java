package fr.greenmove.location.application;

import static org.assertj.core.api.Assertions.*;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.greenmove.location.domaine.*;
import fr.greenmove.location.domaine.Ports.Flotte;
import fr.greenmove.location.domaine.Ports.Impayes;
import fr.greenmove.location.domaine.Ports.PublicationEvenements;
import fr.greenmove.location.domaine.Ports.Usagers;
import fr.greenmove.location.domaine.erreurs.LocationDejaEnCours;
import fr.greenmove.location.domaine.erreurs.VehiculeIndisponible;
import fr.greenmove.location.infrastructure.HorlogeFixe;
import fr.greenmove.location.infrastructure.LocationsEnMemoire;

class ServiceLocationTest {

    private LocationsEnMemoire locations;
    private HorlogeFixe horloge;
    private ServiceLocation service;

    private final IdUsager usagerId = new IdUsager("usr-1");
    private final IdVehicule vehiculeId = new IdVehicule("veh-1");
    private final Instant instantTest = Instant.parse("2026-03-30T10:00:00Z");

    @BeforeEach
    void setup() {
        locations = new LocationsEnMemoire();
        horloge = new HorlogeFixe(instantTest);

        // Doubles manuels pour Flotte, Usagers, Impayes et Evenements
        Flotte flotte = id -> Optional.of(new Vehicule(id, TypeVehicule.VELO, true));
        Usagers usagers = id -> true; // on considère que l'usager existe toujours
        Impayes impayesSansDette = id -> false; // dit toujours non !
        GenerateurIdentifiants identifiants = new GenerateurIdentifiants();
        PublicationEvenements publication = evenement -> {
        };

        service = new ServiceLocation(
                locations,
                usagers,
                flotte,
                impayesSansDette,
                horloge,
                identifiants,
                publication);
    }

    @Test
    @DisplayName("Démarrer une location avec succès enregistre la location dans la Map")
    void demarrage_reussi() {
        IdLocation nouvelleId = service.demarrer(usagerId, vehiculeId);

        assertThat(nouvelleId).isNotNull();
        Optional<Location> enregistree = locations.parId(nouvelleId);
        assertThat(enregistree).isPresent();
        assertThat(enregistree.get().statut()).isEqualTo(Statut.EN_COURS);
    }

    @Test
    @DisplayName("Interdire de démarrer si l'usager a déjà une location en cours")
    void refus_si_location_deja_en_cours() {
        // 1er démarrage
        service.demarrer(usagerId, vehiculeId);

        // 2e démarrage -> doit lever l'exception métier
        assertThatThrownBy(() -> service.demarrer(usagerId, vehiculeId))
                .isInstanceOf(LocationDejaEnCours.class);
    }

    @Test
    @DisplayName("Interdire de démarrer si le véhicule n'est pas disponible")
    void refus_si_vehicule_indisponible() {
        Flotte flotteAvecVehiculePris = id -> Optional.of(new Vehicule(id, TypeVehicule.VELO, false));
        ServiceLocation serviceVehiculeOccupe = new ServiceLocation(
                locations,
                id -> true,
                flotteAvecVehiculePris,
                id -> false,
                horloge,
                new GenerateurIdentifiants(),
                evt -> {
                });

        assertThatThrownBy(() -> serviceVehiculeOccupe.demarrer(usagerId, vehiculeId))
                .isInstanceOf(VehiculeIndisponible.class);
    }
}
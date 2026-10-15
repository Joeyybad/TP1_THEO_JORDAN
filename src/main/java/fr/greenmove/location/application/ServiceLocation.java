package fr.greenmove.location.application;

import java.util.Optional;

import fr.greenmove.location.domaine.*;
import fr.greenmove.location.domaine.Ports.*;
import fr.greenmove.location.domaine.erreurs.*;

public class ServiceLocation implements DemarrerLocation, TerminerLocation, ConsulterLocation {

    private final Locations locations;
    private final Usagers usagers;
    private final Flotte flotte;
    private final Impayes impayes;
    private final Horloge horloge;
    private final GenerateurIdentifiants generateurIdentifiants;
    private final PublicationEvenements publicationEvenements;

    public ServiceLocation(
            Locations locations,
            Usagers usagers,
            Flotte flotte,
            Impayes impayes,
            Horloge horloge,
            GenerateurIdentifiants generateurIdentifiants, PublicationEvenements publicationEvenements) {
        this.locations = locations;
        this.usagers = usagers;
        this.flotte = flotte;
        this.impayes = impayes;
        this.horloge = horloge;
        this.generateurIdentifiants = generateurIdentifiants;
        this.publicationEvenements = publicationEvenements;
    }

    // demarrerLocation
    @Override
    public IdLocation demarrer(IdUsager usagerId, IdVehicule vehiculeId) {
        // 1. Le véhicule existe et est disponible (port Flotte)
        Vehicule vehicule = flotte.vehicule(vehiculeId)
                .orElseThrow(() -> new VehiculeInconnu(vehiculeId));

        if (!vehicule.estDisponible()) {
            throw new VehiculeIndisponible(vehiculeId);
        }

        // 2. L'usager existe (port Usagers)
        if (usagers.existe(usagerId)) {
            throw new UsagerInconnu(usagerId);
        }

        // 3. L'usager n'a pas de location en cours (port Locations)
        if (locations.enCoursPour(usagerId).isPresent()) {
            throw new LocationDejaEnCours(usagerId);
        }

        // 4. L'usager n'a pas de facture impayée (port Impayes)
        if (impayes.aDesImpayes(usagerId)) {
            throw new UsagerBloquePourImpayes(usagerId);
        }

        // Toutes les conditions sont réunies : création dans le Domaine
        IdLocation nouvelleId = generateurIdentifiants.prochainIdLocation();
        Location location = Location.demarrer(
                nouvelleId,
                usagerId,
                vehicule.id(),
                vehicule.type(),
                horloge.maintenant());

        // Persistance via le port sortant
        locations.enregistrer(location);

        return nouvelleId;
    }

    // terminerLocation
    @Override
    public Recu terminer(IdLocation idLocation) {
        Location location = locations.parId(idLocation)
                .orElseThrow(() -> new LocationInconnue(idLocation));

        location.terminer(horloge.maintenant());

        locations.enregistrer(location);

        if (publicationEvenements != null) {
            location.evenementsEmis().forEach(publicationEvenements::publier);
        }

        return new Recu(idLocation, location.debut(), location.fin(), location.duree());
    }

    // ConsulterLocation
    @Override
    public Optional<Location> consulter(IdLocation idLocation) {
        return locations.parId(idLocation);
    }

}
package fr.greenmove.location.domaine;

import java.time.Duration;
// CHECKER POUR INSTANT
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.greenmove.location.domaine.erreurs.DureeInvalide;
import fr.greenmove.location.domaine.erreurs.LocationDejaTerminee;
import fr.greenmove.location.domaine.evenements.LocationTerminee;

public class Location {
    private final IdLocation id;
    private final IdUsager usager;
    private final IdVehicule vehicule;
    private final TypeVehicule type;
    private final Instant debut;
    private Instant fin;
    private Statut statut = Statut.EN_COURS;
    private final List<LocationTerminee> evenements = new ArrayList<>();

    private Location(IdLocation id, IdUsager usager, IdVehicule vehicule, TypeVehicule type, Instant debut) {
        this.id = id;
        this.usager = usager;
        this.vehicule = vehicule;
        this.type = type;
        this.debut = debut;

    }

    public static Location demarrer(IdLocation id, IdUsager usager, IdVehicule vehicule, TypeVehicule type,
            Instant maintenant) {
        if (maintenant == null) {
            throw new IllegalArgumentException("La date de début ne peut pas être nulle");
        }
        return new Location(id, usager, vehicule, type, maintenant);
    }

    public void terminer(Instant maintenant) {
        // Invariant 1 : pas deux fois
        if (this.statut != Statut.EN_COURS) {
            throw new LocationDejaTerminee(this.id);
        }

        // Invariant 2 : fin après début
        if (maintenant.isBefore(this.debut)) {
            throw new DureeInvalide(this.id);
        }

        this.fin = maintenant;
        this.statut = Statut.TERMINEE;

        // Invariant 3 : calcul de durée en minutes
        Duration duree = Duration.between(this.debut, this.fin);

        // On émet l'événement de domaine sans argent : Facturation s'en chargera !
        this.evenements.add(new LocationTerminee(
                this.id.valeur(),
                this.usager.valeur(),
                this.type.name(),
                this.debut,
                this.fin));
    }

    public IdLocation id() {
        return id;
    }

    public IdUsager usager() {
        return usager;
    }

    public IdVehicule vehicule() {
        return vehicule;
    }

    public TypeVehicule type() {
        return type;
    }

    public Instant debut() {
        return debut;
    }

    public Instant fin() {
        return fin;
    }

    public Statut statut() {
        return statut;
    }

    public Duration duree() {
        return Duration.between(this.debut, this.fin);
    }

    public List<LocationTerminee> evenementsEmis() {
        return Collections.unmodifiableList(evenements);
    }

    // Fabrique static pour reconstituer le mappage de l'entité JPA vers le domaine
    public static Location reconstituer(
            IdLocation id,
            IdUsager usager,
            IdVehicule vehicule,
            TypeVehicule type,
            Instant debut,
            Instant fin,
            Statut statut) {
        Location location = new Location(id, usager, vehicule, type, debut);
        location.fin = fin;
        location.statut = statut;
        return location;
    }
}
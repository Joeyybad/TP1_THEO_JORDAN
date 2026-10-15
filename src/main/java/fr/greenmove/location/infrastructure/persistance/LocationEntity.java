package fr.greenmove.location.infrastructure.persistance;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "location")
class LocationEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "usager_id", nullable = false)
    private String usagerId;

    @Column(name = "vehicule_id", nullable = false)
    private String vehiculeId;

    @Column(name = "type_vehicule", nullable = false)
    private String typeVehicule;

    @Column(name = "date_debut", nullable = false)
    private Instant debut;

    @Column(name = "date_fin")
    private Instant fin;

    @Column(name = "statut", nullable = false)
    private String statut;

    // Constructeur vide sans argument obligatoire pour JPA
    protected LocationEntity() {
    }

    LocationEntity(String id, String usagerId, String vehiculeId, String typeVehicule, Instant debut, Instant fin,
            String statut) {
        this.id = id;
        this.usagerId = usagerId;
        this.vehiculeId = vehiculeId;
        this.typeVehicule = typeVehicule;
        this.debut = debut;
        this.fin = fin;
        this.statut = statut;
    }

    String getId() {
        return id;
    }

    String getUsagerId() {
        return usagerId;
    }

    String getVehiculeId() {
        return vehiculeId;
    }

    String getTypeVehicule() {
        return typeVehicule;
    }

    Instant getDebut() {
        return debut;
    }

    Instant getFin() {
        return fin;
    }

    String getStatut() {
        return statut;
    }
}
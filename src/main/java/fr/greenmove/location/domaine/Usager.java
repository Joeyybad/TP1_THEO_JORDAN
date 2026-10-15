package fr.greenmove.location.domaine;

import java.util.Objects;

/**
 * Ce que le domaine de Location a besoin de savoir d'un usager :
 * son identifiant et s'il bénéficie d'un abonnement.
 */
public record Usager(IdUsager id, boolean abonne) {

    public Usager {
        Objects.requireNonNull(id, "L'identifiant de l'usager est obligatoire");
    }
}
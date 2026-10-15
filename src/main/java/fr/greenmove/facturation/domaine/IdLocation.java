package fr.greenmove.facturation.domaine;

/** Identifiant typé d'une Location */
public record IdLocation(String valeur) {
    public IdLocation {
        if (valeur == null || valeur.isBlank()) {
            throw new IllegalArgumentException("Identifiant de la location vide");
        }
    }
}

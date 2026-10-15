package fr.greenmove.facturation.domaine;

/** Identifiant typé d'un usager */
public record IdUsager(String valeur) {
    public IdUsager {
        if (valeur == null || valeur.isBlank()) {
            throw new IllegalArgumentException("Identifiant de l'usager vide");
        }
    }
}

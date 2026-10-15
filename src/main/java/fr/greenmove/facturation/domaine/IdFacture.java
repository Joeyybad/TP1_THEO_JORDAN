package fr.greenmove.facturation.domaine;

/** Identifiant typé d'une Location */
public record IdFacture(String valeur) {
    public IdFacture {
        if (valeur == null || valeur.isBlank()) {
            throw new IllegalArgumentException("Identifiant de la facture vide");
        }
    }
}

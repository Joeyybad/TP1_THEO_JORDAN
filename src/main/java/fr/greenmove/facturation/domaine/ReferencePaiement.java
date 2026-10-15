package fr.greenmove.facturation.domaine;

import java.util.Objects;

public record ReferencePaiement(String valeur) {
    public ReferencePaiement {
        Objects.requireNonNull(valeur, "La référence de paiement est obligatoire");
    }
}

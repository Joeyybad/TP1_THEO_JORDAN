package fr.greenmove.facturation.domaine.erreurs;

public class PaiementEncoreRefuse extends RuntimeException {
    public PaiementEncoreRefuse(String id) {
        super("Le paiement " + id + " a encore été refusé");
    }
}

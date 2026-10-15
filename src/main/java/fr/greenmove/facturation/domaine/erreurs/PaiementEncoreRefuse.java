package fr.greenmove.facturation.domaine.erreurs;

public class PaiementEncoreRefuse extends {
    public PaiementEncoreRefuse(String id) {
        super("Le paiement " + id + " à encore été refusé");
    }
}

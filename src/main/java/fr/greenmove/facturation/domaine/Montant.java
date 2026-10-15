package fr.greenmove.facturation.domaine;

public final class Montant {

    private final int centimes;

    public Montant(int centimes) {
        this.centimes = centimes;
    }

    public int getCentimes() {
        return centimes;
    }

    public double getEuros() {
        return centimes / 100.0;
    }

    public Montant plafonneA(Montant plafond) {
        return centimes > plafond.getCentimes() ? plafond : this;
    }

    public Montant reduitDe(int pourcent) {
        int remise = centimes * pourcent / 100;
        return new Montant(centimes - remise);
    }
}

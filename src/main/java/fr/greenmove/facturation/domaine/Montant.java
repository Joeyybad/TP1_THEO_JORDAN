package fr.greenmove.facturation.domaine;

public record Montant(long centimes) {

    public static final Montant ZERO = new Montant(0);

    public Montant {
        if (centimes < 0)
            throw new IllegalArgumentException("Montant Négatif");
    }

    public Montant plus(Montant autre) {
        return new Montant(this.centimes + autre.centimes);
    }

    public Montant fois(long n) {
        return new Montant(this.centimes * n);
    }

    public Montant plafondA(Montant max) {
        return this.centimes > max.centimes ? max : this;
    }

    public Montant moinsPourcent(int pourcent) {
        return new Montant(centimes - centimes * pourcent / 100);
    }
}

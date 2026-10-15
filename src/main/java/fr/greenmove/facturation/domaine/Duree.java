package fr.greenmove.facturation.domaine;

public final class Duree {

    private final long minutes;

    public Duree(long minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("Durée négative : " + minutes + " minute(s)");
        }
        this.minutes = minutes;
    }

    public long getMinutes() {
        return minutes;
    }
}
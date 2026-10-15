package fr.greenmove.location.domaine;

import java.util.UUID;

public class GenerateurIdentifiants {

    public IdLocation prochainIdLocation() {
        return new IdLocation(UUID.randomUUID().toString());
    }
}
package fr.greenmove.location.domaine.evenements;

import java.time.Instant;

/**
 * L'événement que Location publie quand une location se termine. C'est le
 * <b>contrat</b> entre les deux hexagones.
 *
 * <p>
 * Il ne transporte que des types simples : l'hexagone qui l'écoute n'a pas à
 * importer les value objects de
 * Location. Il dit ce qui s'est passé (des faits), pas ce qu'il faut en faire :
 * Location ne sait pas qu'une
 * facture va en sortir. Ce fichier est fourni pour que les deux moitiés du
 * binôme travaillent sur le même contrat :
 * vous pouvez l'enrichir, pas le remplacer par un objet du domaine.
 * </p>
 *
 * @param idLocation   l'identifiant de la location
 * @param idUsager     l'usager qui a loué
 * @param typeVehicule {@code VELO} ou {@code TROTTINETTE}
 * @param debut        le début de la location
 * @param fin          la fin de la location
 */
public record LocationTerminee(String idLocation, String idUsager, String typeVehicule, Instant debut, Instant fin) {

    public LocationTerminee {
        if (idLocation == null || idLocation.isBlank()) {
            throw new IllegalArgumentException("idLocation ne peut pas être vide");
        }
        if (idUsager == null || idUsager.isBlank()) {
            throw new IllegalArgumentException("idUsager ne peut pas être vide");
        }
        if (typeVehicule == null || typeVehicule.isBlank()) {
            throw new IllegalArgumentException("typeVehicule ne peut pas être vide");
        }
        if (debut == null) {
            throw new IllegalArgumentException("debut ne peut pas être nul");
        }
        if (fin == null) {
            throw new IllegalArgumentException("fin ne peut pas être nul");
        }
    }
}

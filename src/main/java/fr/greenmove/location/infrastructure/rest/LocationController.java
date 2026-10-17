package fr.greenmove.location.infrastructure.rest;

import java.net.URI;

import fr.greenmove.location.infrastructure.rest.dto.LocationCreeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.greenmove.location.application.ConsulterLocation;
import fr.greenmove.location.application.DemarrerLocation;
import fr.greenmove.location.application.Recu;
import fr.greenmove.location.application.TerminerLocation;
import fr.greenmove.location.domaine.IdLocation;
import fr.greenmove.location.domaine.IdUsager;
import fr.greenmove.location.domaine.IdVehicule;
import fr.greenmove.location.domaine.erreurs.*;
import fr.greenmove.location.infrastructure.rest.dto.DemandeDemarrageDto;
import fr.greenmove.location.infrastructure.rest.dto.LocationDto;
import fr.greenmove.location.infrastructure.rest.dto.RecuDto;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final DemarrerLocation demarrerLocation;
    private final TerminerLocation terminerLocation;
    private final ConsulterLocation consulterLocation;

    public LocationController(DemarrerLocation demarrer, TerminerLocation terminer, ConsulterLocation consulter) {
        this.demarrerLocation = demarrer;
        this.terminerLocation = terminer;
        this.consulterLocation = consulter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> consulter(@PathVariable String id) {
        return consulterLocation.consulter(new IdLocation(id))
                .map(location -> ResponseEntity.ok(new LocationDto(location)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LocationCreeDto> demarrer(
            @RequestBody DemandeDemarrageDto demande) {

        try {
            IdLocation idCree = demarrerLocation.demarrer(
                    new IdUsager(demande.usager()),
                    new IdVehicule(demande.vehicule())
            );

            var reponse = new LocationCreeDto(
                    idCree.valeur(),
                    "EN_COURS"
            );

            return ResponseEntity
                    .created(URI.create("/locations/" + idCree.valeur()))
                    .body(reponse);

        } catch (UsagerInconnu | VehiculeInconnu e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();

        } catch (VehiculeIndisponible
                 | LocationDejaEnCours
                 | UsagerBloquePourImpayes e) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        }
    }

    @PostMapping("/{id}/fin")
    public ResponseEntity<RecuDto> terminer(@PathVariable String id) {
        try {
            Recu recu = terminerLocation.terminer(new IdLocation(id));
            return ResponseEntity.ok(new RecuDto(recu));
        } catch (LocationInconnue e) {
            return ResponseEntity.notFound().build();
        } catch (LocationDejaTerminee e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
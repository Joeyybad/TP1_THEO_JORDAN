package fr.greenmove.facturation.infrastructure.rest;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.greenmove.facturation.application.ConsulterFacture;
import fr.greenmove.facturation.application.RegulariserFacture;
import fr.greenmove.facturation.domaine.Facture;
import fr.greenmove.facturation.domaine.IdFacture;
import fr.greenmove.facturation.domaine.IdLocation;
import fr.greenmove.facturation.domaine.erreurs.FactureDejaPayee;
import fr.greenmove.facturation.domaine.erreurs.FactureInconnue;
import fr.greenmove.facturation.domaine.erreurs.PaiementEncoreRefuse;
import fr.greenmove.facturation.domaine.ports.Factures;
import fr.greenmove.facturation.infrastructure.rest.dto.FactureDto;

@RestController
@RequestMapping("/factures")
public class FacturationController {

    private final ConsulterFacture consulterFacture;
    private final RegulariserFacture regulariserFacture;
    private final Factures factures;

    public FacturationController(
            ConsulterFacture consulterFacture,
            RegulariserFacture regulariserFacture,
            Factures factures) {
        this.consulterFacture = consulterFacture;
        this.regulariserFacture = regulariserFacture;
        this.factures = factures;
    }

    // GET /factures?location={idLocation} -> 200 ou 404
    @GetMapping(params = "location")
    public ResponseEntity<FactureDto> factureDUneLocation(@RequestParam("location") String idLocation) {
        Optional<Facture> facture = factures.parLocation(new IdLocation(idLocation));
        return facture.map(f -> ResponseEntity.ok(FactureDto.depuis(f)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET /factures/{id} -> 200 ou 404
    @GetMapping("/{id}")
    public ResponseEntity<FactureDto> consulter(@PathVariable String id) {
        return consulterFacture.consulter(new IdFacture(id))
                .map(f -> ResponseEntity.ok(FactureDto.depuis(f)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /factures/{id}/regularisation -> 200 (PAYEE), 404, 409, 502
    @PostMapping("/{id}/regularisation")
    public ResponseEntity<FactureDto> regulariser(@PathVariable String id) {
        Facture facture = regulariserFacture.regulariser(new IdFacture(id));
        return ResponseEntity.ok(FactureDto.depuis(facture));
    }

    // --- Gestion des statuts HTTP ---

    @ExceptionHandler(FactureInconnue.class)
    public ResponseEntity<Void> gererFactureInconnue() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
    }

    @ExceptionHandler(FactureDejaPayee.class)
    public ResponseEntity<Void> gererFactureDejaPayee() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409
    }

    @ExceptionHandler(PaiementEncoreRefuse.class)
    public ResponseEntity<Void> gererPaiementEncoreRefuse() {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build(); // 502
    }
}
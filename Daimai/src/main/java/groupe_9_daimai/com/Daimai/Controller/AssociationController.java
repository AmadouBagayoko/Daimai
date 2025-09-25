package groupe_9_daimai.com.Daimai.Controller;

import groupe_9_daimai.com.Daimai.DTO.*;
import groupe_9_daimai.com.Daimai.Entite.RapportScolaire;
import groupe_9_daimai.com.Daimai.Service.AssociationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/associations")
@CrossOrigin(origins = "*")
@Validated
public class AssociationController {

    @Autowired
    private AssociationService associationService;

    // === GESTION DES ASSOCIATIONS ===

    @PostMapping
    public ResponseEntity<?> createAssociation(@Valid @RequestBody AssociationRequestDTO associationRequestDTO) {
        try {
            AssociationResponseDTO association = associationService.createAssociation(associationRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(association);
        } catch (RuntimeException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/authentification")
    public ResponseEntity<?> authentifier(@Valid @RequestBody LoginRequestDTO loginRequest) {
        try {
            AssociationResponseDTO association = associationService.authentifier(loginRequest);
            return ResponseEntity.ok(association);
        } catch (RuntimeException e) {
            return errorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAssociations() {
        try {
            List<AssociationResponseDTO> associations = associationService.getAllAssociations();
            return ResponseEntity.ok(associations);
        } catch (RuntimeException e) {
            return errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssociationById(@PathVariable Long id) {
        try {
            AssociationResponseDTO association = associationService.getAssociationById(id);
            return ResponseEntity.ok(association);
        } catch (RuntimeException e) {
            return errorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAssociation(@PathVariable Long id, @Valid @RequestBody AssociationRequestDTO associationRequestDTO) {
        try {
            AssociationResponseDTO association = associationService.updateAssociation(id, associationRequestDTO);
            return ResponseEntity.ok(association);
        } catch (RuntimeException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<?> toggleStatutAssociation(@PathVariable Long id, @RequestParam Boolean statutBloquer) {
        try {
            AssociationResponseDTO association = associationService.toggleStatutAssociation(id, statutBloquer);
            return ResponseEntity.ok(association);
        } catch (RuntimeException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}/validation")
    public ResponseEntity<?> validerAssociation(@PathVariable Long id, @RequestParam String autorisation) {
        try {
            AssociationResponseDTO association = associationService.validerAssociation(id, autorisation);
            return ResponseEntity.ok(association);
        } catch (RuntimeException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/recherche")
    public ResponseEntity<?> searchAssociations(@RequestParam String recherche) {
        try {
            List<AssociationResponseDTO> associations = associationService.searchAssociations(recherche);
            return ResponseEntity.ok(associations);
        } catch (RuntimeException e) {
            return errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/domaine/{domaine}")
    public ResponseEntity<?> getAssociationsByDomaine(@PathVariable String domaine) {
        try {
            List<AssociationResponseDTO> associations = associationService.getAssociationsByDomaine(domaine);
            return ResponseEntity.ok(associations);
        } catch (RuntimeException e) {
            return errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/statut/{statutBloquer}")
    public ResponseEntity<?> getAssociationsByStatut(@PathVariable Boolean statutBloquer) {
        try {
            List<AssociationResponseDTO> associations = associationService.getAssociationsByStatut(statutBloquer);
            return ResponseEntity.ok(associations);
        } catch (RuntimeException e) {
            return errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssociation(@PathVariable Long id) {
        try {
            associationService.deleteAssociation(id);
            return ResponseEntity.ok(successResponse("Association supprimée avec succès"));
        } catch (RuntimeException e) {
            return errorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/statistiques")
    public ResponseEntity<?> getStatistiquesAssociation(@PathVariable Long id) {
        try {
            AssociationResponseDTO association = associationService.getAssociationById(id);
            Integer nombreEnfants = associationService.getNombreTotalEnfants(id);
            Integer nombreParrains = associationService.getNombreTotalParrains(id);

            Map<String, Object> statistiques = new HashMap<>();
            statistiques.put("association", association);
            statistiques.put("nombreEnfants", nombreEnfants);
            statistiques.put("nombreParrains", nombreParrains);

            return ResponseEntity.ok(statistiques);
        } catch (RuntimeException e) {
            return errorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // === GESTION DES ENFANTS ===

    @PostMapping("/{associationId}/enfants")
    public ResponseEntity<?> enregistrerEnfant(@PathVariable Long associationId, @Valid @RequestBody EnfantRequestDTO enfantRequestDTO) {
        try {
            enfantRequestDTO.setAssociationId(associationId);
            EnfantResponseDTO enfant = associationService.enregistrerEnfant(enfantRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(enfant);
        } catch (RuntimeException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{associationId}/enfants")
    public ResponseEntity<?> getEnfantsByAssociation(@PathVariable Long associationId) {
        try {
            List<EnfantResponseDTO> enfants = associationService.getEnfantsByAssociation(associationId);
            return ResponseEntity.ok(enfants);
        } catch (RuntimeException e) {
            return errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/enfants/{enfantId}")
    public ResponseEntity<?> getEnfantById(@PathVariable Long enfantId) {
        try {
            EnfantResponseDTO enfant = associationService.getEnfantById(enfantId);
            return ResponseEntity.ok(enfant);
        } catch (RuntimeException e) {
            return errorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/enfants/{enfantId}/statut")
    public ResponseEntity<?> toggleStatutEnfant(@PathVariable Long enfantId, @RequestParam Boolean statutAbandon) {
        try {
            EnfantResponseDTO enfant = associationService.toggleStatutEnfant(enfantId, statutAbandon);
            return ResponseEntity.ok(enfant);
        } catch (RuntimeException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // === GESTION DES PAIEMENTS ===

    @PostMapping("/paiements")
    public ResponseEntity<?> enregistrerPaiement(@Valid @RequestBody PaiementRequestDTO paiementRequestDTO) {
        try {
            PaiementResponseDTO paiement = associationService.enregistrerPaiementParrain(paiementRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(paiement);
        } catch (RuntimeException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // === GESTION DES RAPPORTS ===

    @PostMapping("/rapports-scolaires")
    public ResponseEntity<?> genererRapportScolaire(@Valid @RequestBody RapportRequestDTO rapportRequestDTO) {
        try {
            RapportScolaire rapport = associationService.genererRapportScolaire(rapportRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(rapport);
        } catch (RuntimeException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{associationId}/rapports-financiers")
    public ResponseEntity<?> genererRapportFinancier(
            @PathVariable Long associationId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        try {
            Map<String, Object> rapport = associationService.genererRapportFinancier(associationId, dateDebut, dateFin);
            return ResponseEntity.ok(rapport);
        } catch (RuntimeException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // === GESTION DES PARRAINAGES ===

    @PostMapping("/demandes-parrainage")
    public ResponseEntity<?> envoyerDemandeParrainage(@Valid @RequestBody ParrainageRequestDTO parrainageRequestDTO) {
        try {
            associationService.envoyerDemandeParrainage(parrainageRequestDTO);
            return ResponseEntity.ok(successResponse("Demande de parrainage envoyée avec succès"));
        } catch (RuntimeException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // === METHODES UTILITAIRES ===

    private ResponseEntity<Map<String, String>> errorResponse(String message, HttpStatus status) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        return ResponseEntity.status(status).body(errorResponse);
    }

    private Map<String, String> successResponse(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return response;
    }
}
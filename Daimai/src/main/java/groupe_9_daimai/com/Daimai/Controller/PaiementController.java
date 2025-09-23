package groupe_9_daimai.com.Daimai.Controller;

import groupe_9_daimai.com.Daimai.DTO.PaiementRequestDTO;
import groupe_9_daimai.com.Daimai.DTO.PaiementResponseDTO;
import groupe_9_daimai.com.Daimai.Entite.enums.StatutPaiement;
import groupe_9_daimai.com.Daimai.Service.PaiementService;
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
@RequestMapping("/api/paiements")
@CrossOrigin(origins = "*")
@Validated
public class PaiementController {

    @Autowired
    private PaiementService paiementService;

    // Effectuer un paiement
    @PostMapping
    public ResponseEntity<?> effectuerPaiement(@Valid @RequestBody PaiementRequestDTO paiementRequestDTO) {
        try {
            PaiementResponseDTO resultat = paiementService.effectuerPaiement(paiementRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultat);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // Récupérer tous les paiements
    @GetMapping
    public ResponseEntity<?> getAllPaiements() {
        try {
            List<PaiementResponseDTO> paiements = paiementService.getAllPaiements();
            return ResponseEntity.ok(paiements);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Récupérer un paiement par ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPaiementById(@PathVariable Long id) {
        try {
            PaiementResponseDTO paiement = paiementService.getPaiementById(id);
            return ResponseEntity.ok(paiement);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // Vérifier le statut d'un paiement
    @GetMapping("/{id}/statut")
    public ResponseEntity<?> verifierStatutPaiement(@PathVariable Long id) {
        try {
            PaiementResponseDTO paiement = paiementService.verifierStatutPaiement(id);
            return ResponseEntity.ok(paiement);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // Mettre à jour le statut d'un paiement
    @PutMapping("/{id}/statut")
    public ResponseEntity<?> updateStatutPaiement(
            @PathVariable Long id,
            @RequestParam StatutPaiement statut) {
        try {
            PaiementResponseDTO paiement = paiementService.updateStatutPaiement(id, statut);
            return ResponseEntity.ok(paiement);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // Récupérer les paiements d'un parrain
    @GetMapping("/parrain/{parrainId}")
    public ResponseEntity<?> getPaiementsByParrain(@PathVariable Long parrainId) {
        try {
            List<PaiementResponseDTO> paiements = paiementService.getPaiementsByParrain(parrainId);
            return ResponseEntity.ok(paiements);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Récupérer le total des paiements d'un parrain
    @GetMapping("/parrain/{parrainId}/total")
    public ResponseEntity<?> getTotalPaiementsParrain(@PathVariable Long parrainId) {
        try {
            Double total = paiementService.getTotalPaiementsParrain(parrainId);
            Map<String, Object> response = new HashMap<>();
            response.put("parrainId", parrainId);
            response.put("totalPaiements", total);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Récupérer les paiements par période
    @GetMapping("/periode")
    public ResponseEntity<?> getPaiementsByPeriode(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<PaiementResponseDTO> paiements = paiementService.getPaiementsByPeriode(startDate, endDate);
            return ResponseEntity.ok(paiements);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Statistiques des paiements
    @GetMapping("/statistiques")
    public ResponseEntity<?> getStatistiquesPaiements() {
        try {
            // Implémentation des statistiques
            Map<String, Object> statistiques = new HashMap<>();
            // À compléter avec les statistiques nécessaires
            return ResponseEntity.ok(statistiques);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
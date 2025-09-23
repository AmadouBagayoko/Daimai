package groupe_9_daimai.com.Daimai.Controller;

import groupe_9_daimai.com.Daimai.DTO.RecuRequestDTO;
import groupe_9_daimai.com.Daimai.DTO.RecuResponseDTO;
import groupe_9_daimai.com.Daimai.Service.RecuService;
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
@RequestMapping("/api/recus")
@CrossOrigin(origins = "*")
@Validated
public class RecuController {

    @Autowired
    private RecuService recuService;

    // Créer un reçu
    @PostMapping
    public ResponseEntity<?> createRecu(@Valid @RequestBody RecuRequestDTO recuRequestDTO) {
        try {
            RecuResponseDTO recu = recuService.createRecu(recuRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(recu);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // Générer un reçu automatiquement
    @PostMapping("/paiement/{paiementId}/generer-automatique")
    public ResponseEntity<?> genererRecuAutomatique(@PathVariable Long paiementId) {
        try {
            RecuResponseDTO recu = recuService.genererRecuAutomatique(paiementId);
            return ResponseEntity.status(HttpStatus.CREATED).body(recu);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // Récupérer tous les reçus
    @GetMapping
    public ResponseEntity<?> getAllRecus() {
        try {
            List<RecuResponseDTO> recus = recuService.getAllRecus();
            return ResponseEntity.ok(recus);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Récupérer un reçu par ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getRecuById(@PathVariable Long id) {
        try {
            RecuResponseDTO recu = recuService.getRecuById(id);
            return ResponseEntity.ok(recu);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // Récupérer un reçu par code
    @GetMapping("/code/{codeRecu}")
    public ResponseEntity<?> getRecuByCode(@PathVariable String codeRecu) {
        try {
            RecuResponseDTO recu = recuService.getRecuByCode(codeRecu);
            return ResponseEntity.ok(recu);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // Récupérer un reçu par paiement
    @GetMapping("/paiement/{paiementId}")
    public ResponseEntity<?> getRecuByPaiement(@PathVariable Long paiementId) {
        try {
            RecuResponseDTO recu = recuService.getRecuByPaiement(paiementId);
            return ResponseEntity.ok(recu);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // Modifier un reçu
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecu(@PathVariable Long id, @Valid @RequestBody RecuRequestDTO recuRequestDTO) {
        try {
            RecuResponseDTO recu = recuService.updateRecu(id, recuRequestDTO);
            return ResponseEntity.ok(recu);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // Supprimer un reçu
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecu(@PathVariable Long id) {
        try {
            recuService.deleteRecu(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Reçu supprimé avec succès");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // Récupérer les reçus d'un parrain
    @GetMapping("/parrain/{parrainId}")
    public ResponseEntity<?> getRecusByParrain(@PathVariable Long parrainId) {
        try {
            List<RecuResponseDTO> recus = recuService.getRecusByParrain(parrainId);
            return ResponseEntity.ok(recus);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Récupérer les reçus par période
    @GetMapping("/periode")
    public ResponseEntity<?> getRecusByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<RecuResponseDTO> recus = recuService.getRecusByPeriod(startDate, endDate);
            return ResponseEntity.ok(recus);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Récupérer le total des reçus d'un parrain
    @GetMapping("/parrain/{parrainId}/total")
    public ResponseEntity<?> getTotalRecusByParrain(@PathVariable Long parrainId) {
        try {
            Double total = recuService.getTotalRecusByParrain(parrainId);
            Map<String, Object> response = new HashMap<>();
            response.put("parrainId", parrainId);
            response.put("totalRecus", total);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Renvoyer la notification d'un reçu
    @PostMapping("/{recuId}/renvoyer-notification")
    public ResponseEntity<?> renvoyerNotificationRecu(@PathVariable Long recuId) {
        try {
            recuService.renvoyerNotificationRecu(recuId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Notification renvoyée avec succès");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
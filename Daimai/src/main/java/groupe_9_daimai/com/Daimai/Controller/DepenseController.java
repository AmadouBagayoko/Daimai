package groupe_9_daimai.com.Daimai.Controller;

import groupe_9_daimai.com.Daimai.DTO.DepenseRequestDTO;
import groupe_9_daimai.com.Daimai.DTO.DepenseResponseDTO;
import groupe_9_daimai.com.Daimai.Entite.enums.CategorieDepense;
import groupe_9_daimai.com.Daimai.Service.DepenseService;
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
@RequestMapping("/api/depenses")
@CrossOrigin(origins = "*")
@Validated
public class DepenseController {

    @Autowired
    private DepenseService depenseService;

    // Créer une dépense
    @PostMapping
    public ResponseEntity<?> createDepense(@Valid @RequestBody DepenseRequestDTO depenseRequestDTO) {
        try {
            DepenseResponseDTO nouvelleDepense = depenseService.createDepense(depenseRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouvelleDepense);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // Récupérer toutes les dépenses
    @GetMapping
    public ResponseEntity<?> getAllDepenses() {
        try {
            List<DepenseResponseDTO> depenses = depenseService.getAllDepenses();
            return ResponseEntity.ok(depenses);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Récupérer une dépense par ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getDepenseById(@PathVariable Long id) {
        try {
            DepenseResponseDTO depense = depenseService.getDepenseById(id);
            return ResponseEntity.ok(depense);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // Modifier une dépense
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepense(@PathVariable Long id, @Valid @RequestBody DepenseRequestDTO depenseRequestDTO) {
        try {
            DepenseResponseDTO depenseModifiee = depenseService.updateDepense(id, depenseRequestDTO);
            return ResponseEntity.ok(depenseModifiee);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // Supprimer une dépense
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepense(@PathVariable Long id) {
        try {
            depenseService.deleteDepense(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Dépense supprimée avec succès");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // === NOUVEAUX ENDPOINTS AVEC ANNEE SCOLAIRE ===

    // Récupérer les dépenses d'une association
    @GetMapping("/association/{associationId}")
    public ResponseEntity<?> getDepensesByAssociation(@PathVariable Long associationId) {
        try {
            List<DepenseResponseDTO> depenses = depenseService.getDepensesByAssociation(associationId);
            return ResponseEntity.ok(depenses);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Récupérer les dépenses d'une année scolaire
    @GetMapping("/annee-scolaire/{anneeScolaireId}")
    public ResponseEntity<?> getDepensesByAnneeScolaire(@PathVariable Long anneeScolaireId) {
        try {
            List<DepenseResponseDTO> depenses = depenseService.getDepensesByAnneeScolaire(anneeScolaireId);
            return ResponseEntity.ok(depenses);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Récupérer les dépenses d'une association et année scolaire
    @GetMapping("/association/{associationId}/annee-scolaire/{anneeScolaireId}")
    public ResponseEntity<?> getDepensesByAssociationAndAnneeScolaire(
            @PathVariable Long associationId, @PathVariable Long anneeScolaireId) {
        try {
            List<DepenseResponseDTO> depenses = depenseService.getDepensesByAssociationAndAnneeScolaire(associationId, anneeScolaireId);
            return ResponseEntity.ok(depenses);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Récupérer les dépenses visibles par les parrains (année active)
    @GetMapping("/association/{associationId}/visibles-parrains")
    public ResponseEntity<?> getDepensesVisiblesParParrains(@PathVariable Long associationId) {
        try {
            List<DepenseResponseDTO> depenses = depenseService.getDepensesVisiblesParParrains(associationId);
            return ResponseEntity.ok(depenses);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Récupérer les dépenses par catégorie
    @GetMapping("/categorie/{categorie}")
    public ResponseEntity<?> getDepensesByCategorie(@PathVariable CategorieDepense categorie) {
        try {
            List<DepenseResponseDTO> depenses = depenseService.getDepensesByCategorie(categorie);
            return ResponseEntity.ok(depenses);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Récupérer les dépenses par association et catégorie
    @GetMapping("/association/{associationId}/categorie/{categorie}")
    public ResponseEntity<?> getDepensesByAssociationAndCategorie(
            @PathVariable Long associationId, @PathVariable CategorieDepense categorie) {
        try {
            List<DepenseResponseDTO> depenses = depenseService.getDepensesByAssociationAndCategorie(associationId, categorie);
            return ResponseEntity.ok(depenses);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Récupérer les dépenses par association, année scolaire et catégorie
    @GetMapping("/association/{associationId}/annee-scolaire/{anneeScolaireId}/categorie/{categorie}")
    public ResponseEntity<?> getDepensesByAssociationAndAnneeScolaireAndCategorie(
            @PathVariable Long associationId, @PathVariable Long anneeScolaireId, @PathVariable CategorieDepense categorie) {
        try {
            List<DepenseResponseDTO> depenses = depenseService.getDepensesByAssociationAndAnneeScolaireAndCategorie(
                    associationId, anneeScolaireId, categorie);
            return ResponseEntity.ok(depenses);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Récupérer les dépenses par période
    @GetMapping("/periode")
    public ResponseEntity<?> getDepensesByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<DepenseResponseDTO> depenses = depenseService.getDepensesByPeriod(startDate, endDate);
            return ResponseEntity.ok(depenses);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Récupérer le total des dépenses d'une association
    @GetMapping("/association/{associationId}/total")
    public ResponseEntity<?> getTotalDepensesByAssociation(@PathVariable Long associationId) {
        try {
            Double total = depenseService.getTotalDepensesByAssociation(associationId);
            Map<String, Object> response = new HashMap<>();
            response.put("associationId", associationId);
            response.put("totalDepenses", total);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Récupérer le total des dépenses d'une association et année scolaire
    @GetMapping("/association/{associationId}/annee-scolaire/{anneeScolaireId}/total")
    public ResponseEntity<?> getTotalDepensesByAssociationAndAnneeScolaire(
            @PathVariable Long associationId, @PathVariable Long anneeScolaireId) {
        try {
            Double total = depenseService.getTotalDepensesByAssociationAndAnneeScolaire(associationId, anneeScolaireId);
            Map<String, Object> response = new HashMap<>();
            response.put("associationId", associationId);
            response.put("anneeScolaireId", anneeScolaireId);
            response.put("totalDepenses", total);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Récupérer le total des dépenses d'une association sur une période
    @GetMapping("/association/{associationId}/periode/total")
    public ResponseEntity<?> getTotalDepensesByAssociationAndPeriod(
            @PathVariable Long associationId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            Double total = depenseService.getTotalDepensesByAssociationAndPeriod(associationId, startDate, endDate);
            Map<String, Object> response = new HashMap<>();
            response.put("associationId", associationId);
            response.put("startDate", startDate);
            response.put("endDate", endDate);
            response.put("totalDepenses", total);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Récupérer l'année scolaire active d'une association
    @GetMapping("/association/{associationId}/annee-scolaire-active")
    public ResponseEntity<?> getAnneeScolaireActiveByAssociation(@PathVariable Long associationId) {
        try {
            Object anneeActive = depenseService.getAnneeScolaireActiveByAssociation(associationId);
            return ResponseEntity.ok(anneeActive);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // Endpoint pour les statistiques avancées
    @GetMapping("/association/{associationId}/statistiques")
    public ResponseEntity<?> getStatistiquesDepenses(@PathVariable Long associationId) {
        try {
            Map<String, Object> statistiques = new HashMap<>();

            // Total général
            Double totalGeneral = depenseService.getTotalDepensesByAssociation(associationId);
            statistiques.put("totalGeneral", totalGeneral != null ? totalGeneral : 0.0);

            // Total par catégorie
            Map<String, Double> totalParCategorie = new HashMap<>();
            for (CategorieDepense categorie : CategorieDepense.values()) {
                List<DepenseResponseDTO> depenses = depenseService.getDepensesByAssociationAndCategorie(associationId, categorie);
                Double totalCategorie = depenses.stream()
                        .mapToDouble(DepenseResponseDTO::getMontant)
                        .sum();
                totalParCategorie.put(categorie.name(), totalCategorie);
            }
            statistiques.put("totalParCategorie", totalParCategorie);

            // Dépenses de l'année active
            try {
                Object anneeActive = depenseService.getAnneeScolaireActiveByAssociation(associationId);
                statistiques.put("anneeScolaireActive", anneeActive);
            } catch (RuntimeException e) {
                statistiques.put("anneeScolaireActive", "Aucune année active");
            }

            return ResponseEntity.ok(statistiques);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
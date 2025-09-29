package groupe_9_daimai.com.Daimai.Controller;

import groupe_9_daimai.com.Daimai.DTO.AnneeScolaireRequestDTO;
import groupe_9_daimai.com.Daimai.DTO.AnneeScolaireResponseDTO;
import groupe_9_daimai.com.Daimai.Service.AnneeScolaireService;
import groupe_9_daimai.com.Daimai.Service.AnneeScolaireService.AnneeScolaireNotFoundException;
import groupe_9_daimai.com.Daimai.Service.AnneeScolaireService.AnneeScolaireValidationException;
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
@RequestMapping("/api/annees-scolaires")
@CrossOrigin(origins = "*")
@Validated
public class AnneeScolaireController {

    @Autowired
    private AnneeScolaireService anneeScolaireService;

    @PostMapping
    public ResponseEntity<?> createAnneeScolaire(@Valid @RequestBody AnneeScolaireRequestDTO anneeScolaireRequestDTO) {
        try {
            AnneeScolaireResponseDTO nouvelleAnneeScolaire = anneeScolaireService.createAnneeScolaire(anneeScolaireRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouvelleAnneeScolaire);
        } catch (AnneeScolaireValidationException | AnneeScolaireNotFoundException e) {
            return buildErrorResponse(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAnneeScolaires() {
        try {
            List<AnneeScolaireResponseDTO> anneeScolaires = anneeScolaireService.getAllAnneeScolaires();
            return ResponseEntity.ok(anneeScolaires);
        } catch (Exception e) {
            return buildErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnneeScolaireById(@PathVariable Long id) {
        try {
            AnneeScolaireResponseDTO anneeScolaire = anneeScolaireService.getAnneeScolaireById(id);
            return ResponseEntity.ok(anneeScolaire);
        } catch (AnneeScolaireNotFoundException e) {
            return buildErrorResponse(e, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnneeScolaire(@PathVariable Long id, @Valid @RequestBody AnneeScolaireRequestDTO anneeScolaireRequestDTO) {
        try {
            AnneeScolaireResponseDTO anneeScolaireModifiee = anneeScolaireService.updateAnneeScolaire(id, anneeScolaireRequestDTO);
            return ResponseEntity.ok(anneeScolaireModifiee);
        } catch (AnneeScolaireValidationException | AnneeScolaireNotFoundException e) {
            return buildErrorResponse(e, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> desactiverAnneeScolaire(@PathVariable Long id) {
        try {
            anneeScolaireService.desactiverAnneeScolaire(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Année scolaire désactivée avec succès");
            return ResponseEntity.ok(response);
        } catch (AnneeScolaireValidationException | AnneeScolaireNotFoundException e) {
            return buildErrorResponse(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/reactiver")
    public ResponseEntity<?> reactiverAnneeScolaire(@PathVariable Long id) {
        try {
            AnneeScolaireResponseDTO anneeReactived = anneeScolaireService.reactiverAnneeScolaire(id);
            return ResponseEntity.ok(anneeReactived);
        } catch (AnneeScolaireValidationException | AnneeScolaireNotFoundException e) {
            return buildErrorResponse(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/association/{associationId}")
    public ResponseEntity<?> getAnneeScolairesByAssociation(@PathVariable Long associationId) {
        try {
            List<AnneeScolaireResponseDTO> anneeScolaires = anneeScolaireService.getAnneeScolairesByAssociation(associationId);
            return ResponseEntity.ok(anneeScolaires);
        } catch (Exception e) {
            return buildErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/association/{associationId}/active")
    public ResponseEntity<?> getAnneeScolaireActiveByAssociation(@PathVariable Long associationId) {
        try {
            AnneeScolaireResponseDTO anneeActive = anneeScolaireService.getAnneeScolaireActiveByAssociation(associationId);
            return ResponseEntity.ok(anneeActive);
        } catch (AnneeScolaireNotFoundException e) {
            return buildErrorResponse(e, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/active")
    public ResponseEntity<?> isAnneeScolaireActive(@PathVariable Long id) {
        try {
            boolean isActive = anneeScolaireService.isAnneeScolaireActive(id);
            Map<String, Object> response = new HashMap<>();
            response.put("id", id);
            response.put("active", isActive);
            return ResponseEntity.ok(response);
        } catch (AnneeScolaireNotFoundException e) {
            return buildErrorResponse(e, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/periode")
    public ResponseEntity<?> getAnneeScolairesByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<AnneeScolaireResponseDTO> anneeScolaires = anneeScolaireService.getAnneeScolairesByPeriod(startDate, endDate);
            return ResponseEntity.ok(anneeScolaires);
        } catch (AnneeScolaireValidationException e) {
            return buildErrorResponse(e, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return buildErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/association/{associationId}/exists")
    public ResponseEntity<?> associationHasAnneeScolaires(@PathVariable Long associationId) {
        try {
            boolean hasAnneeScolaires = anneeScolaireService.associationHasAnneeScolaires(associationId);
            Map<String, Object> response = new HashMap<>();
            response.put("associationId", associationId);
            response.put("hasAnneeScolaires", hasAnneeScolaires);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/association/{associationId}/count")
    public ResponseEntity<?> countAnneeScolairesByAssociation(@PathVariable Long associationId) {
        try {
            long count = anneeScolaireService.countAnneeScolairesByAssociation(associationId);
            Map<String, Object> response = new HashMap<>();
            response.put("associationId", associationId);
            response.put("count", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAnneeScolairesWithInactive() {
        try {
            List<AnneeScolaireResponseDTO> anneeScolaires = anneeScolaireService.getAllAnneeScolairesWithInactive();
            return ResponseEntity.ok(anneeScolaires);
        } catch (Exception e) {
            return buildErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/association/{associationId}/inactives")
    public ResponseEntity<?> getAnneeScolairesInactivesByAssociation(@PathVariable Long associationId) {
        try {
            List<AnneeScolaireResponseDTO> anneesInactives = anneeScolaireService.getAnneeScolairesInactivesByAssociation(associationId);
            return ResponseEntity.ok(anneesInactives);
        } catch (Exception e) {
            return buildErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/association/{associationId}/statistiques")
    public ResponseEntity<?> getStatistiquesAnneeScolaires(@PathVariable Long associationId) {
        try {
            List<AnneeScolaireResponseDTO> anneeScolaires = anneeScolaireService.getAnneeScolairesByAssociation(associationId);
            long totalAnneeScolaires = anneeScolaireService.countAnneeScolairesByAssociation(associationId);

            Map<String, Object> statistiques = new HashMap<>();
            statistiques.put("totalAnneeScolaires", totalAnneeScolaires);
            statistiques.put("anneeScolaires", anneeScolaires);

            try {
                AnneeScolaireResponseDTO anneeActive = anneeScolaireService.getAnneeScolaireActiveByAssociation(associationId);
                statistiques.put("anneeActive", anneeActive);
            } catch (AnneeScolaireNotFoundException e) {
                statistiques.put("anneeActive", null);
                statistiques.put("messageAnneeActive", "Aucune année scolaire active");
            }

            return ResponseEntity.ok(statistiques);
        } catch (Exception e) {
            return buildErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<Map<String, String>> buildErrorResponse(Exception e, HttpStatus status) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", e.getMessage());
        return ResponseEntity.status(status).body(errorResponse);
    }
}
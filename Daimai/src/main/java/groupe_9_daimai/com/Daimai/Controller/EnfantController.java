package groupe_9_daimai.com.Daimai.Controller;

import groupe_9_daimai.com.Daimai.Entite.Association;
import groupe_9_daimai.com.Daimai.Entite.Parrain;
import groupe_9_daimai.com.Daimai.Service.EnfantService;
import groupe_9_daimai.com.Daimai.DTO.EnfantDto; // NOUVEL IMPORT
import groupe_9_daimai.com.Daimai.DTO.EnfantUpdateDTO; // NOUVEL IMPORT
import groupe_9_daimai.com.Daimai.ReponseDto.EnfantResponseDTO;
import org.springframework.web.bind.annotation.*;
import java.io.IOException; // 👈 Ajout
import java.util.List;

@RestController
@RequestMapping("/api/enfants")
public class EnfantController {

    private final EnfantService enfantService;

    public EnfantController(EnfantService enfantService) {
        this.enfantService = enfantService;
    }

    // Création d’un enfant POUR une association
    // 👈 CORRECTION : Utiliser @ModelAttribute et le DTO de création
    @PostMapping(value = "/association/{associationId}", consumes = "multipart/form-data")
    public EnfantResponseDTO creerEnfantPourAssociation(
            @PathVariable Long associationId,
            @ModelAttribute EnfantDto enfantDto) throws IOException {
        return enfantService.creerEnfantPourAssociation(associationId, enfantDto);
    }

    // Modifier un enfant (avec photo optionnelle)
    // 👈 CORRECTION : Utiliser @ModelAttribute et le DTO de mise à jour
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public EnfantResponseDTO modifierEnfant(@PathVariable Long id, @ModelAttribute EnfantUpdateDTO enfantDTO) throws IOException {
        return enfantService.modifierEnfant(id, enfantDTO);
    }


    // Désactiver un enfant
    @PutMapping("/{id}/desactiver")
    public EnfantResponseDTO desactiverEnfant(@PathVariable Long id) {
        return enfantService.desactiverEnfant(id);
    }

    // Lister les enfants d’une association
    @GetMapping("/association/{associationId}")
    public List<EnfantResponseDTO> listerEnfantsParAssociation(@PathVariable Long associationId) {
        Association association = new Association();
        association.setId(associationId);
        return enfantService.listerEnfantsParAssociation(association);
    }

    // Lister les enfants d’un parrain
    @GetMapping("/parrain/{parrainId}")
    public List<EnfantResponseDTO> listerEnfantsParParrain(@PathVariable Long parrainId) {
        return enfantService.listerEnfantsParParrain(parrainId);
    }

    // Obtenir le profil d’un enfant pour une association
    @GetMapping("/{id}/association/{associationId}")
    public EnfantResponseDTO obtenirProfilEnfant(@PathVariable Long id, @PathVariable Long associationId) {
        Association association = new Association();
        association.setId(associationId);
        return enfantService.obtenirProfilEnfant(id, association);
    }
}

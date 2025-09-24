package groupe_9_daimai.com.Daimai.Controller;

import groupe_9_daimai.com.Daimai.Entite.Association;
import groupe_9_daimai.com.Daimai.Entite.Parrain;
import groupe_9_daimai.com.Daimai.Service.EnfantService;
import groupe_9_daimai.com.Daimai.DTO.EnfantDto;
import groupe_9_daimai.com.Daimai.ReponseDto.EnfantResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enfants")
public class EnfantController {

    private final EnfantService enfantService;

    public EnfantController(EnfantService enfantService) {
        this.enfantService = enfantService;
    }

    // Création d’un enfant POUR une association
    @PostMapping("/association/{associationId}")
    public EnfantResponseDTO creerEnfantPourAssociation(
            @PathVariable Long associationId,
            @RequestBody EnfantDto enfantDTO) {
        return enfantService.creerEnfantPourAssociation(associationId, enfantDTO);
    }

    // Désactiver un enfant (abandon)
    @PutMapping("/{id}/desactiver")
    public EnfantResponseDTO desactiverEnfant(@PathVariable Long id) {
        return enfantService.desactiverEnfant(id);
    }

    // Modifier un enfant
    @PutMapping("/{id}")
    public EnfantResponseDTO modifierEnfant(@PathVariable Long id, @RequestBody EnfantDto enfantDTO) {
        return enfantService.modifierEnfant(id, enfantDTO);
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
        Parrain parrain = new Parrain();
        parrain.setId(parrainId);
        return enfantService.listerEnfantsParParrain(parrain);
    }

    // Obtenir le profil d’un enfant pour une association
    @GetMapping("/{id}/association/{associationId}")
    public EnfantResponseDTO obtenirProfilEnfant(@PathVariable Long id, @PathVariable Long associationId) {
        Association association = new Association();
        association.setId(associationId);
        return enfantService.obtenirProfilEnfant(id, association);
    }
}

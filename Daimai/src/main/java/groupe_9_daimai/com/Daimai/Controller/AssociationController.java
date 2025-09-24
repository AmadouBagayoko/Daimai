package groupe_9_daimai.com.Daimai.Controller;

import groupe_9_daimai.com.Daimai.Entite.Association;
import groupe_9_daimai.com.Daimai.Service.AssociationService;
import groupe_9_daimai.com.Daimai.DTO.AssociationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/associations")
public class AssociationController {

    @Autowired
    private AssociationService associationService;

    //  Créer une association (avec mot de passe par défaut)
    @PostMapping
    public ResponseEntity<Association> creerAssociation(@RequestBody AssociationDTO dto) {
        return ResponseEntity.ok(associationService.creerAssociation(dto));
    }

    //  Modifier une association
    @PutMapping("/{id}")
    public ResponseEntity<Association> modifierAssociation(@PathVariable Long id, @RequestBody Association association) {
        return ResponseEntity.ok(associationService.modifierAssociation(id, association));
    }

    //  Désactiver une association
    @PutMapping("/{id}/desactiver")
    public ResponseEntity<Association> desactiverAssociation(@PathVariable Long id) {
        return ResponseEntity.ok(associationService.desactiverAssociation(id));
    }

    //  Lister toutes les associations
    @GetMapping
    public ResponseEntity<List<Association>> listerAssociations() {
        return ResponseEntity.ok(associationService.listerAssociations());
    }

    //  Lister une seule association
    @GetMapping("/{id}")
    public ResponseEntity<Association> getAssociation(@PathVariable Long id) {
        return associationService.getAssociation(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint admin : Valider une association
    @PutMapping("/{id}/valider")
    public ResponseEntity<Association> validerAssociation(@PathVariable Long id) {
        return ResponseEntity.ok(associationService.validerAssociation(id));
    }

}

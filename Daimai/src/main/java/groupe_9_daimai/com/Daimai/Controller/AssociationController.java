package groupe_9_daimai.com.Daimai.Controller;

import groupe_9_daimai.com.Daimai.Entite.Association;
import groupe_9_daimai.com.Daimai.Service.AssociationService;
import groupe_9_daimai.com.Daimai.DTO.AssociationDTO; // NOUVEL IMPORT
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/associations")
public class AssociationController {

    @Autowired
    private AssociationService associationService;

    //  Créer une association avec upload de fichiers
    //  CORRECTION : Utiliser @ModelAttribute pour les données et le DTO
    // L'annotation @RequestPart est une alternative plus précise pour les fichiers
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Association> creerAssociation(@ModelAttribute AssociationDTO dto) throws IOException {
        return ResponseEntity.ok(associationService.creerAssociation(dto));
    }

    // ... (autres méthodes du contrôleur qui ne changent pas)
    @PutMapping("/{id}")
    public ResponseEntity<Association> modifierAssociation(@PathVariable Long id, @RequestBody Association association) {
        return ResponseEntity.ok(associationService.modifierAssociation(id, association));
    }

    @PutMapping("/{id}/desactiver")
    public ResponseEntity<Association> desactiverAssociation(@PathVariable Long id) {
        return ResponseEntity.ok(associationService.desactiverAssociation(id));
    }

    @GetMapping
    public ResponseEntity<List<Association>> listerAssociations() {
        return ResponseEntity.ok(associationService.listerAssociations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Association> getAssociation(@PathVariable Long id) {
        return associationService.getAssociation(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/valider")
    public ResponseEntity<Association> validerAssociation(@PathVariable Long id) {
        return ResponseEntity.ok(associationService.validerAssociation(id));
    }

}

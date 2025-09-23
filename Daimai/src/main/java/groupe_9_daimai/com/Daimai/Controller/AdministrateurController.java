package groupe_9_daimai.com.Daimai.Controller;


import groupe_9_daimai.com.Daimai.Entite.Administrateur;
import groupe_9_daimai.com.Daimai.Service.AdministrateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdministrateurController {

    @Autowired
    private AdministrateurService administrateurService;

    @PostMapping("/CreationAdmin")
    public Administrateur CreerAdministrateur(@RequestBody Administrateur administrateur) {
        return administrateurService.CreationAdministrateur(administrateur);
    }

    @GetMapping("ListerAdministrateur")
    public List<Administrateur> ListerAdministrateur() {
        return administrateurService.LireAdministrateur();
    }

    @PutMapping("/{id}/ModifierAdmin")
    public Administrateur UpdateAdministrateur(@RequestBody Administrateur administrateur, @PathVariable Long id) {
        return administrateurService.ModifierAdministrateur(id, administrateur);
    }

    @DeleteMapping("/{id}/SupprimerAdmin")
    public void SupprimerAdmin(@PathVariable Long id) {
        administrateurService.SupprimerAdministrateur(id);
    }



}

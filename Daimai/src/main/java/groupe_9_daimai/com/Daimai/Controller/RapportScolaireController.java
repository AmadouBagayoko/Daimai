package groupe_9_daimai.com.Daimai.Controller;


import groupe_9_daimai.com.Daimai.Entite.RapportScolaire;
import groupe_9_daimai.com.Daimai.Service.RapportScolaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/Rapport")
public class RapportScolaireController {

    @Autowired
    private RapportScolaireService rapportScolaireService;



    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<RapportScolaire> creerRapport(
            @RequestParam("moyenne") String moyenne,
            @RequestParam("presence") String presence,
            @RequestParam("dateDebut") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDebut,
            @RequestParam("dateFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFin,
            @RequestParam("enfantId") Long enfantId,
            @RequestParam("bulletin") MultipartFile bulletinFile) throws IOException {

        RapportScolaire rapport = rapportScolaireService.CreationDeRapportScolaire(enfantId, moyenne, presence,
                dateDebut, dateFin, bulletinFile);

        return ResponseEntity.ok(rapport);
    }
}

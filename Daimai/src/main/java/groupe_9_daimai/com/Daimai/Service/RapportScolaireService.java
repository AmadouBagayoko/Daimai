package groupe_9_daimai.com.Daimai.Service;


import groupe_9_daimai.com.Daimai.Entite.Enfant;
import groupe_9_daimai.com.Daimai.Entite.RapportScolaire;
import groupe_9_daimai.com.Daimai.Repository.EnfantRepository;
import groupe_9_daimai.com.Daimai.Repository.NotificationRepository;
import groupe_9_daimai.com.Daimai.Repository.RapportScolaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


@Service
public class RapportScolaireService {

    //Creation de rapport entraine un envoi de notification

    @Autowired
    private RapportScolaireRepository rapportScolaireRepository;

    @Autowired
    private EnfantRepository enfantRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    private final String uploadUrl = "uploads/";

    public RapportScolaire CreationDeRapportScolaire(Long EnfantId, String moyenne, String presence,
                                                     java.util.Date dateDebut, java.util.Date dateFin,
                                                     MultipartFile bulletin) throws IOException {

        Enfant enfant = enfantRepository.findById(EnfantId).orElseThrow(
                ()-> new IllegalArgumentException("L'id existe")
        );

        RapportScolaire rapportScolaire = new RapportScolaire();
        rapportScolaire.setMoyenne(moyenne);
        rapportScolaire.setPresence(presence);
        rapportScolaire.setDateDebut(dateDebut);
        rapportScolaire.setDateFin(dateFin);
        rapportScolaire.setEnfant(enfant);

        if(bulletin != null && !bulletin.isEmpty()) {
            Path path = Paths.get(uploadUrl + bulletin.getOriginalFilename());
            Files.createDirectories(path.getParent());
            Files.copy(bulletin.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            rapportScolaire.setBulletin(path.toString());
        }

        return rapportScolaireRepository.save(rapportScolaire);
    }

    public List<RapportScolaire> LireRapportScolaire() {
        return rapportScolaireRepository.findAll();
    }

    public void SupprimerRapportScolaire(Long id) {
        RapportScolaire rapportScolaire = rapportScolaireRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("L'id n'existe pas")
        );
        rapportScolaireRepository.deleteById(id);

    }

}

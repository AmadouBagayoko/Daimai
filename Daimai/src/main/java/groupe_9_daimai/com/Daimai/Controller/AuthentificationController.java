package groupe_9_daimai.com.Daimai.Controller;


import groupe_9_daimai.com.Daimai.Config.JwUtil;
import groupe_9_daimai.com.Daimai.DTO.ConnexionClasse;
import groupe_9_daimai.com.Daimai.DTO.InscriptionRequete;
import groupe_9_daimai.com.Daimai.Entite.Parrain;
import groupe_9_daimai.com.Daimai.Repository.ParrainageRepository;
import groupe_9_daimai.com.Daimai.Service.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthentificationController {


    @Autowired
    private ParrainageRepository parrainageRepository;

    @Autowired
    private JwUtil jwUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthentificationService authentificationService;


    @PostMapping("/Inscription")
    public String inscription(@RequestBody InscriptionRequete inscriptionRequete) {
        if(parrainageRepository.existsByTelephone(inscriptionRequete.getTelephone())){
            return ("Numero existe deja");
        }

        Parrain parrain = new Parrain();
        parrain.setEmail(inscriptionRequete.getEmail());
        parrain.setAdresse(inscriptionRequete.getAdresse());
        parrain.setNom(inscriptionRequete.getNom());
        parrain.setPrenom(inscriptionRequete.getPrenom());
        parrain.setMotDepasse(passwordEncoder.encode(inscriptionRequete.getMotDepasse()));
        parrain.setProfession(inscriptionRequete.getProfession());
        parrain.setTelephone(inscriptionRequete.getTelephone());
        parrain.setStatut(false);
        parrain.setDateCreation(LocalDateTime.now().toLocalDate());

        parrainageRepository.save(parrain);

        return "Utilisateur creer avec succes";

    }

    @PostMapping("/Connexion")
    public ResponseEntity<?> connexion(@RequestBody ConnexionClasse connexion) {
        try {
            return ResponseEntity.ok(authentificationService.connexion(connexion.getTelephone(), connexion.getMotDePasse()));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }




}
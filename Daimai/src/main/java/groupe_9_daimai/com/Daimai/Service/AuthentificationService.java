package groupe_9_daimai.com.Daimai.Service;


import groupe_9_daimai.com.Daimai.Config.JwUtil;
import groupe_9_daimai.com.Daimai.Entite.Administrateur;
import groupe_9_daimai.com.Daimai.Entite.Association;
import groupe_9_daimai.com.Daimai.Entite.Enfant;
import groupe_9_daimai.com.Daimai.Entite.Parrain;
import groupe_9_daimai.com.Daimai.Repository.AdministrateurRepository;
import groupe_9_daimai.com.Daimai.Repository.AssociationRepository;
import groupe_9_daimai.com.Daimai.Repository.EnfantRepository;
import groupe_9_daimai.com.Daimai.Repository.ParrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthentificationService {
    @Autowired
    private ParrainRepository parrainRepo;
    @Autowired
    private AssociationRepository associationRepo;
    @Autowired
    private EnfantRepository enfantRepo;
    @Autowired
    private AdministrateurRepository adminRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwUtil jwtUtil;

    public Map<String, Object> connexion(String telephone, String motDePasse) {
        Object user = null;
        String type = "";

        // Vérifier Parrain
        user = parrainRepo.findByTelephone(telephone).orElse(null);
        if(user != null) type = "parrain";

        // Vérifier Association
        if(user == null) {
            user = associationRepo.findByTelephone(telephone).orElse(null);
            if(user != null) type = "association";
        }

        // Vérifier Enfant
        if(user == null) {
            user = enfantRepo.findByTelephone(telephone).orElse(null);
            if(user != null) type = "enfant";
        }

        // Vérifier Admin
        if(user == null) {
            user = adminRepo.findByTelephone(telephone).orElse(null);
            if(user != null) type = "admin";
        }

        if(user == null) throw new RuntimeException("Utilisateur non trouvé");

        String motDePasseHash = null;
        if(user instanceof Parrain p) motDePasseHash = p.getMotDepasse();
        if(user instanceof Association a) motDePasseHash = a.getMotDepasse();
        if(user instanceof Enfant e) motDePasseHash = e.getMotDepasse();
        if(user instanceof Administrateur ad) motDePasseHash = ad.getMotDepasse();

        if(!passwordEncoder.matches(motDePasse, motDePasseHash)){
            throw new RuntimeException("Mot de passe incorrect");
        }

        String token = jwtUtil.generateToken(telephone);

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("type", type);
        result.put("Utilisateur", user);

        return result;
    }
}


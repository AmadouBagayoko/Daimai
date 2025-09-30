package groupe_9_daimai.com.Daimai.Service;


import groupe_9_daimai.com.Daimai.Entite.Administrateur;
import groupe_9_daimai.com.Daimai.Repository.AdministrateurRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@Service
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AdministrateurService {
    //Creation de compte
    @Autowired
    AdministrateurRepository administrateurRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    public Administrateur CreationAdministrateur (Administrateur administrateur ){
        Optional<Administrateur> administrateurOptional = administrateurRepository.findByEmail(administrateur.getEmail());
        if(administrateurOptional.isPresent()){
            throw new IllegalArgumentException("Email Already Exists");
        }

        administrateur.setMotDepasse(passwordEncoder.encode(administrateur.getMotDepasse()));

        return administrateurRepository.save(administrateur);

    }

    public Administrateur ModifierAdministrateur (Long id, Administrateur administrateurDetails){

        Administrateur administrateur = administrateurRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Id non trouvé")
        );

        administrateur.setNom(administrateurDetails.getNom());
        administrateur.setPrenom(administrateurDetails.getPrenom());
        administrateur.setEmail(administrateurDetails.getEmail());
        administrateur.setMotDepasse(passwordEncoder.encode(administrateur.getMotDepasse()));

        if(administrateur.getMotDepasse()!=null && !administrateurDetails.getMotDepasse().isEmpty()){
            administrateur.setMotDepasse(passwordEncoder.encode(administrateurDetails.getMotDepasse()));

        }
        return administrateurRepository.save(administrateur);

    }


    public void SupprimerAdministrateur (Long administrateurId){
       Administrateur administrateur = administrateurRepository.findById(administrateurId).orElseThrow(
               () -> new IllegalArgumentException("Administrateur non trouvé")
       );
       administrateurRepository.delete(administrateur);

        
    }
    public List<Administrateur> LireAdministrateur(){
        return administrateurRepository.findAll();
    }





    //Authentification


    //Lister Demandes de creation d'associations


    // Valider les demandes de creations d'associations


    //Valider les demandes de creations d'associations


    //Voir la nombre de tous les parrains, Associations, Enfant



    //Lister les associations


    //Desactiver les associations



    //Lister les parrains
}

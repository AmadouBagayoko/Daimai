package groupe_9_daimai.com.Daimai.Service;


import org.springframework.stereotype.Service;

import groupe_9_daimai.com.Daimai.Entite.Parrain;
import groupe_9_daimai.com.Daimai.Repository.ParrainRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParrainService {

    //Creation de compte et Authentification




    //Authentification


    //Parrainer un enfant


    //Paiement

    private final ParrainRepository parrainRepository;

    public ParrainService(ParrainRepository parrainRepository) {
        this.parrainRepository = parrainRepository;
    }

    // CREATE
    public Parrain createParrain(Parrain parrain) {
        if (parrainRepository.existsByEmail(parrain.getEmail())) {
            throw new RuntimeException("Email déjà utilisé !");
        }
        if (parrainRepository.existsByTelephone(parrain.getTelephone())) {
            throw new RuntimeException("Téléphone déjà utilisé !");
        }
        return parrainRepository.save(parrain);
    }

    // READ ALL
    public List<Parrain> getAllParrains() {
        return parrainRepository.findAll();
    }

    // READ ONE
    public Optional<Parrain> getParrainById(Long id) {
        return parrainRepository.findById(id);
    }

    // UPDATE
    public Parrain updateParrain(Long id, Parrain parrainDetails) {
        Parrain parrain = parrainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parrain non trouvé"));

        parrain.setNom(parrainDetails.getNom());
        parrain.setPrenom(parrainDetails.getPrenom());
        parrain.setEmail(parrainDetails.getEmail());
        parrain.setTelephone(parrainDetails.getTelephone());
        parrain.setMotDepasse(parrainDetails.getMotDepasse());
        parrain.setRoleUtilisateur(parrainDetails.getRoleUtilisateur());
        parrain.setProfession(parrainDetails.getProfession());
        parrain.setAdresse(parrainDetails.getAdresse());
        parrain.setDateCreation(parrainDetails.getDateCreation());
        parrain.setStatut(parrainDetails.getStatut());

        return parrainRepository.save(parrain);
    }

    // DELETE
    public void deleteParrain(Long id) {
        Parrain parrain = parrainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parrain non trouvé"));
        parrainRepository.delete(parrain);
    }
}

package groupe_9_daimai.com.Daimai.Service;

import groupe_9_daimai.com.Daimai.DTO.ParrainageDTO;
import groupe_9_daimai.com.Daimai.Entite.Enfant;
import groupe_9_daimai.com.Daimai.Entite.Parrain;
import groupe_9_daimai.com.Daimai.Repository.EnfantRepository;
import groupe_9_daimai.com.Daimai.Repository.ParrainRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParrainService {

    private final ParrainRepository parrainRepository;
    private final EnfantRepository enfantRepository;

    public ParrainService(ParrainRepository parrainRepository, EnfantRepository enfantRepository) {
        this.parrainRepository = parrainRepository;
        this.enfantRepository = enfantRepository;
    }

    // Parrainer un enfant
    public Parrain parrainerEnfant(ParrainageDTO dto) {
        Parrain parrain = parrainRepository.findById(dto.getParrainId())
                .orElseThrow(() -> new RuntimeException("Parrain non trouvé"));

        Enfant enfant = enfantRepository.findById(dto.getEnfantId())
                .orElseThrow(() -> new RuntimeException("Enfant non trouvé"));

        // Utilisation d'un Set : si l'enfant est déjà là, add() ne fait rien.
        // Cette ligne gère l'unicité pour vous.
        parrain.getEnfants().add(enfant);

        return parrainRepository.save(parrain);
    }

    // CRUD déjà existants...
    public Parrain createParrain(Parrain parrain) {
        if (parrainRepository.existsByEmail(parrain.getEmail())) {
            throw new RuntimeException("Email déjà utilisé !");
        }
        if (parrainRepository.existsByTelephone(parrain.getTelephone())) {
            throw new RuntimeException("Téléphone déjà utilisé !");
        }
        return parrainRepository.save(parrain);
    }

    public List<Parrain> getAllParrains() {
        return parrainRepository.findAll();
    }

    public Optional<Parrain> getParrainById(Long id) {
        return parrainRepository.findById(id);
    }

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

    public void deleteParrain(Long id) {
        Parrain parrain = parrainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parrain non trouvé"));
        parrainRepository.delete(parrain);
    }
}

package groupe_9_daimai.com.Daimai.Service;

import groupe_9_daimai.com.Daimai.Entite.Association;
import groupe_9_daimai.com.Daimai.Repository.AssociationRepository;
import groupe_9_daimai.com.Daimai.DTO.AssociationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssociationService {

    @Autowired
    private AssociationRepository associationRepository;

    // 🔹 Création d'association (non validée + mot de passe par défaut)
    public Association creerAssociation(AssociationDTO dto) {
        Association association = new Association();
        association.setNom(dto.getNom());
        association.setEmail(dto.getEmail());
        association.setAdresse(dto.getAdresse());
        association.setDomaine(dto.getDomaine());
        association.setTelephone(dto.getTelephone());
        association.setPhoto(dto.getPhoto());
        association.setAutorisation((dto.getAutorisation()));

        association.setEstvalider(false); // bloquée tant que l'admin ne valide pas
        association.setMotDepasse("changeme123"); // mot de passe par défaut

        return associationRepository.save(association);
    }

    // 🔹 Modification d'association
    public Association modifierAssociation(Long id, Association nouvelleAssociation) {
        return associationRepository.findById(id).map(association -> {
            association.setNom(nouvelleAssociation.getNom());
            association.setEmail(nouvelleAssociation.getEmail());
            association.setAdresse(nouvelleAssociation.getAdresse());
            association.setDomaine(nouvelleAssociation.getDomaine());
            association.setTelephone(nouvelleAssociation.getTelephone());
            association.setPhoto(nouvelleAssociation.getPhoto());
            return associationRepository.save(association);
        }).orElseThrow(() -> new RuntimeException("Association non trouvée"));
    }

    // 🔹 Désactiver une association
    public Association desactiverAssociation(Long id) {
        return associationRepository.findById(id).map(association -> {
            association.setEstvalider(false);
            return associationRepository.save(association);
        }).orElseThrow(() -> new RuntimeException("Association non trouvée"));
    }

    // 🔹 Lister toutes les associations
    public List<Association> listerAssociations() {
        return associationRepository.findAll();
    }

    // 🔹 Lister une seule association
    public Optional<Association> getAssociation(Long id) {
        return associationRepository.findById(id);
    }

    // Validation par admin
    public Association validerAssociation(Long id) {
        return associationRepository.findById(id).map(association -> {
            association.setEstvalider(true); // débloquer
            return associationRepository.save(association);
        }).orElseThrow(() -> new RuntimeException("Association non trouvée"));
    }

}

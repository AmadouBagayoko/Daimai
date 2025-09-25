package groupe_9_daimai.com.Daimai.Service;

import groupe_9_daimai.com.Daimai.Entite.Association;
import groupe_9_daimai.com.Daimai.Repository.AssociationRepository;
import groupe_9_daimai.com.Daimai.DTO.AssociationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AssociationService {

    @Autowired
    private AssociationRepository associationRepository;

    @Autowired
    private FileStorageService fileStorageService; // 👈 AJOUT de l'injection du service de stockage

    // 🔹 Création d'association (avec upload de fichiers)
    public Association creerAssociation(AssociationDTO dto) throws IOException {
        Association association = new Association();
        association.setNom(dto.getNom());
        association.setEmail(dto.getEmail());
        association.setAdresse(dto.getAdresse());
        association.setDomaine(dto.getDomaine());
        association.setTelephone(dto.getTelephone());

        // Gérer l'upload de la photo et de l'autorisation
        MultipartFile photoFile = dto.getPhoto();
        if (photoFile != null && !photoFile.isEmpty()) {
            String photoPath = fileStorageService.storeFile(photoFile);
            association.setPhoto(photoPath);
        }

        MultipartFile autorisationFile = dto.getAutorisation();
        if (autorisationFile != null && !autorisationFile.isEmpty()) {
            String autorisationPath = fileStorageService.storeFile(autorisationFile);
            association.setAutorisation(autorisationPath);
        }

        association.setEstvalider(false);
        association.setMotDepasse("Daimai2025");

        return associationRepository.save(association);
    }

    // 🔹 Modification d'association
    // NOTE : La gestion de la modification avec de nouveaux fichiers nécessiterait une logique similaire
    // à la création, avec la suppression des anciens fichiers si nécessaire.
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
            association.setEstvalider(true);
            return associationRepository.save(association);
        }).orElseThrow(() -> new RuntimeException("Association non trouvée"));
    }

}

package groupe_9_daimai.com.Daimai.Service;

import groupe_9_daimai.com.Daimai.Entite.Enfant;
import groupe_9_daimai.com.Daimai.Entite.Association;
import groupe_9_daimai.com.Daimai.Entite.Parrain;
import groupe_9_daimai.com.Daimai.Repository.EnfantRepository;
import groupe_9_daimai.com.Daimai.Repository.AssociationRepository;
import groupe_9_daimai.com.Daimai.DTO.EnfantDto;
import groupe_9_daimai.com.Daimai.DTO.EnfantUpdateDTO; // NOUVEL IMPORT
import groupe_9_daimai.com.Daimai.ReponseDto.EnfantResponseDTO;
import groupe_9_daimai.com.Daimai.Repository.ParrainRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired; // 👈 Ajout
import java.io.IOException; // 👈 Ajout

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnfantService {

    private final EnfantRepository enfantRepository;
    private final AssociationRepository associationRepository;
    private final ParrainRepository parrainRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired // 👈 Ajout de l'injection du service de stockage
    private FileStorageService fileStorageService;

    // Constructeur mis à jour pour inclure toutes les dépendances
    public EnfantService(EnfantRepository enfantRepository, AssociationRepository associationRepository, ParrainRepository parrainRepository, PasswordEncoder passwordEncoder) {
        this.enfantRepository = enfantRepository;
        this.associationRepository = associationRepository;
        this.parrainRepository = parrainRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // --- Création d’un enfant pour une association (LOGIQUE ADAPTÉE) ---
    public EnfantResponseDTO creerEnfantPourAssociation(Long associationId, EnfantDto enfantDTO) throws IOException {

        // 1. Récupération de l'Association
        Association association = associationRepository.findById(associationId)
                .orElseThrow(() -> new RuntimeException("Association introuvable"));

        // 2. VÉRIFICATION DU STATUT : Empêche la création si l'association n'est pas active
        if (association.getEstvalider() == null || !association.getEstvalider()) {
            throw new IllegalStateException("L'association " + association.getNom() + " n'est pas active (statut 'estvalider' est faux ou nul) et ne peut pas créer d'enfant.");
        }

        // 3. Mappage et configuration de l'Entité Enfant
        Enfant enfant = new Enfant();
        enfant.setNom(enfantDTO.getNom());
        enfant.setPrenom(enfantDTO.getPrenom());
        enfant.setDateNaissance(enfantDTO.getDateNaissance());
        enfant.setNiveauScolaire(enfantDTO.getNiveauScolaire());
        enfant.setTuteur(enfantDTO.getTuteur());
        enfant.setTelephone(enfantDTO.getTelephone());
        enfant.setEmail(enfantDTO.getEmail());
        enfant.setStatutAbandon(enfantDTO.getStatutAbandon() != null ? enfantDTO.getStatutAbandon() : false);
        enfant.setAssociation(association);

        // 4. Logique d'upload de la photo de profil
        MultipartFile photoFile = enfantDTO.getPhoto();
        if (photoFile != null && !photoFile.isEmpty()) {
            String photoPath = fileStorageService.storeFile(photoFile);
            enfant.setUrlPhoto(photoPath);
        } else {
            enfant.setUrlPhoto(null); // S'assurer que le champ est null si aucun fichier n'est fourni
        }

        // 5. Logique de génération et de HACHAGE du mot de passe
        String anneeNaissance = String.valueOf(enfantDTO.getDateNaissance().getYear());
        String motDePasseClair = enfantDTO.getNom() + anneeNaissance;
        String motDePasseHache = passwordEncoder.encode(motDePasseClair);
        enfant.setMotDepasse(motDePasseHache);

        // 6. Sauvegarde
        Enfant saved = enfantRepository.save(enfant);
        return new EnfantResponseDTO(saved);
    }

    // --- Modification d'un enfant (MÉTHODE ADAPTÉE) ---
    public EnfantResponseDTO modifierEnfant(Long enfantId, EnfantUpdateDTO enfantDetailsDTO) throws IOException {
        Enfant enfant = enfantRepository.findById(enfantId)
                .orElseThrow(() -> new RuntimeException("Enfant non trouvé"));

        enfant.setNom(enfantDetailsDTO.getNom());
        enfant.setPrenom(enfantDetailsDTO.getPrenom());
        enfant.setDateNaissance(enfantDetailsDTO.getDateNaissance());
        enfant.setNiveauScolaire(enfantDetailsDTO.getNiveauScolaire());
        enfant.setTuteur(enfantDetailsDTO.getTuteur());
        enfant.setTelephone(enfantDetailsDTO.getTelephone());
        enfant.setEmail(enfantDetailsDTO.getEmail());

        // Gérer la mise à jour optionnelle de la photo
        MultipartFile photoFile = enfantDetailsDTO.getPhoto();
        if (photoFile != null && !photoFile.isEmpty()) {
            String photoPath = fileStorageService.storeFile(photoFile);
            enfant.setUrlPhoto(photoPath);
        }

        if (enfantDetailsDTO.getStatutAbandon() != null) {
            enfant.setStatutAbandon(enfantDetailsDTO.getStatutAbandon());
        }

        Enfant saved = enfantRepository.save(enfant);
        return new EnfantResponseDTO(saved);
    }

    // Désactiver un enfant
    public EnfantResponseDTO desactiverEnfant(Long enfantId) {
        Enfant enfant = enfantRepository.findById(enfantId)
                .orElseThrow(() -> new RuntimeException("Enfant non trouvé"));
        enfant.setStatutAbandon(true);
        Enfant saved = enfantRepository.save(enfant);
        return new EnfantResponseDTO(saved);
    }

    // Lister les enfants d’une association
    public List<EnfantResponseDTO> listerEnfantsParAssociation(Association association) {
        return enfantRepository.findAll().stream()
                .filter(e -> e.getAssociation() != null && e.getAssociation().getId().equals(association.getId()))
                .map(EnfantResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Lister les enfants d’un parrain
    public List<EnfantResponseDTO> listerEnfantsParParrain(Long parrainId) {
        Parrain parrainGere = parrainRepository.findById(parrainId)
                .orElseThrow(() -> new RuntimeException("Parrain non trouvé avec l'ID: " + parrainId));
        return enfantRepository.findAll().stream()
                .filter(e -> e.getParrains() != null && e.getParrains().contains(parrainGere))
                .map(EnfantResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Obtenir le profil d’un enfant pour une association
    public EnfantResponseDTO obtenirProfilEnfant(Long enfantId, Association association) {
        Enfant enfant = enfantRepository.findById(enfantId)
                .orElseThrow(() -> new RuntimeException("Enfant non trouvé"));
        if (enfant.getAssociation() != null && enfant.getAssociation().getId().equals(association.getId())) {
            return new EnfantResponseDTO(enfant);
        }
        throw new RuntimeException("Cet enfant n'appartient pas à cette association");
    }
}

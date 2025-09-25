package groupe_9_daimai.com.Daimai.Service;

import groupe_9_daimai.com.Daimai.Entite.Enfant;
import groupe_9_daimai.com.Daimai.Entite.Association;
import groupe_9_daimai.com.Daimai.Entite.Parrain;
import groupe_9_daimai.com.Daimai.Repository.EnfantRepository;
import groupe_9_daimai.com.Daimai.Repository.AssociationRepository;
import groupe_9_daimai.com.Daimai.DTO.EnfantDto;
import groupe_9_daimai.com.Daimai.ReponseDto.EnfantResponseDTO;
import groupe_9_daimai.com.Daimai.Repository.ParrainRepository;
import org.springframework.security.crypto.password.PasswordEncoder; // 👈 Nécessaire pour le hachage
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnfantService {

    private final EnfantRepository enfantRepository;
    private final AssociationRepository associationRepository;
    private final ParrainRepository parrainRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructeur mis à jour pour inclure PasswordEncoder
    public EnfantService(EnfantRepository enfantRepository, AssociationRepository associationRepository, ParrainRepository parrainRepository,PasswordEncoder passwordEncoder) {
        this.enfantRepository = enfantRepository;
        this.associationRepository = associationRepository;
        this.parrainRepository = parrainRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // --- Création d’un enfant pour une association (LOGIQUE ADAPTÉE) ---
    public EnfantResponseDTO creerEnfantPourAssociation(Long associationId, EnfantDto enfantDTO) {

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
        enfant.setUrlPhoto(enfantDTO.getUrlPhoto());
        enfant.setTuteur(enfantDTO.getTuteur());
        enfant.setTelephone(enfantDTO.getTelephone());
        enfant.setEmail(enfantDTO.getEmail());
        enfant.setStatutAbandon(enfantDTO.getStatutAbandon() != null ? enfantDTO.getStatutAbandon() : false);
        enfant.setAssociation(association);

        // 4. Logique de génération et de HACHAGE du mot de passe
        String anneeNaissance = String.valueOf(enfantDTO.getDateNaissance().getYear());
        String motDePasseClair = enfantDTO.getNom() + anneeNaissance;

        // 🚨 CORRECTION SÉCURITÉ : HACHAGE du mot de passe avant de sauvegarder
        String motDePasseHache = passwordEncoder.encode(motDePasseClair);
        enfant.setMotDepasse(motDePasseHache);

        // 5. Sauvegarde
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

    // Modifier un enfant
    public EnfantResponseDTO modifierEnfant(Long enfantId, EnfantDto enfantDetailsDTO) {
        Enfant enfant = enfantRepository.findById(enfantId)
                .orElseThrow(() -> new RuntimeException("Enfant non trouvé"));

        enfant.setNom(enfantDetailsDTO.getNom());
        enfant.setPrenom(enfantDetailsDTO.getPrenom());
        enfant.setDateNaissance(enfantDetailsDTO.getDateNaissance());
        enfant.setNiveauScolaire(enfantDetailsDTO.getNiveauScolaire());
        enfant.setUrlPhoto(enfantDetailsDTO.getUrlPhoto());
        enfant.setTuteur(enfantDetailsDTO.getTuteur());
        enfant.setTelephone(enfantDetailsDTO.getTelephone());
        enfant.setEmail(enfantDetailsDTO.getEmail());

        if (enfantDetailsDTO.getStatutAbandon() != null) {
            enfant.setStatutAbandon(enfantDetailsDTO.getStatutAbandon());
        }

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

        // 1. Charger l'entité Parrain GÉRÉE par JPA
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
package groupe_9_daimai.com.Daimai.Service;

import groupe_9_daimai.com.Daimai.DTO.*;
import groupe_9_daimai.com.Daimai.Entite.*;
import groupe_9_daimai.com.Daimai.Entite.enums.TypeNotifcation;
import groupe_9_daimai.com.Daimai.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AssociationService {

    @Autowired
    private AssociationRepository associationRepository;

    @Autowired
    private AdministrateurRepository administrateurRepository;

    @Autowired
    private EnfantRepository enfantRepository;

    @Autowired
    private ParrainRepository parrainRepository;

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private RapportScolaireRepository rapportScolaireRepository;

    @Autowired
    private DepenseRepository depenseRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // === METHODES ASSOCIATION ===

    // Conversion Entity -> DTO
    private AssociationResponseDTO convertToDTO(Association association) {
        if (association == null) return null;

        AssociationResponseDTO dto = new AssociationResponseDTO();
        dto.setId(association.getId());
        dto.setNom(association.getNom());
        dto.setEmail(association.getEmail());
        dto.setAdresse(association.getAdresse());
        dto.setDomaine(association.getDomaine());
        dto.setTelephone(association.getTelephone());
        dto.setPhoto(association.getPhoto());
        dto.setStatutBloquer(association.getStatutBloquer());
        dto.setAutorisation(association.getAutorisation());

        if (association.getAdministrateur() != null) {
            dto.setAdministrateurId(association.getAdministrateur().getId());
            dto.setAdministrateurNom(association.getAdministrateur().getNom() + " " + association.getAdministrateur().getPrenom());
        }

        // Statistiques
        dto.setNombreEnfants(associationRepository.countEnfantsByAssociationId(association.getId()));
        dto.setNombreParrains(associationRepository.countParrainsByAssociationId(association.getId()));

        return dto;
    }

    // Conversion DTO -> Entity
    private Association convertToEntity(AssociationRequestDTO dto) {
        Association association = new Association();
        association.setNom(dto.getNom().trim());
        association.setEmail(dto.getEmail().toLowerCase().trim());
        association.setAdresse(dto.getAdresse().trim());
        association.setDomaine(dto.getDomaine().trim());
        association.setMotDepasse(passwordEncoder.encode(dto.getMotDepasse()));
        association.setTelephone(dto.getTelephone().trim());
        association.setPhoto(dto.getPhoto());
        association.setStatutBloquer(false);
        association.setAutorisation("EN_ATTENTE");
        return association;
    }

    // Création d'association
    public AssociationResponseDTO createAssociation(AssociationRequestDTO associationRequestDTO) {
        try {
            // Validation des données
            validateAssociationData(associationRequestDTO);

            // Vérifier les doublons
            if (associationRepository.findByEmail(associationRequestDTO.getEmail().toLowerCase()).isPresent()) {
                throw new RuntimeException("Une association avec cet email existe déjà");
            }

            if (associationRepository.findByTelephone(associationRequestDTO.getTelephone()).isPresent()) {
                throw new RuntimeException("Une association avec ce téléphone existe déjà");
            }

            Optional<Administrateur> adminOpt = administrateurRepository.findById(associationRequestDTO.getAdministrateurId());
            if (adminOpt.isEmpty()) {
                throw new RuntimeException("Administrateur non trouvé");
            }

            Association association = convertToEntity(associationRequestDTO);
            association.setAdministrateur(adminOpt.get());

            Association savedAssociation = associationRepository.save(association);

            // Notification
            envoyerNotificationCreation(savedAssociation, adminOpt.get());

            return convertToDTO(savedAssociation);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création de l'association: " + e.getMessage());
        }
    }

    private void validateAssociationData(AssociationRequestDTO dto) {
        if (dto.getNom() == null || dto.getNom().trim().isEmpty()) {
            throw new RuntimeException("Le nom est obligatoire");
        }
        if (dto.getEmail() == null || !dto.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new RuntimeException("Format d'email invalide");
        }
        if (dto.getTelephone() == null || !dto.getTelephone().matches("^[+]?[0-9]{10,15}$")) {
            throw new RuntimeException("Format de téléphone invalide");
        }
    }

    private void envoyerNotificationCreation(Association association, Administrateur admin) {
        String message = String.format(
                "Nouvelle demande de création d'association:\n\n" +
                        "Nom: %s\nEmail: %s\nDomaine: %s\nTéléphone: %s\n\n" +
                        "Veuillez valider cette demande dans l'interface administrateur.",
                association.getNom(), association.getEmail(), association.getDomaine(), association.getTelephone()
        );

        Notification notification = new Notification();
        notification.setTypeNotifcation(TypeNotifcation.EMAIL);
        notification.setEnvoyeur("systeme@daimai.ml");
        notification.setRecepteur(admin.getEmail());
        notification.setContenue(message);
        notification.setAssociation(association);

        notificationService.envoiNotification(notification);
    }

    // Authentification
    public AssociationResponseDTO authentifier(LoginRequestDTO loginRequest) {
        try {
            Optional<Association> associationOpt = associationRepository.findByEmail(loginRequest.getEmail().toLowerCase());
            if (associationOpt.isEmpty()) {
                throw new RuntimeException("Email ou mot de passe incorrect");
            }

            Association association = associationOpt.get();

            if (Boolean.TRUE.equals(association.getStatutBloquer())) {
                throw new RuntimeException("Compte bloqué. Contactez l'administrateur.");
            }

            if (!"VALIDE".equals(association.getAutorisation())) {
                throw new RuntimeException("Compte en attente de validation");
            }

            if (!passwordEncoder.matches(loginRequest.getMotDepasse(), association.getMotDepasse())) {
                throw new RuntimeException("Email ou mot de passe incorrect");
            }

            return convertToDTO(association);

        } catch (Exception e) {
            throw new RuntimeException("Erreur d'authentification: " + e.getMessage());
        }
    }

    // Modification d'association
    public AssociationResponseDTO updateAssociation(Long id, AssociationRequestDTO associationRequestDTO) {
        try {
            Optional<Association> associationOpt = associationRepository.findById(id);
            if (associationOpt.isEmpty()) {
                throw new RuntimeException("Association non trouvée avec l'ID: " + id);
            }

            Association association = associationOpt.get();
            association.setNom(associationRequestDTO.getNom().trim());
            association.setAdresse(associationRequestDTO.getAdresse().trim());
            association.setDomaine(associationRequestDTO.getDomaine().trim());
            association.setTelephone(associationRequestDTO.getTelephone().trim());
            association.setPhoto(associationRequestDTO.getPhoto());

            if (associationRequestDTO.getMotDepasse() != null && !associationRequestDTO.getMotDepasse().isEmpty()) {
                association.setMotDepasse(passwordEncoder.encode(associationRequestDTO.getMotDepasse()));
            }

            Association updatedAssociation = associationRepository.save(association);
            return convertToDTO(updatedAssociation);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la modification de l'association: " + e.getMessage());
        }
    }

    // Désactiver/Activer association
    public AssociationResponseDTO toggleStatutAssociation(Long id, Boolean statutBloquer) {
        try {
            Optional<Association> associationOpt = associationRepository.findById(id);
            if (associationOpt.isEmpty()) {
                throw new RuntimeException("Association non trouvée avec l'ID: " + id);
            }

            Association association = associationOpt.get();
            association.setStatutBloquer(statutBloquer);

            Association updatedAssociation = associationRepository.save(association);
            return convertToDTO(updatedAssociation);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la modification du statut: " + e.getMessage());
        }
    }

    // Valider association
    public AssociationResponseDTO validerAssociation(Long id, String autorisation) {
        try {
            Optional<Association> associationOpt = associationRepository.findById(id);
            if (associationOpt.isEmpty()) {
                throw new RuntimeException("Association non trouvée avec l'ID: " + id);
            }

            Association association = associationOpt.get();
            association.setAutorisation(autorisation);

            Association updatedAssociation = associationRepository.save(association);

            // Notification de validation
            envoyerNotificationValidation(updatedAssociation);

            return convertToDTO(updatedAssociation);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la validation de l'association: " + e.getMessage());
        }
    }

    private void envoyerNotificationValidation(Association association) {
        String message = String.format(
                "Félicitations ! Votre association '%s' a été validée.\n\n" +
                        "Vous pouvez maintenant vous connecter à votre espace association et commencer à enregistrer des enfants.",
                association.getNom()
        );

        Notification notification = new Notification();
        notification.setTypeNotifcation(TypeNotifcation.EMAIL);
        notification.setEnvoyeur("validation@daimai.ml");
        notification.setRecepteur(association.getEmail());
        notification.setContenue(message);
        notification.setAssociation(association);

        notificationService.envoiNotification(notification);
    }

    // Récupérer toutes les associations
    public List<AssociationResponseDTO> getAllAssociations() {
        try {
            List<Association> associations = associationRepository.findAll();
            return associations.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des associations: " + e.getMessage());
        }
    }

    // Récupérer une association par ID
    public AssociationResponseDTO getAssociationById(Long id) {
        try {
            Optional<Association> associationOpt = associationRepository.findById(id);
            if (associationOpt.isEmpty()) {
                throw new RuntimeException("Association non trouvée avec l'ID: " + id);
            }
            return convertToDTO(associationOpt.get());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de l'association: " + e.getMessage());
        }
    }

    // Rechercher des associations
    public List<AssociationResponseDTO> searchAssociations(String recherche) {
        try {
            List<Association> associations = associationRepository.findByNomOrDomaineContaining(recherche);
            return associations.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche des associations: " + e.getMessage());
        }
    }

    // Récupérer les associations par domaine
    public List<AssociationResponseDTO> getAssociationsByDomaine(String domaine) {
        try {
            List<Association> associations = associationRepository.findByDomaine(domaine);
            return associations.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des associations par domaine: " + e.getMessage());
        }
    }

    // Récupérer les associations par statut
    public List<AssociationResponseDTO> getAssociationsByStatut(Boolean statutBloquer) {
        try {
            List<Association> associations = associationRepository.findByStatutBloquer(statutBloquer);
            return associations.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des associations par statut: " + e.getMessage());
        }
    }

    // Supprimer une association
    public void deleteAssociation(Long id) {
        try {
            if (!associationRepository.existsById(id)) {
                throw new RuntimeException("Association non trouvée avec l'ID: " + id);
            }
            associationRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de l'association: " + e.getMessage());
        }
    }

    // Méthodes statistiques
    public Integer getNombreTotalEnfants(Long associationId) {
        try {
            return associationRepository.countEnfantsByAssociationId(associationId);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul du nombre d'enfants: " + e.getMessage());
        }
    }

    public Integer getNombreTotalParrains(Long associationId) {
        try {
            return associationRepository.countParrainsByAssociationId(associationId);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul du nombre de parrains: " + e.getMessage());
        }
    }

    // === METHODES ENFANTS ===

    // Conversion Enfant -> EnfantResponseDTO
    private EnfantResponseDTO convertToEnfantDTO(Enfant enfant) {
        if (enfant == null) return null;

        EnfantResponseDTO dto = new EnfantResponseDTO();
        dto.setId(enfant.getId());
        dto.setNom(enfant.getNom());
        dto.setPrenom(enfant.getPrenom());
        dto.setDateNaissance(enfant.getDateNaissance());
        dto.setAge(Period.between(enfant.getDateNaissance(), LocalDate.now()).getYears());
        dto.setNiveauScolaire(enfant.getNiveauScolaire());
        dto.setUrlPhoto(enfant.getUrlPhoto());
        dto.setTuteur(enfant.getTuteur());
        dto.setTelephone(enfant.getTelephone());
        dto.setEmail(enfant.getEmail());
        dto.setStatutAbandon(enfant.getStatutAbandon());
        dto.setAssociationId(enfant.getAssociation().getId());
        dto.setAssociationNom(enfant.getAssociation().getNom());
        dto.setNombreParrains(enfant.getParrains() != null ? enfant.getParrains().size() : 0);

        return dto;
    }

    // Enregistrer un enfant avec création de compte parent
    public EnfantResponseDTO enregistrerEnfant(EnfantRequestDTO enfantRequestDTO) {
        try {
            // Validation
            Optional<Association> associationOpt = associationRepository.findById(enfantRequestDTO.getAssociationId());
            if (associationOpt.isEmpty()) {
                throw new RuntimeException("Association non trouvée avec l'ID: " + enfantRequestDTO.getAssociationId());
            }

            if (enfantRepository.findByEmail(enfantRequestDTO.getEmail().toLowerCase()).isPresent()) {
                throw new RuntimeException("Un compte avec cet email existe déjà");
            }

            // Création de l'enfant
            Enfant enfant = new Enfant();
            enfant.setNom(enfantRequestDTO.getNom().trim());
            enfant.setPrenom(enfantRequestDTO.getPrenom().trim());
            enfant.setDateNaissance(enfantRequestDTO.getDateNaissance());
            enfant.setNiveauScolaire(enfantRequestDTO.getNiveauScolaire());
            enfant.setUrlPhoto(enfantRequestDTO.getUrlPhoto());
            enfant.setTuteur(enfantRequestDTO.getTuteur().trim());
            enfant.setTelephone(enfantRequestDTO.getTelephone().trim());
            enfant.setEmail(enfantRequestDTO.getEmail().toLowerCase().trim());
            enfant.setStatutAbandon(false);
            enfant.setAssociation(associationOpt.get());

            // Génération mot de passe sécurisé
            String motDePasseTemporaire = genererMotDePasseTemporaire();
            enfant.setMotDepasse(passwordEncoder.encode(motDePasseTemporaire));

            Enfant savedEnfant = enfantRepository.save(enfant);

            // Notification des identifiants
            envoyerIdentifiantsParent(savedEnfant, motDePasseTemporaire);

            return convertToEnfantDTO(savedEnfant);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de l'enfant: " + e.getMessage());
        }
    }

    private String genererMotDePasseTemporaire() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private void envoyerIdentifiantsParent(Enfant enfant, String motDePasse) {
        String message = String.format(
                "Bonjour %s,\n\nVotre enfant %s %s a été enregistré dans le système Daimai.\n\n" +
                        "Vos identifiants de connexion:\nEmail: %s\nMot de passe temporaire: %s\n\n" +
                        "Veuillez changer votre mot de passe après votre première connexion.\n\n" +
                        "Cordialement,\nL'équipe Daimai",
                enfant.getTuteur(), enfant.getPrenom(), enfant.getNom(), enfant.getEmail(), motDePasse
        );

        Notification notification = new Notification();
        notification.setTypeNotifcation(TypeNotifcation.EMAIL);
        notification.setEnvoyeur("inscription@daimai.ml");
        notification.setRecepteur(enfant.getEmail());
        notification.setContenue(message);
        notification.setAssociation(enfant.getAssociation());

        notificationService.envoiNotification(notification);
    }

    // Activer/Désactiver un enfant
    public EnfantResponseDTO toggleStatutEnfant(Long enfantId, Boolean statutAbandon) {
        try {
            Optional<Enfant> enfantOpt = enfantRepository.findById(enfantId);
            if (enfantOpt.isEmpty()) {
                throw new RuntimeException("Enfant non trouvé avec l'ID: " + enfantId);
            }

            Enfant enfant = enfantOpt.get();
            enfant.setStatutAbandon(statutAbandon);

            Enfant updatedEnfant = enfantRepository.save(enfant);
            return convertToEnfantDTO(updatedEnfant);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la modification du statut de l'enfant: " + e.getMessage());
        }
    }

    // Récupérer les enfants d'une association
    public List<EnfantResponseDTO> getEnfantsByAssociation(Long associationId) {
        try {
            List<Enfant> enfants = enfantRepository.findByAssociationId(associationId);
            return enfants.stream()
                    .map(this::convertToEnfantDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des enfants: " + e.getMessage());
        }
    }

    // Récupérer un enfant par ID
    public EnfantResponseDTO getEnfantById(Long enfantId) {
        try {
            Optional<Enfant> enfantOpt = enfantRepository.findById(enfantId);
            if (enfantOpt.isEmpty()) {
                throw new RuntimeException("Enfant non trouvé avec l'ID: " + enfantId);
            }
            return convertToEnfantDTO(enfantOpt.get());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de l'enfant: " + e.getMessage());
        }
    }

    // === METHODES PAIEMENTS ===

    // Enregistrer un paiement effectué par un parrain
    public PaiementResponseDTO enregistrerPaiementParrain(PaiementRequestDTO paiementRequestDTO) {
        try {
            // Vérifier que le parrain existe
            Optional<Parrain> parrainOpt = parrainRepository.findById(paiementRequestDTO.getParrainId());
            if (parrainOpt.isEmpty()) {
                throw new RuntimeException("Parrain non trouvé avec l'ID: " + paiementRequestDTO.getParrainId());
            }

            // Créer le paiement
            Paiement paiement = new Paiement();
            paiement.setMontant(paiementRequestDTO.getMontant());
            paiement.setDatePaiement(LocalDate.now());
            paiement.setModePaiement(paiementRequestDTO.getModePaiement());
            paiement.setStatutPaiement(groupe_9_daimai.com.Daimai.Entite.enums.StatutPaiement.CONFIRME);
            paiement.setParrain(parrainOpt.get());

            Paiement savedPaiement = paiementRepository.save(paiement);

            // Convertir en DTO
            PaiementResponseDTO responseDTO = new PaiementResponseDTO();
            responseDTO.setId(savedPaiement.getId());
            responseDTO.setMontant(savedPaiement.getMontant());
            responseDTO.setDatePaiement(savedPaiement.getDatePaiement());
            responseDTO.setModePaiement(savedPaiement.getModePaiement());
            responseDTO.setStatutPaiement(savedPaiement.getStatutPaiement());
            responseDTO.setParrainId(savedPaiement.getParrain().getId());
            responseDTO.setParrainNom(savedPaiement.getParrain().getNom());
            responseDTO.setParrainPrenom(savedPaiement.getParrain().getPrenom());
            responseDTO.setMessageStatut("Paiement enregistré avec succès");

            return responseDTO;

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du paiement: " + e.getMessage());
        }
    }

    // === METHODES RAPPORTS ===

    // Générer un rapport scolaire
    public RapportScolaire genererRapportScolaire(RapportRequestDTO rapportRequestDTO) {
        try {
            Optional<Enfant> enfantOpt = enfantRepository.findById(rapportRequestDTO.getEnfantId());
            if (enfantOpt.isEmpty()) {
                throw new RuntimeException("Enfant non trouvé avec l'ID: " + rapportRequestDTO.getEnfantId());
            }

            RapportScolaire rapport = new RapportScolaire();
            rapport.setEnfant(enfantOpt.get());
            rapport.setDateRapport(rapportRequestDTO.getDateRapport());
            rapport.setNotes(rapportRequestDTO.getNotes());
            rapport.setPresence(rapportRequestDTO.getPresence());
            rapport.setComportement(rapportRequestDTO.getComportement());
            rapport.setMoyenneGenerale(rapportRequestDTO.getMoyenneGenerale());
            rapport.setPhotoClasse(rapportRequestDTO.getPhotoClasse());

            RapportScolaire savedRapport = rapportScolaireRepository.save(rapport);

            // Notifier les parrains
            notifierParrainsRapport(savedRapport);

            return savedRapport;

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du rapport scolaire: " + e.getMessage());
        }
    }

    private void notifierParrainsRapport(RapportScolaire rapport) {
        Enfant enfant = rapport.getEnfant();
        if (enfant.getParrains() != null && !enfant.getParrains().isEmpty()) {
            for (Parrain parrain : enfant.getParrains()) {
                String message = String.format(
                        "Bonjour %s %s,\n\nNouveau rapport scolaire disponible pour %s %s:\n" +
                                "Date: %s\nMoyenne: %.2f/20\nComportement: %s\n\n" +
                                "Connectez-vous pour consulter le rapport complet.\n\n" +
                                "Cordialement,\nL'équipe Daimai",
                        parrain.getPrenom(), parrain.getNom(), enfant.getPrenom(), enfant.getNom(),
                        rapport.getDateRapport(), rapport.getMoyenneGenerale(), rapport.getComportement()
                );

                Notification notification = new Notification();
                notification.setTypeNotifcation(TypeNotifcation.EMAIL);
                notification.setEnvoyeur("rapports@daimai.ml");
                notification.setRecepteur(parrain.getEmail());
                notification.setContenue(message);
                notification.setAssociation(enfant.getAssociation());

                notificationService.envoiNotification(notification);
            }
        }
    }

    // Générer un rapport financier
    public Map<String, Object> genererRapportFinancier(Long associationId, LocalDate dateDebut, LocalDate dateFin) {
        try {
            Optional<Association> associationOpt = associationRepository.findById(associationId);
            if (associationOpt.isEmpty()) {
                throw new RuntimeException("Association non trouvée avec l'ID: " + associationId);
            }

            // Récupérer les paiements pour la période
            List<Paiement> paiements = paiementRepository.findPaiementsByAssociationAndPeriod(associationId, dateDebut, dateFin);

            // Récupérer les dépenses pour la période
            List<Depense> depenses = depenseRepository.findByAssociationIdAndDateDepenseBetween(associationId, dateDebut, dateFin);

            // Calculer les totaux
            Double totalPaiements = paiements.stream()
                    .mapToDouble(Paiement::getMontant)
                    .sum();

            Double totalDepenses = depenses.stream()
                    .mapToDouble(Depense::getMontant)
                    .sum();

            Double solde = totalPaiements - totalDepenses;

            // Préparer le rapport
            Map<String, Object> rapport = new HashMap<>();
            rapport.put("association", associationOpt.get().getNom());
            rapport.put("periode", dateDebut + " à " + dateFin);
            rapport.put("totalPaiements", totalPaiements);
            rapport.put("totalDepenses", totalDepenses);
            rapport.put("solde", solde);
            rapport.put("nombrePaiements", paiements.size());
            rapport.put("nombreDepenses", depenses.size());
            rapport.put("paiements", paiements);
            rapport.put("depenses", depenses);

            return rapport;

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du rapport financier: " + e.getMessage());
        }
    }

    // === METHODES PARRAINAGE ===

    // Envoyer une demande de parrainage
    public void envoyerDemandeParrainage(ParrainageRequestDTO parrainageRequestDTO) {
        try {
            Optional<Enfant> enfantOpt = enfantRepository.findById(parrainageRequestDTO.getEnfantId());
            Optional<Parrain> parrainOpt = parrainRepository.findById(parrainageRequestDTO.getParrainId());

            if (enfantOpt.isEmpty()) {
                throw new RuntimeException("Enfant non trouvé avec l'ID: " + parrainageRequestDTO.getEnfantId());
            }
            if (parrainOpt.isEmpty()) {
                throw new RuntimeException("Parrain non trouvé avec l'ID: " + parrainageRequestDTO.getParrainId());
            }

            envoyerNotificationDemandeParrainage(enfantOpt.get(), parrainOpt.get(), parrainageRequestDTO.getMessagePersonnalise());

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'envoi de la demande de parrainage: " + e.getMessage());
        }
    }

    private void envoyerNotificationDemandeParrainage(Enfant enfant, Parrain parrain, String messagePersonnalise) {
        int age = Period.between(enfant.getDateNaissance(), LocalDate.now()).getYears();
        String message = String.format(
                "Bonjour %s %s,\n\n" +
                        "L'association %s vous propose de parrainer %s %s, un enfant de %s ans.\n\n" +
                        "%s\n\n" +
                        "Informations sur l'enfant:\n" +
                        "• Âge: %s ans\n" +
                        "• Niveau scolaire: %s\n" +
                        "• Situation familiale: %s\n\n" +
                        "Pour accepter cette demande, connectez-vous à votre espace parrain.\n\n" +
                        "Cordialement,\nL'équipe Daimai",
                parrain.getPrenom(), parrain.getNom(),
                enfant.getAssociation().getNom(),
                enfant.getPrenom(), enfant.getNom(), age,
                messagePersonnalise != null ? messagePersonnalise : "Votre soutien pourrait changer sa vie.",
                age,
                enfant.getNiveauScolaire(),
                enfant.getTuteur()
        );

        Notification notification = new Notification();
        notification.setTypeNotifcation(TypeNotifcation.EMAIL);
        notification.setEnvoyeur("parrainage@daimai.ml");
        notification.setRecepteur(parrain.getEmail());
        notification.setContenue(message);
        notification.setAssociation(enfant.getAssociation());

        notificationService.envoiNotification(notification);
    }
}
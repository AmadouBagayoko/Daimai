package groupe_9_daimai.com.Daimai.Service;

import groupe_9_daimai.com.Daimai.DTO.RecuRequestDTO;
import groupe_9_daimai.com.Daimai.DTO.RecuResponseDTO;
import groupe_9_daimai.com.Daimai.Entite.*;
import groupe_9_daimai.com.Daimai.Entite.enums.TypeNotifcation;
import groupe_9_daimai.com.Daimai.Repository.PaiementRepository;
import groupe_9_daimai.com.Daimai.Repository.RecuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecuService {

    @Autowired
    private RecuRepository recuRepository;

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private NotificationService notificationService;

    // Générer un code de reçu unique
    private String generateRecuCode(Long paiementId) {
        String datePart = LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomPart = String.valueOf((int) (Math.random() * 10000));
        return String.format("RECU-%s-%s-%d", datePart, randomPart, paiementId);
    }

    // Conversion Entity -> DTO
    private RecuResponseDTO convertToDTO(Recu recu) {
        RecuResponseDTO dto = new RecuResponseDTO();
        dto.setId(recu.getId());
        dto.setMontantPayer(recu.getMontantPayer());
        dto.setDate(recu.getDate());
        dto.setPaiementId(recu.getPaiement().getId());
        dto.setCodeRecu(recu.getCodeRecu());

        if (recu.getPaiement().getParrain() != null) {
            dto.setParrainNom(recu.getPaiement().getParrain().getNom());
            dto.setParrainPrenom(recu.getPaiement().getParrain().getPrenom());
            dto.setParrainEmail(recu.getPaiement().getParrain().getEmail());
            dto.setModePaiement(recu.getPaiement().getModePaiement().name());
        }

        return dto;
    }

    // Conversion DTO -> Entity
    private Recu convertToEntity(RecuRequestDTO dto, Paiement paiement) {
        Recu recu = new Recu();
        recu.setMontantPayer(dto.getMontantPayer());
        recu.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());
        recu.setPaiement(paiement);
        recu.setCodeRecu(generateRecuCode(paiement.getId()));
        return recu;
    }

    // Méthode pour créer et envoyer les notifications de reçu
    private void envoyerNotificationsRecu(Recu recu, Parrain parrain, Association association) {
        // Notification EMAIL
        String messageEmail = creerMessageEmail(recu, parrain);
        Notification notificationEmail = new Notification();
        notificationEmail.setTypeNotifcation(TypeNotifcation.EMAIL);
        notificationEmail.setEnvoyeur("systeme@daimai.ml");
        notificationEmail.setRecepteur(parrain.getEmail());
        notificationEmail.setContenue(messageEmail);
        notificationEmail.setAssociation(association);

        notificationService.envoiNotification(notificationEmail);

        // Notification SMS (si le téléphone est disponible)
        if (parrain.getTelephone() != null && !parrain.getTelephone().isEmpty()) {
            String messageSMS = creerMessageSMS(recu, parrain);
            Notification notificationSMS = new Notification();
            notificationSMS.setTypeNotifcation(TypeNotifcation.SMS);
            notificationSMS.setEnvoyeur("DAIMAI");
            notificationSMS.setRecepteur(parrain.getTelephone());
            notificationSMS.setContenue(messageSMS);
            notificationSMS.setAssociation(association);

            notificationService.envoiNotification(notificationSMS);
        }
    }

    private String creerMessageEmail(Recu recu, Parrain parrain) {
        return String.format(
                "Bonjour %s %s,\n\n" +
                        "VOTRE REÇU DE PAIEMENT - DAIMAI\n" +
                        "───────────────────────────────\n\n" +
                        "📋 Code du reçu: %s\n" +
                        "💰 Montant payé: %.2f FCFA\n" +
                        "📅 Date: %s\n" +
                        "💳 Mode de paiement: %s\n\n" +
                        "Merci pour votre générosité !\n\n" +
                        "Cordialement,\n" +
                        "L'équipe Daimai",
                parrain.getPrenom(), parrain.getNom(),
                recu.getCodeRecu(),
                recu.getMontantPayer(),
                recu.getDate(),
                recu.getPaiement().getModePaiement()
        );
    }

    private String creerMessageSMS(Recu recu, Parrain parrain) {
        return String.format(
                "Reçu Daimai: %.2f FCFA payes. Code: %s. Merci!",
                recu.getMontantPayer(),
                recu.getCodeRecu()
        );
    }

    // Créer un reçu avec notification
    public RecuResponseDTO createRecu(RecuRequestDTO recuRequestDTO) {
        try {
            // Vérifier que le paiement existe
            Optional<Paiement> paiementOpt = paiementRepository.findById(recuRequestDTO.getPaiementId());
            if (paiementOpt.isEmpty()) {
                throw new RuntimeException("Paiement non trouvé avec l'ID: " + recuRequestDTO.getPaiementId());
            }

            // Vérifier qu'un reçu n'existe pas déjà pour ce paiement
            if (recuRepository.existsByPaiementId(recuRequestDTO.getPaiementId())) {
                throw new RuntimeException("Un reçu existe déjà pour ce paiement");
            }

            Paiement paiement = paiementOpt.get();
            Recu recu = convertToEntity(recuRequestDTO, paiement);

            Recu savedRecu = recuRepository.save(recu);

            // Récupérer l'association et envoyer les notifications
            Association association = getAssociationFromPaiement(paiement);
            envoyerNotificationsRecu(savedRecu, paiement.getParrain(), association);

            return convertToDTO(savedRecu);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création du reçu: " + e.getMessage());
        }
    }

    // Méthode pour récupérer l'association
    private Association getAssociationFromPaiement(Paiement paiement) {
        // Implémentation selon votre modèle de données
        // Exemple simplifié - à adapter
        Association association = new Association();
        association.setNom("Association Daimai");
        association.setEmail("contact@daimai.ml");
        return association;
    }

    // Générer un reçu automatiquement à partir d'un paiement confirmé
    public RecuResponseDTO genererRecuAutomatique(Long paiementId) {
        try {
            Optional<Paiement> paiementOpt = paiementRepository.findById(paiementId);
            if (paiementOpt.isEmpty()) {
                throw new RuntimeException("Paiement non trouvé avec l'ID: " + paiementId);
            }

            if (recuRepository.existsByPaiementId(paiementId)) {
                throw new RuntimeException("Un reçu existe déjà pour ce paiement");
            }

            Paiement paiement = paiementOpt.get();

            // Vérifier que le paiement est confirmé
            if (paiement.getStatutPaiement() != groupe_9_daimai.com.Daimai.Entite.enums.StatutPaiement.CONFIRME) {
                throw new RuntimeException("Impossible de générer un reçu pour un paiement non confirmé");
            }

            Recu recu = new Recu();
            recu.setMontantPayer(paiement.getMontant());
            recu.setDate(LocalDate.now());
            recu.setPaiement(paiement);
            recu.setCodeRecu(generateRecuCode(paiementId));

            Recu savedRecu = recuRepository.save(recu);

            // Envoyer les notifications
            Association association = getAssociationFromPaiement(paiement);
            envoyerNotificationsRecu(savedRecu, paiement.getParrain(), association);

            return convertToDTO(savedRecu);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération automatique du reçu: " + e.getMessage());
        }
    }

    // Récupérer tous les reçus
    public List<RecuResponseDTO> getAllRecus() {
        try {
            List<Recu> recus = recuRepository.findAll();
            return recus.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des reçus: " + e.getMessage());
        }
    }

    // Récupérer un reçu par ID
    public RecuResponseDTO getRecuById(Long id) {
        try {
            Optional<Recu> recuOpt = recuRepository.findById(id);
            if (recuOpt.isEmpty()) {
                throw new RuntimeException("Reçu non trouvé avec l'ID: " + id);
            }
            return convertToDTO(recuOpt.get());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération du reçu: " + e.getMessage());
        }
    }

    // Récupérer un reçu par code
    public RecuResponseDTO getRecuByCode(String codeRecu) {
        try {
            Optional<Recu> recuOpt = recuRepository.findByCodeRecu(codeRecu);
            if (recuOpt.isEmpty()) {
                throw new RuntimeException("Reçu non trouvé avec le code: " + codeRecu);
            }
            return convertToDTO(recuOpt.get());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération du reçu: " + e.getMessage());
        }
    }

    // Récupérer un reçu par paiement
    public RecuResponseDTO getRecuByPaiement(Long paiementId) {
        try {
            Optional<Recu> recuOpt = recuRepository.findByPaiementId(paiementId);
            if (recuOpt.isEmpty()) {
                throw new RuntimeException("Reçu non trouvé pour le paiement ID: " + paiementId);
            }
            return convertToDTO(recuOpt.get());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération du reçu: " + e.getMessage());
        }
    }

    // Modifier un reçu
    public RecuResponseDTO updateRecu(Long id, RecuRequestDTO recuRequestDTO) {
        try {
            Optional<Recu> recuOpt = recuRepository.findById(id);
            if (recuOpt.isEmpty()) {
                throw new RuntimeException("Reçu non trouvé avec l'ID: " + id);
            }

            Optional<Paiement> paiementOpt = paiementRepository.findById(recuRequestDTO.getPaiementId());
            if (paiementOpt.isEmpty()) {
                throw new RuntimeException("Paiement non trouvé avec l'ID: " + recuRequestDTO.getPaiementId());
            }

            Recu recu = recuOpt.get();
            recu.setMontantPayer(recuRequestDTO.getMontantPayer());
            recu.setDate(recuRequestDTO.getDate());

            // Vérifier si le paiement a changé
            if (!recu.getPaiement().getId().equals(recuRequestDTO.getPaiementId())) {
                recu.setPaiement(paiementOpt.get());
            }

            Recu updatedRecu = recuRepository.save(recu);
            return convertToDTO(updatedRecu);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la modification du reçu: " + e.getMessage());
        }
    }

    // Supprimer un reçu
    public void deleteRecu(Long id) {
        try {
            if (!recuRepository.existsById(id)) {
                throw new RuntimeException("Reçu non trouvé avec l'ID: " + id);
            }
            recuRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression du reçu: " + e.getMessage());
        }
    }

    // Récupérer les reçus d'un parrain
    public List<RecuResponseDTO> getRecusByParrain(Long parrainId) {
        try {
            List<Recu> recus = recuRepository.findByParrainId(parrainId);
            return recus.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des reçus du parrain: " + e.getMessage());
        }
    }

    // Récupérer les reçus par période
    public List<RecuResponseDTO> getRecusByPeriod(LocalDate startDate, LocalDate endDate) {
        try {
            List<Recu> recus = recuRepository.findByDateBetween(startDate, endDate);
            return recus.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des reçus par période: " + e.getMessage());
        }
    }

    // Calculer le total des reçus d'un parrain
    public Double getTotalRecusByParrain(Long parrainId) {
        try {
            Double total = recuRepository.getTotalMontantByParrainId(parrainId);
            return total != null ? total : 0.0;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul du total des reçus: " + e.getMessage());
        }
    }

    // Renvoyer la notification d'un reçu
    public void renvoyerNotificationRecu(Long recuId) {
        try {
            Optional<Recu> recuOpt = recuRepository.findById(recuId);
            if (recuOpt.isEmpty()) {
                throw new RuntimeException("Reçu non trouvé avec l'ID: " + recuId);
            }

            Recu recu = recuOpt.get();
            Association association = getAssociationFromPaiement(recu.getPaiement());

            // Renvoyer les notifications
            envoyerNotificationsRecu(recu, recu.getPaiement().getParrain(), association);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du renvoi de la notification: " + e.getMessage());
        }
    }
}
package groupe_9_daimai.com.Daimai.Service;

import groupe_9_daimai.com.Daimai.DTO.RecuRequestDTO;
import groupe_9_daimai.com.Daimai.DTO.RecuResponseDTO;
import groupe_9_daimai.com.Daimai.Entite.Paiement;
import groupe_9_daimai.com.Daimai.Entite.Recu;
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
    private RecuCodeGenerator codeGenerator;

    @Autowired
    private NotificationService notificationService;

    // Conversion Entity -> DTO
    private RecuResponseDTO convertToDTO(Recu recu) {
        RecuResponseDTO dto = new RecuResponseDTO();
        dto.setId(recu.getId());
        dto.setMontantPayer(recu.getMontantPayer());
        dto.setDate(recu.getDate());
        dto.setPaiementId(recu.getPaiement().getId());
        dto.setCodeRecu(recu.getCodeRecu());

        // Informations du parrain
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
        recu.setCodeRecu(codeGenerator.generateRecuCode(paiement.getId()));
        return recu;
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

            // Envoyer la notification
            notificationService.envoyerNotificationRecu(savedRecu, paiement.getParrain());

            return convertToDTO(savedRecu);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création du reçu: " + e.getMessage());
        }
    }

    // Générer un reçu automatiquement à partir d'un paiement
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

            Recu recu = new Recu();
            recu.setMontantPayer(paiement.getMontant());
            recu.setDate(LocalDate.now());
            recu.setPaiement(paiement);
            recu.setCodeRecu(codeGenerator.generateRecuCode(paiementId));

            Recu savedRecu = recuRepository.save(recu);

            // Envoyer la notification
            notificationService.envoyerNotificationRecu(savedRecu, paiement.getParrain());

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

    // Méthodes spécifiques supplémentaires

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
            notificationService.envoyerNotificationRecu(recu, recu.getPaiement().getParrain());

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du renvoi de la notification: " + e.getMessage());
        }
    }
}
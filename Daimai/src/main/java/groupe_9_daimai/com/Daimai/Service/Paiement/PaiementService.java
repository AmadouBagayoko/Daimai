package groupe_9_daimai.com.Daimai.Service.Paiement;

import groupe_9_daimai.com.Daimai.DTO.PaiementRequestDTO;
import groupe_9_daimai.com.Daimai.DTO.PaiementResponseDTO;
import groupe_9_daimai.com.Daimai.Entite.Paiement;
import groupe_9_daimai.com.Daimai.Entite.Parrain;
import groupe_9_daimai.com.Daimai.Entite.enums.StatutPaiement;
import groupe_9_daimai.com.Daimai.Repository.PaiementRepository;
import groupe_9_daimai.com.Daimai.Repository.ParrainRepository;
import groupe_9_daimai.com.Daimai.Service.Paiement.Processor.PaiementProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private ParrainRepository parrainRepository;

    @Autowired
    private List<PaiementProcessor> paiementProcessors;

    // Trouver le processeur approprié
    private PaiementProcessor getProcessor(groupe_9_daimai.com.Daimai.Entite.enums.ModePaiement modePaiement) {
        return paiementProcessors.stream()
                .filter(processor -> processor.supports(modePaiement))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Processeur de paiement non trouvé pour: " + modePaiement));
    }

    // Conversion Entity -> DTO
    private PaiementResponseDTO convertToDTO(Paiement paiement) {
        PaiementResponseDTO dto = new PaiementResponseDTO();
        dto.setId(paiement.getId());
        dto.setMontant(paiement.getMontant());
        dto.setDatePaiement(paiement.getDatePaiement());
        dto.setModePaiement(paiement.getModePaiement());
        dto.setStatutPaiement(paiement.getStatutPaiement());
        return dto;
    }

    // Conversion DTO -> Entity
    private Paiement convertToEntity(PaiementRequestDTO dto, Parrain parrain) {
        Paiement paiement = new Paiement();
        paiement.setMontant(dto.getMontant());
        paiement.setModePaiement(dto.getModePaiement());
        paiement.setDatePaiement(dto.getDatePaiement() != null ? dto.getDatePaiement() : LocalDate.now());
        return paiement;
    }

    // Effectuer un paiement
    public PaiementResponseDTO effectuerPaiement(PaiementRequestDTO paiementRequestDTO) {
        try {
            // Vérifier le parrain
            Optional<Parrain> parrainOpt = parrainRepository.findById(paiementRequestDTO.getParrainId());
            if (parrainOpt.isEmpty()) {
                throw new RuntimeException("Parrain non trouvé avec l'ID: " + paiementRequestDTO.getParrainId());
            }

            Parrain parrain = parrainOpt.get();
            Paiement paiement = convertToEntity(paiementRequestDTO, parrain);

            // Traiter le paiement avec le processeur approprié
            PaiementProcessor processor = getProcessor(paiementRequestDTO.getModePaiement());
            ProcessPaiementResult result = processor.process(paiementRequestDTO);

            // Mettre à jour le statut du paiement
            paiement.setStatutPaiement(result.getStatut());

            Paiement savedPaiement = paiementRepository.save(paiement);
            PaiementResponseDTO responseDTO = convertToDTO(savedPaiement);
            responseDTO.setMessageStatut(result.getMessage());

            return responseDTO;

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du paiement: " + e.getMessage());
        }
    }

    // Récupérer tous les paiements
    public List<PaiementResponseDTO> getAllPaiements() {
        try {
            return paiementRepository.findAllPaiementDTOs();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des paiements: " + e.getMessage());
        }
    }

    // Récupérer un paiement par ID
    public PaiementResponseDTO getPaiementById(Long id) {
        try {
            PaiementResponseDTO paiementDTO = paiementRepository.findPaiementDTOById(id);
            if (paiementDTO == null) {
                throw new RuntimeException("Paiement non trouvé avec l'ID: " + id);
            }
            return paiementDTO;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération du paiement: " + e.getMessage());
        }
    }

    // Mettre à jour le statut d'un paiement
    public PaiementResponseDTO updateStatutPaiement(Long id, StatutPaiement nouveauStatut) {
        try {
            Optional<Paiement> paiementOpt = paiementRepository.findById(id);
            if (paiementOpt.isEmpty()) {
                throw new RuntimeException("Paiement non trouvé avec l'ID: " + id);
            }

            Paiement paiement = paiementOpt.get();
            paiement.setStatutPaiement(nouveauStatut);
            Paiement updatedPaiement = paiementRepository.save(paiement);

            return convertToDTO(updatedPaiement);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour du statut: " + e.getMessage());
        }
    }

    // Vérifier le statut d'un paiement
    public PaiementResponseDTO verifierStatutPaiement(Long id) {
        try {
            Optional<Paiement> paiementOpt = paiementRepository.findById(id);
            if (paiementOpt.isEmpty()) {
                throw new RuntimeException("Paiement non trouvé avec l'ID: " + id);
            }

            Paiement paiement = paiementOpt.get();
            PaiementProcessor processor = getProcessor(paiement.getModePaiement());
            StatutPaiement nouveauStatut = processor.checkStatus(paiement.getId().toString());

            // Mettre à jour si le statut a changé
            if (paiement.getStatutPaiement() != nouveauStatut) {
                paiement.setStatutPaiement(nouveauStatut);
                paiementRepository.save(paiement);
            }

            PaiementResponseDTO responseDTO = convertToDTO(paiement);
            responseDTO.setMessageStatut("Statut vérifié: " + nouveauStatut);

            return responseDTO;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la vérification du statut: " + e.getMessage());
        }
    }

    // Méthodes supplémentaires
    public List<PaiementResponseDTO> getPaiementsByParrain(Long parrainId) {
        try {
            List<Paiement> paiements = paiementRepository.findByParrainId(parrainId);
            return paiements.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des paiements du parrain: " + e.getMessage());
        }
    }

    public Double getTotalPaiementsParrain(Long parrainId) {
        try {
            Double total = paiementRepository.getTotalPaiementsConfirmesByParrain(parrainId);
            return total != null ? total : 0.0;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul du total des paiements: " + e.getMessage());
        }
    }

    public List<PaiementResponseDTO> getPaiementsByPeriode(LocalDate startDate, LocalDate endDate) {
        try {
            List<Paiement> paiements = paiementRepository.findByDatePaiementBetween(startDate, endDate);
            return paiements.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des paiements par période: " + e.getMessage());
        }
    }
}
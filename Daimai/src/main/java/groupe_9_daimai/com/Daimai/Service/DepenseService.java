package groupe_9_daimai.com.Daimai.Service;

import groupe_9_daimai.com.Daimai.DTO.DepenseRequestDTO;
import groupe_9_daimai.com.Daimai.DTO.DepenseResponseDTO;
import groupe_9_daimai.com.Daimai.Entite.Depense;
import groupe_9_daimai.com.Daimai.Entite.Association;
import groupe_9_daimai.com.Daimai.Entite.AnneeScolaire;
import groupe_9_daimai.com.Daimai.Entite.enums.CategorieDepense;
import groupe_9_daimai.com.Daimai.Repository.DepenseRepository;
import groupe_9_daimai.com.Daimai.Repository.AssociationRepository;
import groupe_9_daimai.com.Daimai.Repository.AnneeScolaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepenseService {

    @Autowired
    private DepenseRepository depenseRepository;

    @Autowired
    private AssociationRepository associationRepository;

    @Autowired
    private AnneeScolaireRepository anneeScolaireRepository;

    // Convertir Entity vers ResponseDTO
    private DepenseResponseDTO convertToDTO(Depense depense) {
        if (depense == null) return null;

        // Vérifier si l'année scolaire est active
        boolean anneeActive = false;
        if (depense.getAnneeScolaire() != null) {
            LocalDate now = LocalDate.now();
            anneeActive = !now.isBefore(depense.getAnneeScolaire().getDateDebut()) &&
                    !now.isAfter(depense.getAnneeScolaire().getDateFin());
        }

        return new DepenseResponseDTO(
                depense.getId(),
                depense.getMontant(),
                depense.getCategorie(),
                depense.getDateDepense(),
                depense.getAssociation().getId(),
                depense.getAssociation().getNom(),
                depense.getAnneeScolaire() != null ? depense.getAnneeScolaire().getId() : null,
                depense.getAnneeScolaire() != null ? depense.getAnneeScolaire().getLibelle() : null,
                anneeActive
        );
    }

    // Convertir RequestDTO vers Entity
    private Depense convertToEntity(DepenseRequestDTO depenseDTO, Association association, AnneeScolaire anneeScolaire) {
        Depense depense = new Depense();
        depense.setMontant(depenseDTO.getMontant());
        depense.setCategorie(depenseDTO.getCategorie());
        depense.setDateDepense(depenseDTO.getDateDepense() != null ? depenseDTO.getDateDepense() : LocalDate.now());
        depense.setAssociation(association);
        depense.setAnneeScolaire(anneeScolaire);
        return depense;
    }

    // Créer une dépense avec DTO
    public DepenseResponseDTO createDepense(DepenseRequestDTO depenseRequestDTO) {
        try {
            // Vérifier que l'association existe
            Optional<Association> associationOpt = associationRepository.findById(depenseRequestDTO.getAssociationId());
            if (associationOpt.isEmpty()) {
                throw new RuntimeException("Association non trouvée avec l'ID: " + depenseRequestDTO.getAssociationId());
            }

            // Vérifier que l'année scolaire existe
            Optional<AnneeScolaire> anneeScolaireOpt = anneeScolaireRepository.findById(depenseRequestDTO.getAnneeScolaireId());
            if (anneeScolaireOpt.isEmpty()) {
                throw new RuntimeException("Année scolaire non trouvée avec l'ID: " + depenseRequestDTO.getAnneeScolaireId());
            }

            // Vérifier que l'année scolaire appartient à l'association
            AnneeScolaire anneeScolaire = anneeScolaireOpt.get();
            if (!anneeScolaire.getAssociation().getId().equals(depenseRequestDTO.getAssociationId())) {
                throw new RuntimeException("L'année scolaire n'appartient pas à cette association");
            }

            Association association = associationOpt.get();
            Depense depense = convertToEntity(depenseRequestDTO, association, anneeScolaire);

            Depense savedDepense = depenseRepository.save(depense);
            return convertToDTO(savedDepense);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création de la dépense: " + e.getMessage());
        }
    }

    // Récupérer toutes les dépenses avec DTO
    public List<DepenseResponseDTO> getAllDepenses() {
        try {
            return depenseRepository.findAllDepenseDTOs();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des dépenses: " + e.getMessage());
        }
    }

    // Récupérer une dépense par ID avec DTO
    public DepenseResponseDTO getDepenseById(Long id) {
        try {
            DepenseResponseDTO depenseDTO = depenseRepository.findDepenseDTOById(id);
            if (depenseDTO == null) {
                throw new RuntimeException("Dépense non trouvée avec l'ID: " + id);
            }
            return depenseDTO;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de la dépense: " + e.getMessage());
        }
    }

    // Modifier une dépense avec DTO
    public DepenseResponseDTO updateDepense(Long id, DepenseRequestDTO depenseRequestDTO) {
        try {
            Optional<Depense> depenseOpt = depenseRepository.findById(id);
            if (depenseOpt.isEmpty()) {
                throw new RuntimeException("Dépense non trouvée avec l'ID: " + id);
            }

            // Vérifier que l'année scolaire existe
            Optional<AnneeScolaire> anneeScolaireOpt = anneeScolaireRepository.findById(depenseRequestDTO.getAnneeScolaireId());
            if (anneeScolaireOpt.isEmpty()) {
                throw new RuntimeException("Année scolaire non trouvée avec l'ID: " + depenseRequestDTO.getAnneeScolaireId());
            }

            Depense depense = depenseOpt.get();
            depense.setMontant(depenseRequestDTO.getMontant());
            depense.setCategorie(depenseRequestDTO.getCategorie());
            depense.setDateDepense(depenseRequestDTO.getDateDepense());
            depense.setAnneeScolaire(anneeScolaireOpt.get());

            // Vérifier si l'association a changé
            if (!depense.getAssociation().getId().equals(depenseRequestDTO.getAssociationId())) {
                Optional<Association> newAssociationOpt = associationRepository.findById(depenseRequestDTO.getAssociationId());
                if (newAssociationOpt.isEmpty()) {
                    throw new RuntimeException("Nouvelle association non trouvée avec l'ID: " + depenseRequestDTO.getAssociationId());
                }
                depense.setAssociation(newAssociationOpt.get());
            }

            Depense updatedDepense = depenseRepository.save(depense);
            return convertToDTO(updatedDepense);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la modification de la dépense: " + e.getMessage());
        }
    }

    // Supprimer une dépense
    public void deleteDepense(Long id) {
        try {
            if (!depenseRepository.existsById(id)) {
                throw new RuntimeException("Dépense non trouvée avec l'ID: " + id);
            }
            depenseRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de la dépense: " + e.getMessage());
        }
    }

    // === METHODES SPECIFIQUES AVEC ANNEE SCOLAIRE ===

    // Récupérer les dépenses par association avec DTO
    public List<DepenseResponseDTO> getDepensesByAssociation(Long associationId) {
        try {
            return depenseRepository.findDepenseDTOsByAssociationId(associationId);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des dépenses de l'association: " + e.getMessage());
        }
    }

    // Récupérer les dépenses par année scolaire
    public List<DepenseResponseDTO> getDepensesByAnneeScolaire(Long anneeScolaireId) {
        try {
            return depenseRepository.findDepenseDTOsByAnneeScolaireId(anneeScolaireId);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des dépenses par année scolaire: " + e.getMessage());
        }
    }

    // Récupérer les dépenses par association et année scolaire
    public List<DepenseResponseDTO> getDepensesByAssociationAndAnneeScolaire(Long associationId, Long anneeScolaireId) {
        try {
            List<Depense> depenses = depenseRepository.findByAssociationIdAndAnneeScolaireId(associationId, anneeScolaireId);
            return depenses.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des dépenses: " + e.getMessage());
        }
    }


    // Récupérer les dépenses par catégorie avec conversion DTO
    public List<DepenseResponseDTO> getDepensesByCategorie(CategorieDepense categorie) {
        try {
            List<Depense> depenses = depenseRepository.findByCategorie(categorie);
            return depenses.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des dépenses par catégorie: " + e.getMessage());
        }
    }

    // Récupérer les dépenses par association et catégorie avec DTO
    public List<DepenseResponseDTO> getDepensesByAssociationAndCategorie(Long associationId, CategorieDepense categorie) {
        try {
            List<Depense> depenses = depenseRepository.findByAssociationIdAndCategorie(associationId, categorie);
            return depenses.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des dépenses: " + e.getMessage());
        }
    }

    // Récupérer les dépenses par association, année scolaire et catégorie
    public List<DepenseResponseDTO> getDepensesByAssociationAndAnneeScolaireAndCategorie(
            Long associationId, Long anneeScolaireId, CategorieDepense categorie) {
        try {
            List<Depense> depenses = depenseRepository.findByAssociationIdAndAnneeScolaireIdAndCategorie(
                    associationId, anneeScolaireId, categorie);
            return depenses.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des dépenses: " + e.getMessage());
        }
    }

    // Récupérer les dépenses par période avec DTO
    public List<DepenseResponseDTO> getDepensesByPeriod(LocalDate startDate, LocalDate endDate) {
        try {
            List<Depense> depenses = depenseRepository.findByDateDepenseBetween(startDate, endDate);
            return depenses.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des dépenses par période: " + e.getMessage());
        }
    }

    // Calculer le total des dépenses d'une association
    public Double getTotalDepensesByAssociation(Long associationId) {
        try {
            Double total = depenseRepository.getTotalDepensesByAssociationId(associationId);
            return total != null ? total : 0.0;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul du total des dépenses: " + e.getMessage());
        }
    }

    // Calculer le total des dépenses d'une association et année scolaire
    public Double getTotalDepensesByAssociationAndAnneeScolaire(Long associationId, Long anneeScolaireId) {
        try {
            Double total = depenseRepository.getTotalDepensesByAssociationIdAndAnneeScolaireId(associationId, anneeScolaireId);
            return total != null ? total : 0.0;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul du total des dépenses: " + e.getMessage());
        }
    }

    // Calculer le total des dépenses d'une association sur une période
    public Double getTotalDepensesByAssociationAndPeriod(Long associationId, LocalDate startDate, LocalDate endDate) {
        try {
            Double total = depenseRepository.getTotalDepensesByAssociationIdAndPeriod(associationId, startDate, endDate);
            return total != null ? total : 0.0;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul du total des dépenses: " + e.getMessage());
        }
    }

    // Obtenir l'année scolaire active d'une association
    public AnneeScolaire getAnneeScolaireActiveByAssociation(Long associationId) {
        try {
            Optional<AnneeScolaire> anneeActive = anneeScolaireRepository.findAnneeScolaireActiveByAssociation(associationId);
            return anneeActive.orElseThrow(() -> new RuntimeException("Aucune année scolaire active trouvée pour cette association"));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de l'année scolaire active: " + e.getMessage());
        }
    }
}
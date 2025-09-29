package groupe_9_daimai.com.Daimai.Service;

import groupe_9_daimai.com.Daimai.DTO.AnneeScolaireRequestDTO;
import groupe_9_daimai.com.Daimai.DTO.AnneeScolaireResponseDTO;
import groupe_9_daimai.com.Daimai.Entite.AnneeScolaire;
import groupe_9_daimai.com.Daimai.Entite.Association;
import groupe_9_daimai.com.Daimai.Mapper.AnneeScolaireMapper;
import groupe_9_daimai.com.Daimai.Repository.AnneeScolaireRepository;
import groupe_9_daimai.com.Daimai.Repository.AssociationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnneeScolaireService {

    @Autowired
    private AnneeScolaireRepository anneeScolaireRepository;

    @Autowired
    private AssociationRepository associationRepository;

    @Autowired
    private AnneeScolaireMapper anneeScolaireMapper;

    public AnneeScolaireResponseDTO createAnneeScolaire(AnneeScolaireRequestDTO anneeScolaireRequestDTO) {
        // Valider le format du libellé
        validateLibelleFormat(anneeScolaireRequestDTO.getLibelle());

        // Vérifier l'existence de l'association
        Association association = associationRepository.findById(anneeScolaireRequestDTO.getAssociationId())
                .orElseThrow(() -> new AnneeScolaireNotFoundException("Association non trouvée avec l'ID: " + anneeScolaireRequestDTO.getAssociationId()));

        // Vérifier la cohérence des dates
        if (anneeScolaireRequestDTO.getDateDebut().isAfter(anneeScolaireRequestDTO.getDateFin())) {
            throw new AnneeScolaireValidationException("La date de début doit être antérieure à la date de fin");
        }

        // Vérifier l'unicité du libellé
        Optional<AnneeScolaire> existingAnnee = anneeScolaireRepository.findByLibelleAndAssociationId(
                anneeScolaireRequestDTO.getLibelle(), anneeScolaireRequestDTO.getAssociationId());
        if (existingAnnee.isPresent()) {
            throw new AnneeScolaireValidationException("Une année scolaire avec ce libellé existe déjà pour cette association");
        }

        // Vérifier les chevauchements
        if (anneeScolaireRepository.existsOverlappingPeriod(
                anneeScolaireRequestDTO.getAssociationId(),
                anneeScolaireRequestDTO.getDateDebut(),
                anneeScolaireRequestDTO.getDateFin(),
                0L)) {
            throw new AnneeScolaireValidationException("Cette période chevauche une année scolaire existante active");
        }

        // Convertir en entité
        AnneeScolaire anneeScolaire = anneeScolaireMapper.toEntity(anneeScolaireRequestDTO, association);

        // Désactiver les anciennes années
        desactiverAnciennesAnnees(association.getId(), anneeScolaire);

        // Sauvegarder
        AnneeScolaire savedAnneeScolaire = anneeScolaireRepository.save(anneeScolaire);
        return anneeScolaireMapper.toResponseDTO(savedAnneeScolaire);
    }

    @Transactional(readOnly = true)
    public List<AnneeScolaireResponseDTO> getAllAnneeScolaires() {
        List<AnneeScolaire> anneeScolaires = anneeScolaireRepository.findByActiveTrue();
        return anneeScolaires.stream()
                .map(anneeScolaireMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AnneeScolaireResponseDTO getAnneeScolaireById(Long id) {
        AnneeScolaire anneeScolaire = anneeScolaireRepository.findById(id)
                .orElseThrow(() -> new AnneeScolaireNotFoundException("Année scolaire non trouvée avec l'ID: " + id));
        return anneeScolaireMapper.toResponseDTO(anneeScolaire);
    }

    public AnneeScolaireResponseDTO updateAnneeScolaire(Long id, AnneeScolaireRequestDTO anneeScolaireRequestDTO) {
        // Vérifier l'existence
        AnneeScolaire anneeScolaire = anneeScolaireRepository.findById(id)
                .orElseThrow(() -> new AnneeScolaireNotFoundException("Année scolaire non trouvée avec l'ID: " + id));

        // Valider le format du libellé
        validateLibelleFormat(anneeScolaireRequestDTO.getLibelle());

        // Vérifier l'existence de l'association
        Association association = associationRepository.findById(anneeScolaireRequestDTO.getAssociationId())
                .orElseThrow(() -> new AnneeScolaireNotFoundException("Association non trouvée avec l'ID: " + anneeScolaireRequestDTO.getAssociationId()));

        // Vérifier la cohérence des dates
        if (anneeScolaireRequestDTO.getDateDebut().isAfter(anneeScolaireRequestDTO.getDateFin())) {
            throw new AnneeScolaireValidationException("La date de début doit être antérieure à la date de fin");
        }

        // Vérifier l'unicité du libellé
        Optional<AnneeScolaire> existingAnnee = anneeScolaireRepository.findByLibelleAndAssociationId(
                anneeScolaireRequestDTO.getLibelle(), anneeScolaireRequestDTO.getAssociationId());
        if (existingAnnee.isPresent() && !existingAnnee.get().getId().equals(id)) {
            throw new AnneeScolaireValidationException("Une année scolaire avec ce libellé existe déjà pour cette association");
        }

        // Vérifier les chevauchements
        if (anneeScolaireRepository.existsOverlappingPeriod(
                anneeScolaireRequestDTO.getAssociationId(),
                anneeScolaireRequestDTO.getDateDebut(),
                anneeScolaireRequestDTO.getDateFin(),
                id)) {
            throw new AnneeScolaireValidationException("Cette période chevauche une année scolaire existante active");
        }

        // Mettre à jour les champs
        anneeScolaire.setLibelle(anneeScolaireRequestDTO.getLibelle());
        anneeScolaire.setDateDebut(anneeScolaireRequestDTO.getDateDebut());
        anneeScolaire.setDateFin(anneeScolaireRequestDTO.getDateFin());
        anneeScolaire.setAssociation(association);

        AnneeScolaire updatedAnneeScolaire = anneeScolaireRepository.save(anneeScolaire);
        return anneeScolaireMapper.toResponseDTO(updatedAnneeScolaire);
    }

    public void desactiverAnneeScolaire(Long id) {
        AnneeScolaire anneeScolaire = anneeScolaireRepository.findById(id)
                .orElseThrow(() -> new AnneeScolaireNotFoundException("Année scolaire non trouvée avec l'ID: " + id));

        if (!anneeScolaire.getDepenses().isEmpty()) {
            throw new AnneeScolaireValidationException("Impossible de désactiver cette année scolaire : elle contient des dépenses");
        }

        anneeScolaire.setActive(false);
        anneeScolaireRepository.save(anneeScolaire);
    }

    public AnneeScolaireResponseDTO reactiverAnneeScolaire(Long id) {
        AnneeScolaire anneeScolaire = anneeScolaireRepository.findById(id)
                .orElseThrow(() -> new AnneeScolaireNotFoundException("Année scolaire non trouvée avec l'ID: " + id));

        // Vérifier les chevauchements lors de la réactivation
        if (anneeScolaireRepository.existsOverlappingPeriod(
                anneeScolaire.getAssociation().getId(),
                anneeScolaire.getDateDebut(),
                anneeScolaire.getDateFin(),
                id)) {
            throw new AnneeScolaireValidationException("Impossible de réactiver : cette période chevauche une autre année scolaire active");
        }

        anneeScolaire.setActive(true);
        AnneeScolaire anneeReactived = anneeScolaireRepository.save(anneeScolaire);
        return anneeScolaireMapper.toResponseDTO(anneeReactived);
    }

    @Transactional(readOnly = true)
    public List<AnneeScolaireResponseDTO> getAnneeScolairesByAssociation(Long associationId) {
        List<AnneeScolaire> anneeScolaires = anneeScolaireRepository.findByAssociationIdAndActiveTrue(associationId);
        return anneeScolaires.stream()
                .map(anneeScolaireMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AnneeScolaireResponseDTO getAnneeScolaireActiveByAssociation(Long associationId) {
        AnneeScolaire anneeActive = anneeScolaireRepository.findAnneeScolaireActiveByAssociation(associationId)
                .orElseThrow(() -> new AnneeScolaireNotFoundException("Aucune année scolaire active trouvée pour cette association"));
        return anneeScolaireMapper.toResponseDTO(anneeActive);
    }

    @Transactional(readOnly = true)
    public boolean isAnneeScolaireActive(Long id) {
        AnneeScolaire anneeScolaire = anneeScolaireRepository.findById(id)
                .orElseThrow(() -> new AnneeScolaireNotFoundException("Année scolaire non trouvée avec l'ID: " + id));
        if (!anneeScolaire.isActive()) {
            return false;
        }
        LocalDate now = LocalDate.now();
        return !now.isBefore(anneeScolaire.getDateDebut()) && !now.isAfter(anneeScolaire.getDateFin());
    }

    @Transactional(readOnly = true)
    public List<AnneeScolaireResponseDTO> getAnneeScolairesByPeriod(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new AnneeScolaireValidationException("La date de début doit être antérieure à la date de fin");
        }
        List<AnneeScolaire> anneeScolaires = anneeScolaireRepository.findByDateDebutBetweenAndActiveTrue(startDate, endDate);
        return anneeScolaires.stream()
                .map(anneeScolaireMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean associationHasAnneeScolaires(Long associationId) {
        return anneeScolaireRepository.existsByAssociationIdAndActiveTrue(associationId);
    }

    @Transactional(readOnly = true)
    public long countAnneeScolairesByAssociation(Long associationId) {
        return anneeScolaireRepository.countByAssociationIdAndActiveTrue(associationId);
    }

    @Transactional(readOnly = true)
    public List<AnneeScolaireResponseDTO> getAllAnneeScolairesWithInactive() {
        List<AnneeScolaire> anneeScolaires = anneeScolaireRepository.findAll();
        return anneeScolaires.stream()
                .map(anneeScolaireMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AnneeScolaireResponseDTO> getAnneeScolairesInactivesByAssociation(Long associationId) {
        List<AnneeScolaire> anneesInactives = anneeScolaireRepository.findByAssociationIdAndActiveFalse(associationId);
        return anneesInactives.stream()
                .map(anneeScolaireMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    private void desactiverAnciennesAnnees(Long associationId, AnneeScolaire nouvelleAnnee) {
        List<AnneeScolaire> anneesActives = anneeScolaireRepository.findByAssociationIdAndActiveTrue(associationId);
        Integer nouvelleAnneeValue = extractAnneeFromLibelle(nouvelleAnnee.getLibelle());

        for (AnneeScolaire anneeExistante : anneesActives) {
            Integer anneeExistanteValue = extractAnneeFromLibelle(anneeExistante.getLibelle());
            if (anneeExistanteValue != null && nouvelleAnneeValue != null) {
                if (nouvelleAnneeValue > anneeExistanteValue) {
                    anneeExistante.setActive(false);
                    anneeScolaireRepository.save(anneeExistante);
                }
            } else {
                anneeExistante.setActive(false);
                anneeScolaireRepository.save(anneeExistante);
            }
        }
    }

    private Integer extractAnneeFromLibelle(String libelle) {
        if (libelle == null) return null;
        try {
            // Accepte les formats comme "2023-2024"
            Pattern pattern = Pattern.compile("^(\\d{4}).*");
            java.util.regex.Matcher matcher = pattern.matcher(libelle);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
        } catch (Exception e) {
            // En cas d'erreur de parsing
        }
        return null;
    }

    private void validateLibelleFormat(String libelle) {
        if (libelle == null || !libelle.matches("^\\d{4}-\\d{4}$")) {
            throw new AnneeScolaireValidationException("Le libellé doit être au format AAAA-AAAA (ex: 2023-2024)");
        }
    }

    // Exceptions personnalisées
    public static class AnneeScolaireNotFoundException extends RuntimeException {
        public AnneeScolaireNotFoundException(String message) {
            super(message);
        }
    }

    public static class AnneeScolaireValidationException extends RuntimeException {
        public AnneeScolaireValidationException(String message) {
            super(message);
        }
    }
}
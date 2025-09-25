package groupe_9_daimai.com.Daimai.Repository;

import groupe_9_daimai.com.Daimai.Entite.AnneeScolaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnneeScolaireRepository extends JpaRepository<AnneeScolaire, Long> {

    List<AnneeScolaire> findByAssociationId(Long associationId);

    List<AnneeScolaire> findByAssociationIdAndActiveTrue(Long associationId);

    List<AnneeScolaire> findByAssociationIdAndActiveFalse(Long associationId);

    Optional<AnneeScolaire> findByLibelleAndAssociationId(String libelle, Long associationId);

    @Query("SELECT a FROM AnneeScolaire a WHERE a.association.id = :associationId " +
            "AND CURRENT_DATE BETWEEN a.dateDebut AND a.dateFin AND a.active = true")
    Optional<AnneeScolaire> findAnneeScolaireActiveByAssociation(@Param("associationId") Long associationId);

    @Query("SELECT COUNT(a) > 0 FROM AnneeScolaire a WHERE a.association.id = :associationId " +
            "AND a.id != :excludeId " +
            "AND a.active = true " +
            "AND ((a.dateDebut BETWEEN :dateDebut AND :dateFin) OR " +
            "(a.dateFin BETWEEN :dateDebut AND :dateFin) OR " +
            "(:dateDebut BETWEEN a.dateDebut AND a.dateFin) OR " +
            "(:dateFin BETWEEN a.dateDebut AND a.dateFin))")
    boolean existsOverlappingPeriod(
            @Param("associationId") Long associationId,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin,
            @Param("excludeId") Long excludeId);

    List<AnneeScolaire> findByDateDebutBetweenAndActiveTrue(LocalDate start, LocalDate end);

    boolean existsByAssociationIdAndActiveTrue(Long associationId);

    long countByAssociationIdAndActiveTrue(Long associationId);
}
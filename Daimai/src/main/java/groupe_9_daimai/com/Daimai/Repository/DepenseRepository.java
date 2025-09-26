package groupe_9_daimai.com.Daimai.Repository;

import groupe_9_daimai.com.Daimai.DTO.DepenseResponseDTO;
import groupe_9_daimai.com.Daimai.Entite.Depense;
import groupe_9_daimai.com.Daimai.Entite.enums.CategorieDepense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DepenseRepository extends JpaRepository<Depense, Long> {

    // Trouver les dépenses par association
    List<Depense> findByAssociationId(Long associationId);

    // Trouver les dépenses par année scolaire
    List<Depense> findByAnneeScolaireId(Long anneeScolaireId);

    // Trouver les dépenses par association et année scolaire
    List<Depense> findByAssociationIdAndAnneeScolaireId(Long associationId, Long anneeScolaireId);

    // Trouver les dépenses par catégorie
    List<Depense> findByCategorie(CategorieDepense categorie);

    // Trouver les dépenses par association et catégorie
    List<Depense> findByAssociationIdAndCategorie(Long associationId, CategorieDepense categorie);

    // Trouver les dépenses par association, année scolaire et catégorie
    List<Depense> findByAssociationIdAndAnneeScolaireIdAndCategorie(Long associationId, Long anneeScolaireId, CategorieDepense categorie);

    // Trouver les dépenses entre deux dates
    List<Depense> findByDateDepenseBetween(LocalDate startDate, LocalDate endDate);

    // Trouver les dépenses d'une association entre deux dates
    List<Depense> findByAssociationIdAndDateDepenseBetween(Long associationId, LocalDate startDate, LocalDate endDate);

    // Trouver les dépenses d'une année scolaire entre deux dates
    List<Depense> findByAnneeScolaireIdAndDateDepenseBetween(Long anneeScolaireId, LocalDate startDate, LocalDate endDate);

    // Calculer le total des dépenses par association
    @Query("SELECT SUM(d.montant) FROM Depense d WHERE d.association.id = :associationId")
    Double getTotalDepensesByAssociationId(@Param("associationId") Long associationId);

    // Calculer le total des dépenses par association et année scolaire
    @Query("SELECT SUM(d.montant) FROM Depense d WHERE d.association.id = :associationId AND d.anneeScolaire.id = :anneeScolaireId")
    Double getTotalDepensesByAssociationIdAndAnneeScolaireId(@Param("associationId") Long associationId,
                                                             @Param("anneeScolaireId") Long anneeScolaireId);

    // Calculer le total des dépenses par association et période
    @Query("SELECT SUM(d.montant) FROM Depense d WHERE d.association.id = :associationId AND d.dateDepense BETWEEN :startDate AND :endDate")
    Double getTotalDepensesByAssociationIdAndPeriod(@Param("associationId") Long associationId,
                                                    @Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate);

    // Projection DTO avec année scolaire
    @Query("SELECT new groupe_9_daimai.com.Daimai.DTO.DepenseResponseDTO(" +
            "d.id, d.montant, d.categorie, d.dateDepense, d.association.id, d.association.nom, " +
            "d.anneeScolaire.id, d.anneeScolaire.libelle, " +
            "CASE WHEN CURRENT_DATE BETWEEN d.anneeScolaire.dateDebut AND d.anneeScolaire.dateFin THEN true ELSE false END) " +
            "FROM Depense d WHERE d.id = :id")
    DepenseResponseDTO findDepenseDTOById(@Param("id") Long id);

    @Query("SELECT new groupe_9_daimai.com.Daimai.DTO.DepenseResponseDTO(" +
            "d.id, d.montant, d.categorie, d.dateDepense, d.association.id, d.association.nom, " +
            "d.anneeScolaire.id, d.anneeScolaire.libelle, " +
            "CASE WHEN CURRENT_DATE BETWEEN d.anneeScolaire.dateDebut AND d.anneeScolaire.dateFin THEN true ELSE false END) " +
            "FROM Depense d")
    List<DepenseResponseDTO> findAllDepenseDTOs();

    @Query("SELECT new groupe_9_daimai.com.Daimai.DTO.DepenseResponseDTO(" +
            "d.id, d.montant, d.categorie, d.dateDepense, d.association.id, d.association.nom, " +
            "d.anneeScolaire.id, d.anneeScolaire.libelle, " +
            "CASE WHEN CURRENT_DATE BETWEEN d.anneeScolaire.dateDebut AND d.anneeScolaire.dateFin THEN true ELSE false END) " +
            "FROM Depense d WHERE d.association.id = :associationId")
    List<DepenseResponseDTO> findDepenseDTOsByAssociationId(@Param("associationId") Long associationId);

    @Query("SELECT new groupe_9_daimai.com.Daimai.DTO.DepenseResponseDTO(" +
            "d.id, d.montant, d.categorie, d.dateDepense, d.association.id, d.association.nom, " +
            "d.anneeScolaire.id, d.anneeScolaire.libelle, " +
            "CASE WHEN CURRENT_DATE BETWEEN d.anneeScolaire.dateDebut AND d.anneeScolaire.dateFin THEN true ELSE false END) " +
            "FROM Depense d WHERE d.anneeScolaire.id = :anneeScolaireId")
    List<DepenseResponseDTO> findDepenseDTOsByAnneeScolaireId(@Param("anneeScolaireId") Long anneeScolaireId);

    // Dépenses visibles par les parrains (année active + association de l'enfant parrainé)
    @Query("SELECT new groupe_9_daimai.com.Daimai.DTO.DepenseResponseDTO(" +
            "d.id, d.montant, d.categorie, d.dateDepense, d.association.id, d.association.nom, " +
            "d.anneeScolaire.id, d.anneeScolaire.libelle, true) " +
            "FROM Depense d WHERE d.association.id = :associationId " +
            "AND d.anneeScolaire.id IN (" +
            "   SELECT ascol.id FROM AnneeScolaire ascol " +
            "   WHERE CURRENT_DATE BETWEEN ascol.dateDebut AND ascol.dateFin" +
            ")")
    List<DepenseResponseDTO> findDepensesVisiblesParParrains(@Param("associationId") Long associationId);
}
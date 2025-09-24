package groupe_9_daimai.com.Daimai.Repository;

import groupe_9_daimai.com.Daimai.Entite.Enfant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnfantRepository extends JpaRepository<Enfant, Long> {

    Optional<Enfant> findByTelephone(String telephone);
    Optional<Enfant> findByEmail(String email);
    List<Enfant> findByAssociationId(Long associationId);
    List<Enfant> findByStatutAbandon(Boolean statutAbandon);

    @Query("SELECT e FROM Enfant e WHERE e.association.id = :associationId AND e.statutAbandon = false")
    List<Enfant> findEnfantsActifsByAssociation(@Param("associationId") Long associationId);

    @Query("SELECT e, p FROM Enfant e JOIN e.parrains p WHERE e.association.id = :associationId")
    List<Object[]> findParrainagesByAssociationId(@Param("associationId") Long associationId);

    @Query("SELECT COUNT(e) FROM Enfant e WHERE e.association.id = :associationId")
    Integer countByAssociationId(@Param("associationId") Long associationId);

    @Query("SELECT COUNT(e) FROM Enfant e WHERE e.association.id = :associationId AND e.statutAbandon = false")
    Integer countEnfantsActifsByAssociationId(@Param("associationId") Long associationId);

    @Query("SELECT e FROM Enfant e WHERE LOWER(e.nom) LIKE LOWER(CONCAT('%', :recherche, '%')) OR LOWER(e.prenom) LIKE LOWER(CONCAT('%', :recherche, '%'))")
    List<Enfant> findByNomOrPrenomContaining(@Param("recherche") String recherche);
}
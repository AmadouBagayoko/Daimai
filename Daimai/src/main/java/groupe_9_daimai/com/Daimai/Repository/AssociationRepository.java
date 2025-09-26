package groupe_9_daimai.com.Daimai.Repository;

import groupe_9_daimai.com.Daimai.Entite.Association;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssociationRepository extends JpaRepository<Association, Long> {

    Optional<Association> findByTelephone(String telephone);

    Optional<Association> findByEmail(String email);

    List<Association> findByDomaine(String domaine);

    List<Association> findByStatutBloquer(Boolean statutBloquer);

    // Méthode corrigée avec la convention JPA
    List<Association> findByAdministrateur_Id(Long id);

    @Query("SELECT COUNT(e) FROM Enfant e WHERE e.association.id = :associationId")
    Integer countEnfantsByAssociationId(@Param("associationId") Long associationId);

    @Query("SELECT COUNT(DISTINCT p) FROM Parrain p JOIN p.enfants e WHERE e.association.id = :associationId")
    Integer countParrainsByAssociationId(@Param("associationId") Long associationId);

    @Query("SELECT a FROM Association a WHERE a.nom LIKE %:recherche% OR a.domaine LIKE %:recherche%")
    List<Association> findByNomOrDomaineContaining(@Param("recherche") String recherche);
}

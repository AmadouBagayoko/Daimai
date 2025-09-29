package groupe_9_daimai.com.Daimai.Repository;

import groupe_9_daimai.com.Daimai.Entite.Recu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecuRepository extends JpaRepository<Recu, Long> {

    // Trouver les reçus par paiement
    Optional<Recu> findByPaiementId(Long paiementId);

    // Trouver les reçus par parrain
    @Query("SELECT r FROM Recu r WHERE r.paiement.parrain.id = :parrainId")
    List<Recu> findByParrainId(@Param("parrainId") Long parrainId);

    // Trouver les reçus par période
    List<Recu> findByDateBetween(LocalDate startDate, LocalDate endDate);

    // Trouver les reçus d'un parrain par période
    @Query("SELECT r FROM Recu r WHERE r.paiement.parrain.id = :parrainId AND r.date BETWEEN :startDate AND :endDate")
    List<Recu> findByParrainIdAndDateBetween(@Param("parrainId") Long parrainId,
                                             @Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);

    // Vérifier si un reçu existe pour un paiement
    boolean existsByPaiementId(Long paiementId);

    // Trouver par code de reçu
    Optional<Recu> findByCodeRecu(String codeRecu);

    // Calculer le total des reçus par parrain
    @Query("SELECT SUM(r.montantPayer) FROM Recu r WHERE r.paiement.parrain.id = :parrainId")
    Double getTotalMontantByParrainId(@Param("parrainId") Long parrainId);
}
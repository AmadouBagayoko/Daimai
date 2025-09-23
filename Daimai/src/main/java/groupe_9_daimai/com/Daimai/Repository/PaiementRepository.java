package groupe_9_daimai.com.Daimai.Repository;

import groupe_9_daimai.com.Daimai.Entite.Paiement;
import groupe_9_daimai.com.Daimai.Entite.enums.ModePaiement;
import groupe_9_daimai.com.Daimai.Entite.enums.StatutPaiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    List<Paiement> findByParrainId(Long parrainId);
    List<Paiement> findByModePaiement(ModePaiement modePaiement);
    List<Paiement> findByStatutPaiement(StatutPaiement statutPaiement);
    List<Paiement> findByDatePaiementBetween(LocalDate startDate, LocalDate endDate);
    List<Paiement> findByParrainIdAndDatePaiementBetween(Long parrainId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(p.montant) FROM Paiement p WHERE p.parrain.id = :parrainId AND p.statutPaiement = 'CONFIRME'")
    Double getTotalPaiementsConfirmesByParrain(@Param("parrainId") Long parrainId);

    @Query("SELECT SUM(p.montant) FROM Paiement p WHERE p.statutPaiement = 'CONFIRME'")
    Double getTotalPaiementsConfirmes();

    @Query("SELECT new groupe_9_daimai.com.Daimai.DTO.PaiementResponseDTO(p.id, p.montant, p.datePaiement, p.modePaiement, p.statutPaiement, p.parrain.id, p.parrain.nom, p.parrain.prenom) FROM Paiement p WHERE p.id = :id")
    PaiementResponseDTO findPaiementDTOById(@Param("id") Long id);

    @Query("SELECT new groupe_9_daimai.com.Daimai.DTO.PaiementResponseDTO(p.id, p.montant, p.datePaiement, p.modePaiement, p.statutPaiement, p.parrain.id, p.parrain.nom, p.parrain.prenom) FROM Paiement p")
    List<PaiementResponseDTO> findAllPaiementDTOs();
}
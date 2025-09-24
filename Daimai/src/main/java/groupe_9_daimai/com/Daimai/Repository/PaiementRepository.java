package groupe_9_daimai.com.Daimai.Repository;

import groupe_9_daimai.com.Daimai.Entite.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaiementRepository extends JpaRepository<Paiement,Long> {

    Optional<Paiement> findPaiementByTransactionId(long transactionId);

}

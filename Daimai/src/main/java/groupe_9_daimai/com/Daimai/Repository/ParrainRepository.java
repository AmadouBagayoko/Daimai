package groupe_9_daimai.com.Daimai.Repository;

import groupe_9_daimai.com.Daimai.Entite.Parrain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParrainRepository extends JpaRepository<Parrain, Long> {


    Optional<Parrain> findByTelephone(String telephone);
    boolean existsByEmail(String email);
    boolean existsByTelephone(String telephone);
}

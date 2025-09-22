package groupe_9_daimai.com.Daimai.Repository;

import groupe_9_daimai.com.Daimai.Entite.Parrain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParrainageRepository extends JpaRepository<Parrain, Long> {
    Optional<Parrain> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByTelephone(String telephone);
    Optional<Parrain> findByTelephone( String telephone);
}

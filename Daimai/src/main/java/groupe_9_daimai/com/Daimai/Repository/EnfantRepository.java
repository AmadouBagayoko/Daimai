package groupe_9_daimai.com.Daimai.Repository;

import groupe_9_daimai.com.Daimai.Entite.Enfant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnfantRepository extends JpaRepository<Enfant,Long> {

    Optional<Enfant> findByTelephone(String telephone);
    boolean existsByEmail(String email);
    boolean existsByTelephone(String telephone);

}

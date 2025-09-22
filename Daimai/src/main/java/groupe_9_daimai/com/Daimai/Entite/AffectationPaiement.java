package groupe_9_daimai.com.Daimai.Entite;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class AffectationPaiement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne
    private Paiement paiement;

    @ManyToOne
    private Depense depense;




}

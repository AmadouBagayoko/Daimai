package groupe_9_daimai.com.Daimai.Entite;

import groupe_9_daimai.com.Daimai.Entite.enums.ModePaiement;
import groupe_9_daimai.com.Daimai.Entite.enums.StatutPaiement;
import groupe_9_daimai.com.Daimai.Entite.enums.TypeNotifcation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Paiement")
@Getter
@Setter
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double montant;

    @Column
    private LocalDate DatePaiement;

    @Column
    private ModePaiement modePaiement;

    @Column
    private StatutPaiement statutPaiement;


    @OneToMany(mappedBy = "paiement")
    private List<AffectationPaiement> affectationPaiements = new ArrayList<>();

    @OneToOne(mappedBy = "paiement")
    private Recu recu;


}

package groupe_9_daimai.com.Daimai.Entite;

import groupe_9_daimai.com.Daimai.Entite.enums.CategorieDepense;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Depense")
@Getter
@Setter
public class Depense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double montant;

    @Column
    private CategorieDepense Categorie;

    @Column
    private LocalDate dateDepense;

    @OneToMany(mappedBy = "depense")
    private List<AffectationPaiement> affectationPaiements = new ArrayList<>();

    @ManyToOne
    @JoinColumn(
            name = "association_id"
    )
    private Association association ;

    @ManyToOne
    @JoinColumn(name = "annee_scolaire_id")
    private AnneeScolaire anneeScolaire;

}

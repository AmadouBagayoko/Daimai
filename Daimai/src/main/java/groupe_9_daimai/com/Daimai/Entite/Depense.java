package groupe_9_daimai.com.Daimai.Entite;

import groupe_9_daimai.com.Daimai.Entite.enums.CategorieDepense;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Depense")
@Getter
@Setter
public class Depense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private Double montant;

    @Column
    private CategorieDepense Categorie;

    @Column
    private LocalDate dateDepense;
}

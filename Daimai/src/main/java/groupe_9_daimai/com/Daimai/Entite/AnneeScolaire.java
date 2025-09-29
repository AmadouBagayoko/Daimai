package groupe_9_daimai.com.Daimai.Entite;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class AnneeScolaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "association_id")
    private Association association;

    @OneToMany(mappedBy = "anneeScolaire")
    private List<Depense> depenses;
}
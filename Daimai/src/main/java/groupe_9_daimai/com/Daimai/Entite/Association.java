package groupe_9_daimai.com.Daimai.Entite;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Association")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Association {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nom;

    @Column
    private String email;

    @Column
    private String adresse;

    @Column
    private String domaine;

    @Column
    private String motDepasse;

    @Column
    private String autorisation;

    @Column
    private String telephone;

    @Column
    private String photo;

    @Column
    private Boolean statutBloquer;

    @OneToMany(mappedBy = "association")
    private List<Enfant> enfants;

    @OneToMany(mappedBy = "association")
    private List<Notification> notifications;

    @OneToMany(mappedBy = "association")
    private List<Depense> depenses;

    @ManyToOne
    @JoinColumn(name = "administrateur_id")
    private Administrateur administrateur;
}



package groupe_9_daimai.com.Daimai.Entite;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Administrateur")
@Getter
@Setter
public class Administrateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nom;

    @Column
    private String prenom;

    @Column(unique = true)
    private String email;

    @Column
    private String motDepasse;
}

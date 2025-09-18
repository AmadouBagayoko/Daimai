package groupe_9_daimai.com.Daimai.Entite;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Enfant")
@Getter
@Setter
public class Enfant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nom;

    @Column
    private String prenom;

    @Column
    private LocalDate dateNaissance;

    @Column
    private String niveauScolaire;

    @Column
    private String urlPhoto;

    @Column
    private String tuteur;

    @Column
    private int telephone;

    @Column(unique = true)
    private String email;

    @Column
    private Boolean statutAbandon;

}

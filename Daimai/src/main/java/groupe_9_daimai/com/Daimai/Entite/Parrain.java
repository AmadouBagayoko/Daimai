package groupe_9_daimai.com.Daimai.Entite;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Parrain")
@Getter
@Setter
public class Parrain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nom;

    @Column
    private String prenom;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private int telephone;

    @Column
    private String motDepasse;

    @Column
    private Boolean roleUtilisateur;

    @Column
    private String profession;

    @Column
    private String adresse;

    @Column
    private LocalDate dateCreation;

    @Column
    private  Boolean statut;

}

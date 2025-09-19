package groupe_9_daimai.com.Daimai.Entite;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Association")
@Getter
@Setter
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
    private int telephone;

    @Column
    private String photo;

    @Column
    private Boolean statutBloquer;

}

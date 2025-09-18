package groupe_9_daimai.com.Daimai.Entite;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "RapportScolaire")
@Getter
@Setter
public class RapportScolaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String moyenne;

    @Column
    private String presence;

    @Column
    private String bulletin;

    @Column
    private Date dateDebut;

    @Column
    private Date dateFin;

}

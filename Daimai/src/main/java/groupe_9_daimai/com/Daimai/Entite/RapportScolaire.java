package groupe_9_daimai.com.Daimai.Entite;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "RapportScolaire")
public class RapportScolaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @ManyToOne
    @JoinColumn(name = "enfant_id")
    private Enfant enfant;

    public RapportScolaire() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(String moyenne) {
        this.moyenne = moyenne;
    }

    public String getPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }

    public String getBulletin() {
        return bulletin;
    }

    public void setBulletin(String bulletin) {
        this.bulletin = bulletin;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }


    public Enfant getEnfant() {
        return enfant;
    }

    public void setEnfant(Enfant enfant) {
        this.enfant = enfant;
    }

    public RapportScolaire(Long id, String moyenne, String presence, String bulletin, Date dateDebut, Date dateFin, Enfant enfant) {
        this.id = id;
        this.moyenne = moyenne;
        this.presence = presence;
        this.bulletin = bulletin;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.enfant = enfant;
    }

    @Override
    public String toString() {
        return "RapportScolaire{" +
                "id=" + id +
                ", moyenne='" + moyenne + '\'' +
                ", presence='" + presence + '\'' +
                ", bulletin='" + bulletin + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", enfant=" + enfant +
                '}';
    }
}

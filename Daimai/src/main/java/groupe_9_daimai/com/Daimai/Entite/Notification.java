package groupe_9_daimai.com.Daimai.Entite;

import groupe_9_daimai.com.Daimai.Entite.enums.TypeNotifcation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeNotifcation typeNotifcation;

    @Column(nullable = false)
    private String contenue;

    @Column(nullable = false)
    private String Envoyeur;

    @Column(nullable = false)
    private String Recepteur;

    @Column(nullable = false)
    private LocalDate dateEnvoi;

    @ManyToOne
    @JoinColumn(
            name = "association_id"
    )
    private Association association;

    public Notification() {}


    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", typeNotifcation=" + typeNotifcation +
                ", contenue='" + contenue + '\'' +
                ", Envoyeur='" + Envoyeur + '\'' +
                ", Recepteur='" + Recepteur + '\'' +
                ", dateEnvoi=" + dateEnvoi +
                ", association=" + association +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeNotifcation getTypeNotifcation() {
        return typeNotifcation;
    }

    public void setTypeNotifcation(TypeNotifcation typeNotifcation) {
        this.typeNotifcation = typeNotifcation;
    }

    public String getContenue() {
        return contenue;
    }

    public void setContenue(String contenue) {
        this.contenue = contenue;
    }

    public String getEnvoyeur() {
        return Envoyeur;
    }

    public void setEnvoyeur(String envoyeur) {
        Envoyeur = envoyeur;
    }

    public String getRecepteur() {
        return Recepteur;
    }

    public void setRecepteur(String recepteur) {
        Recepteur = recepteur;
    }

    public LocalDate getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(LocalDate dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public void setAssociation(Association association) {
        this.association = association;
    }


    public Notification(Long id, TypeNotifcation typeNotifcation, String contenue, String envoyeur, String recepteur, LocalDate dateEnvoi, Association association) {
        this.id = id;
        this.typeNotifcation = typeNotifcation;
        this.contenue = contenue;
        Envoyeur = envoyeur;
        Recepteur = recepteur;
        this.dateEnvoi = dateEnvoi;
        this.association = association;
    }
}

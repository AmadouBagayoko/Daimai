package groupe_9_daimai.com.Daimai.Entite;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Recu")
@Getter @Setter @ToString @NoArgsConstructor
public class Recu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double montantPayer;

    @Column
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "paiement_id")
    private Paiement paiement;



    @Override
    public String toString() {
        return "Recu{" +
                "id=" + id +
                ", montantPayer=" + montantPayer +
                ", date=" + date +
                ", paiement=" + paiement +
                '}';
    }

    public Recu(Paiement paiement, LocalDate date, double montantPayer, Long id) {
        this.paiement = paiement;
        this.date = date;
        this.montantPayer = montantPayer;
        this.id = id;
    }

    public Recu(Long id, double montantPayer, LocalDate date, Paiement paiement) {
        this.id = id;
        this.montantPayer = montantPayer;
        this.date = date;
        this.paiement = paiement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getMontantPayer() {
        return montantPayer;
    }

    public void setMontantPayer(double montantPayer) {
        this.montantPayer = montantPayer;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }
}

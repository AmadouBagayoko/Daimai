package groupe_9_daimai.com.Daimai.Entite;

import groupe_9_daimai.com.Daimai.Entite.enums.ModePaiement;
import groupe_9_daimai.com.Daimai.Entite.enums.StatutPaiement;
import groupe_9_daimai.com.Daimai.Entite.enums.TypeNotifcation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Paiement")
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double montant;

    @Column
    private LocalDate DatePaiement;

    @Enumerated(EnumType.STRING)
    private ModePaiement modePaiement;

    @Enumerated(EnumType.STRING)
    private StatutPaiement statutPaiement;

    @Column
    private String transactionId;

    @Column
    private String clientReference;


    @OneToMany(mappedBy = "paiement")
    private List<AffectationPaiement> affectationPaiements = new ArrayList<>();

    @OneToOne(mappedBy = "paiement")
    private Recu recu;

    public Paiement() {}

    @Override
    public String toString() {
        return "Paiement{" +
                "id=" + id +
                ", montant=" + montant +
                ", DatePaiement=" + DatePaiement +
                ", modePaiement=" + modePaiement +
                ", statutPaiement=" + statutPaiement +
                ", transactionId='" + transactionId + '\'' +
                ", clientReference='" + clientReference + '\'' +
                ", affectationPaiements=" + affectationPaiements +
                ", recu=" + recu +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDate getDatePaiement() {
        return DatePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        DatePaiement = datePaiement;
    }

    public ModePaiement getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(ModePaiement modePaiement) {
        this.modePaiement = modePaiement;
    }

    public StatutPaiement getStatutPaiement() {
        return statutPaiement;
    }

    public void setStatutPaiement(StatutPaiement statutPaiement) {
        this.statutPaiement = statutPaiement;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getClientReference() {
        return clientReference;
    }

    public void setClientReference(String clientReference) {
        this.clientReference = clientReference;
    }

    public List<AffectationPaiement> getAffectationPaiements() {
        return affectationPaiements;
    }

    public void setAffectationPaiements(List<AffectationPaiement> affectationPaiements) {
        this.affectationPaiements = affectationPaiements;
    }

    public Recu getRecu() {
        return recu;
    }

    public void setRecu(Recu recu) {
        this.recu = recu;
    }

    public Paiement(Long id, double montant, LocalDate datePaiement, ModePaiement modePaiement, StatutPaiement statutPaiement, String transactionId, String clientReference, List<AffectationPaiement> affectationPaiements, Recu recu) {
        this.id = id;
        this.montant = montant;
        DatePaiement = datePaiement;
        this.modePaiement = modePaiement;
        this.statutPaiement = statutPaiement;
        this.transactionId = transactionId;
        this.clientReference = clientReference;
        this.affectationPaiements = affectationPaiements;
        this.recu = recu;
    }
}

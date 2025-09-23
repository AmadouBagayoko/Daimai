package groupe_9_daimai.com.Daimai.DTO;

import groupe_9_daimai.com.Daimai.Entite.enums.ModePaiement;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class PaiementRequestDTO {

    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être positif")
    private Double montant;

    @NotNull(message = "Le mode de paiement est obligatoire")
    private ModePaiement modePaiement;

    private LocalDate datePaiement;

    @NotNull(message = "L'ID du parrain est obligatoire")
    private Long parrainId;

    // Champs spécifiques aux différents modes de paiement
    private String numeroTelephone; // Pour Mobile Money
    private String emailPaypal;     // Pour PayPal
    private String numeroCarte;     // Pour carte bancaire
    private String codeTransaction; // Pour paiement en espèces

    // Getters et Setters
    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }

    public ModePaiement getModePaiement() { return modePaiement; }
    public void setModePaiement(ModePaiement modePaiement) { this.modePaiement = modePaiement; }

    public LocalDate getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDate datePaiement) { this.datePaiement = datePaiement; }

    public Long getParrainId() { return parrainId; }
    public void setParrainId(Long parrainId) { this.parrainId = parrainId; }

    public String getNumeroTelephone() { return numeroTelephone; }
    public void setNumeroTelephone(String numeroTelephone) { this.numeroTelephone = numeroTelephone; }

    public String getEmailPaypal() { return emailPaypal; }
    public void setEmailPaypal(String emailPaypal) { this.emailPaypal = emailPaypal; }

    public String getNumeroCarte() { return numeroCarte; }
    public void setNumeroCarte(String numeroCarte) { this.numeroCarte = numeroCarte; }

    public String getCodeTransaction() { return codeTransaction; }
    public void setCodeTransaction(String codeTransaction) { this.codeTransaction = codeTransaction; }
}
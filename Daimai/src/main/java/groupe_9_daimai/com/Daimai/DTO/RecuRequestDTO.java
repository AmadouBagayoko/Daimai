package groupe_9_daimai.com.Daimai.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class RecuRequestDTO {

    @NotNull(message = "Le montant payé est obligatoire")
    @Positive(message = "Le montant doit être positif")
    private Double montantPayer;

    private LocalDate date;

    @NotNull(message = "L'ID du paiement est obligatoire")
    private Long paiementId;

    // Getters et Setters
    public Double getMontantPayer() { return montantPayer; }
    public void setMontantPayer(Double montantPayer) { this.montantPayer = montantPayer; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Long getPaiementId() { return paiementId; }
    public void setPaiementId(Long paiementId) { this.paiementId = paiementId; }
}
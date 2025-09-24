package groupe_9_daimai.com.Daimai.DTO;

import java.time.LocalDate;

public class RecuResponseDTO {
    private Long id;
    private Double montantPayer;
    private LocalDate date;
    private Long paiementId;
    private String codeRecu;
    private String parrainNom;
    private String parrainPrenom;
    private String parrainEmail;
    private String modePaiement;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getMontantPayer() { return montantPayer; }
    public void setMontantPayer(Double montantPayer) { this.montantPayer = montantPayer; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Long getPaiementId() { return paiementId; }
    public void setPaiementId(Long paiementId) { this.paiementId = paiementId; }

    public String getCodeRecu() { return codeRecu; }
    public void setCodeRecu(String codeRecu) { this.codeRecu = codeRecu; }

    public String getParrainNom() { return parrainNom; }
    public void setParrainNom(String parrainNom) { this.parrainNom = parrainNom; }

    public String getParrainPrenom() { return parrainPrenom; }
    public void setParrainPrenom(String parrainPrenom) { this.parrainPrenom = parrainPrenom; }

    public String getParrainEmail() { return parrainEmail; }
    public void setParrainEmail(String parrainEmail) { this.parrainEmail = parrainEmail; }

    public String getModePaiement() { return modePaiement; }
    public void setModePaiement(String modePaiement) { this.modePaiement = modePaiement; }
}
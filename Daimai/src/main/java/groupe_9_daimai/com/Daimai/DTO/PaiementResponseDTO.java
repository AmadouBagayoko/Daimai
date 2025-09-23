package groupe_9_daimai.com.Daimai.DTO;

import groupe_9_daimai.com.Daimai.Entite.enums.ModePaiement;
import groupe_9_daimai.com.Daimai.Entite.enums.StatutPaiement;

import java.time.LocalDate;

public class PaiementResponseDTO {
    private Long id;
    private Double montant;
    private LocalDate datePaiement;
    private ModePaiement modePaiement;
    private StatutPaiement statutPaiement;
    private Long parrainId;
    private String parrainNom;
    private String parrainPrenom;
    private String codeTransaction;
    private String messageStatut;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }

    public LocalDate getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDate datePaiement) { this.datePaiement = datePaiement; }

    public ModePaiement getModePaiement() { return modePaiement; }
    public void setModePaiement(ModePaiement modePaiement) { this.modePaiement = modePaiement; }

    public StatutPaiement getStatutPaiement() { return statutPaiement; }
    public void setStatutPaiement(StatutPaiement statutPaiement) { this.statutPaiement = statutPaiement; }

    public Long getParrainId() { return parrainId; }
    public void setParrainId(Long parrainId) { this.parrainId = parrainId; }

    public String getParrainNom() { return parrainNom; }
    public void setParrainNom(String parrainNom) { this.parrainNom = parrainNom; }

    public String getParrainPrenom() { return parrainPrenom; }
    public void setParrainPrenom(String parrainPrenom) { this.parrainPrenom = parrainPrenom; }

    public String getCodeTransaction() { return codeTransaction; }
    public void setCodeTransaction(String codeTransaction) { this.codeTransaction = codeTransaction; }

    public String getMessageStatut() { return messageStatut; }
    public void setMessageStatut(String messageStatut) { this.messageStatut = messageStatut; }
}
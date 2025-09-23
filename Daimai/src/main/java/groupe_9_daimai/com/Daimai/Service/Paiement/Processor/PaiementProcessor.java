package groupe_9_daimai.com.Daimai.Service.Paiement;

import groupe_9_daimai.com.Daimai.DTO.PaiementRequestDTO;
import groupe_9_daimai.com.Daimai.Entite.enums.ModePaiement;
import groupe_9_daimai.com.Daimai.Entite.enums.StatutPaiement;

public interface PaiementProcessor {
    boolean supports(ModePaiement modePaiement);
    ProcessPaiementResult process(PaiementRequestDTO paiementRequest);
    StatutPaiement checkStatus(String transactionId);
}

### ProcessPaiementResult.java
```java
package groupe_9_daimai.com.Daimai.Service.Paiement;

import groupe_9_daimai.com.Daimai.Entite.enums.StatutPaiement;

public class ProcessPaiementResult {
    private boolean success;
    private String transactionId;
    private String message;
    private StatutPaiement statut;
    private String codeValidation;

    public ProcessPaiementResult(boolean success, String transactionId, String message, StatutPaiement statut) {
        this.success = success;
        this.transactionId = transactionId;
        this.message = message;
        this.statut = statut;
    }

    // Getters et Setters
    public boolean isSuccess() { return success; }
    public String getTransactionId() { return transactionId; }
    public String getMessage() { return message; }
    public StatutPaiement getStatut() { return statut; }
    public String getCodeValidation() { return codeValidation; }
    public void setCodeValidation(String codeValidation) { this.codeValidation = codeValidation; }
}
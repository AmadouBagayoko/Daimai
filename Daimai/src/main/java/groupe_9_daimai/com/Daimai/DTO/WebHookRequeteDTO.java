package groupe_9_daimai.com.Daimai.DTO;


import lombok.Data;

@Data
public class WebHookRequeteDTO {

    private String transactionId;
    private String status;
    private String reference; //Si le fournisseur renvoie une reference client

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}

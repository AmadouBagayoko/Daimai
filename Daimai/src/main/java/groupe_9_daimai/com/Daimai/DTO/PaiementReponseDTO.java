package groupe_9_daimai.com.Daimai.DTO;


import lombok.Data;

@Data
public class PaiementReponseDTO {
    private String status;
    private String message;
    private String transactionId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public PaiementReponseDTO() {
        this.status = status;
        this.message = message;
        this.transactionId = transactionId;
    }
}

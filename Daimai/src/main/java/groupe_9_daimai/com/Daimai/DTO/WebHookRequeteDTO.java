package groupe_9_daimai.com.Daimai.DTO;


import lombok.Data;

@Data
public class WebHookRequeteDTO {

    private String transactionId;
    private String status;
    private String reference; //Si le fournisseur renvoie une reference client
}

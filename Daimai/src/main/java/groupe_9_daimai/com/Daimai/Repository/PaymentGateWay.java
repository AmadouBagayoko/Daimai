package groupe_9_daimai.com.Daimai.Repository;

import groupe_9_daimai.com.Daimai.DTO.PaiementReponseDTO;
import groupe_9_daimai.com.Daimai.DTO.PaiementRequestDTO;

public interface PaymentGateWay {
    PaiementReponseDTO processPayment(PaiementRequestDTO paiementRequestDTO);
}

package groupe_9_daimai.com.Daimai.Service.Paiement.Processor;

import groupe_9_daimai.com.Daimai.DTO.PaiementRequestDTO;
import groupe_9_daimai.com.Daimai.Entite.enums.ModePaiement;
import groupe_9_daimai.com.Daimai.Entite.enums.StatutPaiement;
import groupe_9_daimai.com.Daimai.Service.Paiement.ProcessPaiementResult;

public interface PaiementProcessor {
    boolean supports(ModePaiement modePaiement);
    ProcessPaiementResult process(PaiementRequestDTO paiementRequest);
    StatutPaiement checkStatus(String transactionId);
}

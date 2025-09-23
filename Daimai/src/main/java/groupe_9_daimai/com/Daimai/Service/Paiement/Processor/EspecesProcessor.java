package groupe_9_daimai.com.Daimai.Service.Paiement;

import groupe_9_daimai.com.Daimai.DTO.PaiementRequestDTO;
import groupe_9_daimai.com.Daimai.Entite.enums.ModePaiement;
import groupe_9_daimai.com.Daimai.Entite.enums.StatutPaiement;
import org.springframework.stereotype.Service;

@Service
public class EspecesProcessor implements PaiementProcessor {

    @Override
    public boolean supports(ModePaiement modePaiement) {
        return modePaiement == ModePaiement.ESPECES;
    }

    @Override
    public ProcessPaiementResult process(PaiementRequestDTO paiementRequest) {
        try {
            String codeTransaction = "ESP_" + System.currentTimeMillis();

            ProcessPaiementResult result = new ProcessPaiementResult(true, codeTransaction,
                    "Paiement en espèces enregistré - Code: " + codeTransaction, StatutPaiement.EN_ATTENTE_CONFIRMATION);
            result.setCodeValidation(codeTransaction);

            return result;

        } catch (Exception e) {
            return new ProcessPaiementResult(false, null,
                    "Erreur enregistrement espèces: " + e.getMessage(), StatutPaiement.ECHEC);
        }
    }

    @Override
    public StatutPaiement checkStatus(String transactionId) {
        return StatutPaiement.EN_ATTENTE_CONFIRMATION;
    }
}
package groupe_9_daimai.com.Daimai.Service.Paiement;

import groupe_9_daimai.com.Daimai.DTO.PaiementRequestDTO;
import groupe_9_daimai.com.Daimai.Entite.enums.ModePaiement;
import groupe_9_daimai.com.Daimai.Entite.enums.StatutPaiement;
import org.springframework.stereotype.Service;

@Service
public class PayPalProcessor implements PaiementProcessor {

    @Override
    public boolean supports(ModePaiement modePaiement) {
        return modePaiement == ModePaiement.PAYPAL;
    }

    @Override
    public ProcessPaiementResult process(PaiementRequestDTO paiementRequest) {
        try {
            String transactionId = "PP_" + System.currentTimeMillis();

            // Intégration API PayPal
            Thread.sleep(1000);

            return new ProcessPaiementResult(true, transactionId,
                    "Paiement PayPal initié avec succès", StatutPaiement.EN_ATTENTE);

        } catch (Exception e) {
            return new ProcessPaiementResult(false, null,
                    "Erreur PayPal: " + e.getMessage(), StatutPaiement.ECHEC);
        }
    }

    @Override
    public StatutPaiement checkStatus(String transactionId) {
        return StatutPaiement.CONFIRME;
    }
}
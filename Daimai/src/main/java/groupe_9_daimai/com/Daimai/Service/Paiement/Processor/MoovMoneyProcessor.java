package groupe_9_daimai.com.Daimai.Service.Paiement.Processor;

import groupe_9_daimai.com.Daimai.DTO.PaiementRequestDTO;
import groupe_9_daimai.com.Daimai.Entite.enums.ModePaiement;
import groupe_9_daimai.com.Daimai.Entite.enums.StatutPaiement;
import groupe_9_daimai.com.Daimai.Service.Paiement.ProcessPaiementResult;
import org.springframework.stereotype.Service;

@Service
public class MoovMoneyProcessor implements PaiementProcessor {

    @Override
    public boolean supports(ModePaiement modePaiement) {
        return modePaiement == ModePaiement.MOOV_MONEY;
    }

    @Override
    public ProcessPaiementResult process(PaiementRequestDTO paiementRequest) {
        try {
            // Simulation de l'appel à l'API Moov Money
            String transactionId = "MM_" + System.currentTimeMillis();

            // Intégration API Moov Money
            Thread.sleep(1500);

            return new ProcessPaiementResult(true, transactionId,
                    "Paiement Moov Money initié avec succès", StatutPaiement.EN_ATTENTE);

        } catch (Exception e) {
            return new ProcessPaiementResult(false, null,
                    "Erreur Moov Money: " + e.getMessage(), StatutPaiement.ECHEC);
        }
    }

    @Override
    public StatutPaiement checkStatus(String transactionId) {
        return StatutPaiement.CONFIRME;
    }
}
package groupe_9_daimai.com.Daimai.Service.Paiement.Processor;

import groupe_9_daimai.com.Daimai.DTO.PaiementRequestDTO;
import groupe_9_daimai.com.Daimai.Entite.enums.ModePaiement;
import groupe_9_daimai.com.Daimai.Entite.enums.StatutPaiement;
import groupe_9_daimai.com.Daimai.Service.Paiement.ProcessPaiementResult;
import org.springframework.stereotype.Service;

@Service
public class CarteBancaireProcessor implements PaiementProcessor {

    @Override
    public boolean supports(ModePaiement modePaiement) {
        return modePaiement == ModePaiement.CARTE_BANCAIRE;
    }

    @Override
    public ProcessPaiementResult process(PaiementRequestDTO paiementRequest) {
        try {
            // Validation de la carte
            if (!validerCarte(paiementRequest.getNumeroCarte())) {
                return new ProcessPaiementResult(false, null,
                        "Numéro de carte invalide", StatutPaiement.ECHEC);
            }

            String transactionId = "CB_" + System.currentTimeMillis();

            // Simulation traitement carte
            Thread.sleep(3000);

            return new ProcessPaiementResult(true, transactionId,
                    "Paiement par carte bancaire traité", StatutPaiement.CONFIRME);

        } catch (Exception e) {
            return new ProcessPaiementResult(false, null,
                    "Erreur carte bancaire: " + e.getMessage(), StatutPaiement.ECHEC);
        }
    }

    private boolean validerCarte(String numeroCarte) {
        // Logique de validation Luhn simplifiée
        return numeroCarte != null && numeroCarte.matches("\\d{16}");
    }

    @Override
    public StatutPaiement checkStatus(String transactionId) {
        return StatutPaiement.CONFIRME;
    }
}
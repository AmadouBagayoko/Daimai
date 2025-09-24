package groupe_9_daimai.com.Daimai.Controller;

import groupe_9_daimai.com.Daimai.DTO.PaiementReponseDTO;
import groupe_9_daimai.com.Daimai.DTO.PaiementRequestDTO;
import groupe_9_daimai.com.Daimai.DTO.WebHookRequeteDTO;
import groupe_9_daimai.com.Daimai.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paiements")
public class PaiementController {

    @Autowired
    private PaymentService paiementService;

    // Endpoint pour initier un paiement
    @PostMapping("/process")
    public ResponseEntity<PaiementReponseDTO> processPayment(@RequestBody PaiementRequestDTO requestDto) {
        try {
            PaiementReponseDTO response = paiementService.processPayment(requestDto);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            PaiementReponseDTO errorResponse = new PaiementReponseDTO();
            errorResponse.setStatus("FAILED");
            errorResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // Endpoint pour recevoir les notifications de webhook
    @PostMapping("/webhook/{fournisseur}")
    public ResponseEntity<Void> handleWebhook(@PathVariable String fournisseur, @RequestBody WebHookRequeteDTO webhookData) {
        System.out.println("Webhook reçu de " + fournisseur + " pour la transaction " + webhookData.getTransactionId());

        // Mettre à jour le statut du paiement en base de données
        paiementService.updatePaymentStatus(webhookData.getTransactionId(), webhookData.getStatus());

        return ResponseEntity.ok().build();
    }
}

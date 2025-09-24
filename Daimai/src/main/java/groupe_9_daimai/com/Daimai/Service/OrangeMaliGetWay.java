package groupe_9_daimai.com.Daimai.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import groupe_9_daimai.com.Daimai.DTO.PaiementReponseDTO;
import groupe_9_daimai.com.Daimai.DTO.PaiementRequestDTO;
import groupe_9_daimai.com.Daimai.Repository.PaymentGateWay;
import lombok.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service("ORANGE_MONEY")
public class OrangeMaliGetWay implements PaymentGateWay {

    @Value("${orange.mali.api.url}")
    private String apiUrl;

    @Value("${orange.mali.api.token}")
    private String apiToken;

    private  WebClient webClient;
    private ObjectMapper objectMapper;

    public void OrangeMaliGateway() {
        this.webClient = WebClient.create();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public PaiementReponseDTO processPayment(PaiementRequestDTO paiementRequestDTO) {
        try {
            // 1. Construire le corps de la requête JSON pour l'API Orange Money
            String requestBody = objectMapper.writeValueAsString(
                    new OrangeMaliApiRequest(
                            paiementRequestDTO.getMontant(),
                            paiementRequestDTO.getNumero(),
                            paiementRequestDTO.getClientReference()
                    )
            );

            // 2. Envoyer la requête HTTP POST à l'API d'Orange Money
            String apiResponse = webClient.post()
                    .uri(apiUrl + "/payment")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // 3. Traiter la réponse de l'API d'Orange Money
            JsonNode rootNode = objectMapper.readTree(apiResponse);
            String transactionId = rootNode.path("data").path("txnid").asText();
            String status = rootNode.path("status").asText();

            PaiementReponseDTO response = new PaiementReponseDTO();
            response.setStatus(status.equalsIgnoreCase("success") ? "PENDING" : "FAILED");
            response.setTransactionId(transactionId);
            response.setMessage("Le paiement est en attente de confirmation du client.");

            return response;
        } catch (Exception e) {
            // Gérer les erreurs de connexion ou de l'API
            PaiementReponseDTO response = new PaiementReponseDTO();
            response.setStatus("FAILED");
            response.setTransactionId(null);
            response.setMessage("Erreur lors de l'appel à l'API d'Orange Money : " + e.getMessage());
            return response;
        }
    }

    // Classe interne pour structurer la requête de l'API Orange Money
    private static class OrangeMaliApiRequest {
        public double amount;
        public String phone;
        public String reference;

        public OrangeMaliApiRequest(double amount, String phone, String reference) {
            this.amount = amount;
            this.phone = phone;
            this.reference = reference;
        }
    }
}

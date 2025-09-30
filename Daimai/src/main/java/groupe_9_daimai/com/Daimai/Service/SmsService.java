package groupe_9_daimai.com.Daimai.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Component
public class SmsService {

    @Value("${easysendsms.apikey}") // Clé API à mettre dans application.properties
    private String apiKey;

    @Value("${easysendsms.url:https://restapi.easysendsms.app/v1/rest/sms/send}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Envoi d'un SMS via EasySendSMS
     * @param from Expéditeur
     * @param to Destinataire (numéro complet avec indicatif, ex: +223XXXXXXXX)
     * @param message Contenu du SMS
     */
    public void sendSms(String from, String to, String message) {
        try {
            // Préparer le corps de la requête JSON
            JSONObject body = new JSONObject();
            body.put("from", from);
            body.put("to", to);
            body.put("message", message);

            // Préparer les headers avec l'API Key
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("apikey", apiKey);

            HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);

            // Envoi POST
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);

            if(response.getStatusCode() == HttpStatus.OK) {
                System.out.println("SMS envoyé avec succès à " + to);
            } else {
                System.err.println("Erreur lors de l'envoi du SMS : " + response.getBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
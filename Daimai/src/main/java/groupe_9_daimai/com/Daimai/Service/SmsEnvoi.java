package groupe_9_daimai.com.Daimai.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.json.JSONObject;

@Service
public class SmsEnvoi {

    private final String API_URL = "https://restapi.easysendsms.app/v1/rest/sms/send";
    private final String API_KEY = "3j8l5o1omb7cfqjmpmxlpa95x9mcj53s"; // Remplace par ta clé

    public void sendSms(String to, String message, String from) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            JSONObject json = new JSONObject();
            json.put("to", to);
            json.put("message", message);
            json.put("from", from);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", API_KEY);

            HttpEntity<String> entity = new HttpEntity<>(json.toString(), headers);
            ResponseEntity<String> response = restTemplate.postForEntity(API_URL, entity, String.class);

            System.out.println("SMS envoyé : " + response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package groupe_9_daimai.com.Daimai.Controller;

import groupe_9_daimai.com.Daimai.DTO.NotificationDto;
import groupe_9_daimai.com.Daimai.Entite.Notification;
import groupe_9_daimai.com.Daimai.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Test d'envoi d'une notification
    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationDto request) {
        try {
            Notification notification = new Notification();
            notification.setRecepteur(request.getRecepteur());
            notification.setEnvoyeur(request.getEnvoyeur());
            notification.setContenue(request.getContenue());
            notification.setTypeNotifcation(request.getTypeNotifcation());

            notificationService.envoiNotification(notification);

            return ResponseEntity.ok("Notification envoyée avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de l'envoi : " + e.getMessage());
        }
    }

}
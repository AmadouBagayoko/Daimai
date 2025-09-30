package groupe_9_daimai.com.Daimai.Service;

import groupe_9_daimai.com.Daimai.Entite.Notification;
import groupe_9_daimai.com.Daimai.Entite.enums.TypeNotifcation;
import groupe_9_daimai.com.Daimai.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender; // Pour Email

    @Autowired
    private SmsService smsSender; // J'ai corrigé le nom de la classe ici

    @Autowired
    private NotificationRepository notificationRepository;

    // Envoyer et enregistrer une notification
    public void envoiNotification(Notification notification) {
        notification.setDateEnvoi(LocalDate.now());

        if (notification.getTypeNotifcation() == TypeNotifcation.EMAIL) {
            sendEmail(notification.getRecepteur(), "Notification", notification.getContenue());
        } else if (notification.getTypeNotifcation() == TypeNotifcation.SMS) {
            // Ordre corrigé : from (envoyeur), to (recepteur), message (contenue)
            smsSender.sendSms(notification.getEnvoyeur(), notification.getRecepteur(), notification.getContenue());
        }

        notificationRepository.save(notification);
    }

    private void sendEmail(String Recepteur, String sujet, String contenue) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(Recepteur);
        mail.setSubject(sujet);
        mail.setText(contenue);
        mailSender.send(mail);
    }
}
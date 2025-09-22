package groupe_9_daimai.com.Daimai.Service;

import groupe_9_daimai.com.Daimai.Entite.Notification;
import groupe_9_daimai.com.Daimai.Entite.enums.TypeNotifcation;
import groupe_9_daimai.com.Daimai.Repository.NotificationRepository;
import groupe_9_daimai.com.Daimai.Service.SmsEnvoi;
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
    private SmsEnvoi smsSender; // Pour SMS

    @Autowired
    private NotificationRepository notificationRepository;

    // Envoyer et enregistrer une notification
    public void envoiNotification(Notification notification) {
        notification.setDateEnvoi(LocalDate.now());

        if (notification.getTypeNotifcation() == TypeNotifcation.EMAIL) {
            sendEmail(notification.getRecepteur(), "Notification", notification.getContenue());
        } else if (notification.getTypeNotifcation() == TypeNotifcation.SMS) {
            smsSender.sendSms(notification.getRecepteur(), notification.getContenue(), notification.getEnvoyeur());
        }

        notificationRepository.save(notification);
    }

    private void sendEmail(String to, String subject, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(message);
        mailSender.send(mail);
    }
}

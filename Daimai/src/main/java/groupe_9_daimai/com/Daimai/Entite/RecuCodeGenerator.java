package groupe_9_daimai.com.Daimai.Service;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class RecuCodeGenerator {

    private static final String PREFIX = "RECU";
    private static final int RANDOM_SUFFIX_LENGTH = 6;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String generateRecuCode(Long paiementId) {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomPart = generateRandomString(RANDOM_SUFFIX_LENGTH);

        return String.format("%s-%s-%s-%d", PREFIX, datePart, randomPart, paiementId);
    }

    private String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return sb.toString();
    }
}
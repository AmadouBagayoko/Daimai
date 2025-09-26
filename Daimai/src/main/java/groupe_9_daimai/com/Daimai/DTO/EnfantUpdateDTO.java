package groupe_9_daimai.com.Daimai.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

@Getter
@Setter
public class EnfantUpdateDTO {
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String niveauScolaire;
    private String tuteur;
    private String telephone;
    private String email;
    private MultipartFile photo; //
    private Boolean statutAbandon;
}

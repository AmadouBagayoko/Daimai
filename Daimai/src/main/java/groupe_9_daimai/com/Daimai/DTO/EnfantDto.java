package groupe_9_daimai.com.Daimai.DTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class EnfantDto {
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String niveauScolaire;
    private MultipartFile photo;
    private String tuteur;
    private String telephone;
    private String email;
    private Boolean statutAbandon;

    // ID de l'association (l'entité Association est gérée par le Service)
    private Long associationId; // N'est pas utilisé ici, car il est dans le chemin URL du Controller, mais on le garde pour d'autres usages.
}
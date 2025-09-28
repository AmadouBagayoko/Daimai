package groupe_9_daimai.com.Daimai.DTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AssociationDTO {
    private String nom;
    private String email;
    private String adresse;
    private String domaine;
    private String telephone;
    private MultipartFile photo;
    private MultipartFile autorisation;
}


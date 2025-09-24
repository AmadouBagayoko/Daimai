package groupe_9_daimai.com.Daimai.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AssociationDTO {
    private String nom;
    private String email;
    private String adresse;
    private String domaine;
    private String telephone;
    private String photo;
    private String autorisation;

    public AssociationDTO() {}

    public AssociationDTO(String nom, String email, String adresse, String domaine, String telephone, String photo, String autorisation) {
        this.nom = nom;
        this.email = email;
        this.adresse = adresse;
        this.domaine = domaine;
        this.telephone = telephone;
        this.photo = photo;
        this.autorisation= autorisation;
    }


}

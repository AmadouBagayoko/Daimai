package groupe_9_daimai.com.Daimai.ReponseDto;

import groupe_9_daimai.com.Daimai.Entite.Parrain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParrainSummaryDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;

    public ParrainSummaryDTO(Parrain parrain) {
        this.id = parrain.getId();
        this.nom = parrain.getNom();
        this.prenom = parrain.getPrenom();
        this.email = parrain.getEmail();
    }
}
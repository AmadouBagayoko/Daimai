package groupe_9_daimai.com.Daimai.ReponseDto;
import groupe_9_daimai.com.Daimai.Entite.Enfant;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class EnfantResponseDTO {
    // Les champs visibles par le client
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String niveauScolaire;
    private String urlPhoto;
    private String tuteur;
    private String telephone;
    private String email;
    private Boolean statutAbandon;

    // Simplification des relations pour le client
    private Long associationId;
    private String nomAssociation;
    private int nombreParrains; // Au lieu de la liste complète des entités Parrain

    // Constructeur pour mapper l'Entité Enfant vers le DTO
    public EnfantResponseDTO(Enfant enfant) {
        this.id = enfant.getId();
        this.nom = enfant.getNom();
        this.prenom = enfant.getPrenom();
        this.dateNaissance = enfant.getDateNaissance();
        this.niveauScolaire = enfant.getNiveauScolaire();
        this.urlPhoto = enfant.getUrlPhoto();
        this.tuteur = enfant.getTuteur();
        this.telephone = enfant.getTelephone();
        this.email = enfant.getEmail();
        this.statutAbandon = enfant.getStatutAbandon();

        // Mappage des relations
        if (enfant.getAssociation() != null) {
            this.associationId = enfant.getAssociation().getId();
            // On suppose que l'entité Association a un getNom()
            // this.nomAssociation = enfant.getAssociation().getNom();
        }
        this.nombreParrains = enfant.getParrains() != null ? enfant.getParrains().size() : 0;
    }
}
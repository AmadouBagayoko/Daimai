package groupe_9_daimai.com.Daimai.ReponseDto;

import groupe_9_daimai.com.Daimai.Entite.Enfant;
import groupe_9_daimai.com.Daimai.Entite.Parrain; // Import de l'entité Parrain
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors; // NOUVEL IMPORT

@Getter
@Setter
public class EnfantResponseDTO {
    // ... (Champs existants)
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
    private String motDepasse;

    // Simplification des relations pour le client
    private Long associationId;
    private String nomAssociation;

    // NOUVEAU CHAMP : La liste des DTOs de parrains
    private List<ParrainSummaryDTO> parrains; // 👈 AJOUTÉ

    private int nombreParrains; // Maintenu pour la concision

    // Constructeur pour mapper l'Entité Enfant vers le DTO
    public EnfantResponseDTO(Enfant enfant) {
        // ... (Mappage des champs existants)
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
        this.motDepasse= enfant.getMotDepasse();

        // Mappage de l'Association
        if (enfant.getAssociation() != null) {
            this.associationId = enfant.getAssociation().getId();
            this.nomAssociation = enfant.getAssociation().getNom();
        }

        // Mappage des Parrains (AJOUTÉ / MODIFIÉ)
        if (enfant.getParrains() != null) {
            this.nombreParrains = enfant.getParrains().size();

            // Mappe chaque entité Parrain en un ParrainSummaryDTO
            this.parrains = enfant.getParrains().stream()
                    .map(ParrainSummaryDTO::new) // Utilise le constructeur du DTO
                    .collect(Collectors.toList());
        } else {
            this.nombreParrains = 0;
            this.parrains = List.of(); // Liste vide par défaut
        }
    }
}
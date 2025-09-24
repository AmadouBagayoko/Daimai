package groupe_9_daimai.com.Daimai.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

public class EnfantRequestDTO {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotNull(message = "La date de naissance est obligatoire")
    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateNaissance;

    @NotBlank(message = "Le niveau scolaire est obligatoire")
    private String niveauScolaire;

    private String urlPhoto;

    @NotBlank(message = "Le nom du tuteur est obligatoire")
    private String tuteur;

    @NotBlank(message = "Le téléphone du tuteur est obligatoire")
    private String telephone;

    @NotBlank(message = "L'email du tuteur est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    @NotNull(message = "L'ID de l'association est obligatoire")
    private Long associationId;

    // Getters et Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getNiveauScolaire() { return niveauScolaire; }
    public void setNiveauScolaire(String niveauScolaire) { this.niveauScolaire = niveauScolaire; }

    public String getUrlPhoto() { return urlPhoto; }
    public void setUrlPhoto(String urlPhoto) { this.urlPhoto = urlPhoto; }

    public String getTuteur() { return tuteur; }
    public void setTuteur(String tuteur) { this.tuteur = tuteur; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getAssociationId() { return associationId; }
    public void setAssociationId(Long associationId) { this.associationId = associationId; }
}
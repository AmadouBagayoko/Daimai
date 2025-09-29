package groupe_9_daimai.com.Daimai.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AssociationRequestDTO {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    @NotBlank(message = "L'adresse est obligatoire")
    private String adresse;

    @NotBlank(message = "Le domaine est obligatoire")
    private String domaine;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDepasse;

    @NotBlank(message = "Le téléphone est obligatoire")
    private String telephone;

    private String photo;

    @NotNull(message = "L'ID de l'administrateur est obligatoire")
    private Long administrateurId;

    // Getters et Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getDomaine() { return domaine; }
    public void setDomaine(String domaine) { this.domaine = domaine; }

    public String getMotDepasse() { return motDepasse; }
    public void setMotDepasse(String motDepasse) { this.motDepasse = motDepasse; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }

    public Long getAdministrateurId() { return administrateurId; }
    public void setAdministrateurId(Long administrateurId) { this.administrateurId = administrateurId; }
}
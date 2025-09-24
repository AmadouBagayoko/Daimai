package groupe_9_daimai.com.Daimai.DTO;

import java.time.LocalDate;

public class EnfantResponseDTO {
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private Integer age;
    private String niveauScolaire;
    private String urlPhoto;
    private String tuteur;
    private String telephone;
    private String email;
    private Boolean statutAbandon;
    private Long associationId;
    private String associationNom;
    private Integer nombreParrains;

    // Constructeurs
    public EnfantResponseDTO() {}

    public EnfantResponseDTO(Long id, String nom, String prenom, LocalDate dateNaissance, Integer age,
                             String niveauScolaire, String urlPhoto, String tuteur, String telephone,
                             String email, Boolean statutAbandon, Long associationId, String associationNom,
                             Integer nombreParrains) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.age = age;
        this.niveauScolaire = niveauScolaire;
        this.urlPhoto = urlPhoto;
        this.tuteur = tuteur;
        this.telephone = telephone;
        this.email = email;
        this.statutAbandon = statutAbandon;
        this.associationId = associationId;
        this.associationNom = associationNom;
        this.nombreParrains = nombreParrains;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

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

    public Boolean getStatutAbandon() { return statutAbandon; }
    public void setStatutAbandon(Boolean statutAbandon) { this.statutAbandon = statutAbandon; }

    public Long getAssociationId() { return associationId; }
    public void setAssociationId(Long associationId) { this.associationId = associationId; }

    public String getAssociationNom() { return associationNom; }
    public void setAssociationNom(String associationNom) { this.associationNom = associationNom; }

    public Integer getNombreParrains() { return nombreParrains; }
    public void setNombreParrains(Integer nombreParrains) { this.nombreParrains = nombreParrains; }
}
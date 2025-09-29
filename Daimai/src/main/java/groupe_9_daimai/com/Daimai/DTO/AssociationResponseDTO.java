package groupe_9_daimai.com.Daimai.DTO;

public class AssociationResponseDTO {
    private Long id;
    private String nom;
    private String email;
    private String adresse;
    private String domaine;
    private String telephone;
    private String photo;
    private Boolean statutBloquer;
    private String autorisation;
    private String administrateurNom;
    private Long administrateurId;
    private Integer nombreEnfants;
    private Integer nombreParrains;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getDomaine() { return domaine; }
    public void setDomaine(String domaine) { this.domaine = domaine; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }

    public Boolean getStatutBloquer() { return statutBloquer; }
    public void setStatutBloquer(Boolean statutBloquer) { this.statutBloquer = statutBloquer; }

    public String getAutorisation() { return autorisation; }
    public void setAutorisation(String autorisation) { this.autorisation = autorisation; }

    public String getAdministrateurNom() { return administrateurNom; }
    public void setAdministrateurNom(String administrateurNom) { this.administrateurNom = administrateurNom; }

    public Long getAdministrateurId() { return administrateurId; }
    public void setAdministrateurId(Long administrateurId) { this.administrateurId = administrateurId; }

    public Integer getNombreEnfants() { return nombreEnfants; }
    public void setNombreEnfants(Integer nombreEnfants) { this.nombreEnfants = nombreEnfants; }

    public Integer getNombreParrains() { return nombreParrains; }
    public void setNombreParrains(Integer nombreParrains) { this.nombreParrains = nombreParrains; }
}
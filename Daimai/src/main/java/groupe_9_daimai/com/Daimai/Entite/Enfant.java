package groupe_9_daimai.com.Daimai.Entite;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Enfant")
public class Enfant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nom;

    @Column
    private String prenom;

    @Column
    private LocalDate dateNaissance;

    @Column
    private String niveauScolaire;

    @Column
    private String urlPhoto;

    @Column
    private String tuteur;

    @Column
    private String telephone;

    @Column(unique = true)
    private String email;

    @Column
    private Boolean statutAbandon;

    @Column
    private String motDepasse;

    @ManyToOne
    @JoinColumn(name = "association_id")
    @JsonBackReference
    private Association association;

    @OneToMany(mappedBy = "enfant")
    private List<RapportScolaire> rapportScolaire;

    @ManyToMany(mappedBy = "enfants")
    @JsonBackReference
    private Set<Parrain> parrains = new HashSet<>();

    public Enfant() {}

    @Override
    public String toString() {
        return "Enfant{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", niveauScolaire='" + niveauScolaire + '\'' +
                ", urlPhoto='" + urlPhoto + '\'' +
                ", tuteur='" + tuteur + '\'' +
                ", telephone=" + telephone +
                ", email='" + email + '\'' +
                ", statutAbandon=" + statutAbandon +
                ", motDepasse='" + motDepasse + '\'' +
                ", association=" + association +
                ", rapportScolaire=" + rapportScolaire +
                ", parrains=" + parrains +
                '}';
    }

    public Enfant(Long id, String nom, String prenom, LocalDate dateNaissance, String niveauScolaire, String urlPhoto, String tuteur, String telephone, String email, Boolean statutAbandon, String motDepasse, Association association, List<RapportScolaire> rapportScolaire, Set<Parrain> parrains) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.niveauScolaire = niveauScolaire;
        this.urlPhoto = urlPhoto;
        this.tuteur = tuteur;
        this.telephone = telephone;
        this.email = email;
        this.statutAbandon = statutAbandon;
        this.motDepasse = motDepasse;
        this.association = association;
        this.rapportScolaire = rapportScolaire;
        this.parrains = parrains;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNiveauScolaire() {
        return niveauScolaire;
    }

    public void setNiveauScolaire(String niveauScolaire) {
        this.niveauScolaire = niveauScolaire;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getTuteur() {
        return tuteur;
    }

    public void setTuteur(String tuteur) {
        this.tuteur = tuteur;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatutAbandon() {
        return statutAbandon;
    }

    public void setStatutAbandon(Boolean statutAbandon) {
        this.statutAbandon = statutAbandon;
    }

    public String getMotDepasse() {
        return motDepasse;
    }

    public void setMotDepasse(String motDepasse) {
        this.motDepasse = motDepasse;
    }

    public Association getAssociation() {
        return association;
    }

    public void setAssociation(Association association) {
        this.association = association;
    }

    public List<RapportScolaire> getRapportScolaire() {
        return rapportScolaire;
    }

    public void setRapportScolaire(List<RapportScolaire> rapportScolaire) {
        this.rapportScolaire = rapportScolaire;
    }

    public Set<Parrain> getParrains() {
        return parrains;
    }

    public void setParrains(Set<Parrain> parrains) {
        this.parrains = parrains;
    }
}

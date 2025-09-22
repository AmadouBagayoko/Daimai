package groupe_9_daimai.com.Daimai.Entite;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Parrain")
public class Parrain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nom;

    @Column
    private String prenom;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String telephone;

    @Column
    private String motDepasse;

    @Column
    private Boolean roleUtilisateur;

    @Column
    private String profession;

    @Column
    private String adresse;

    @Column
    private LocalDate dateCreation;



    @Column
    private  Boolean statut;

    @ManyToMany
    @JoinTable(
            name = "parrainage",
            joinColumns = @JoinColumn(name = "enfant_id"),
            inverseJoinColumns = @JoinColumn(name = "parrain_id")
    )
    private List<Enfant> enfants ;

    @ManyToOne
    @JoinColumn(name = "administrateur_id")
    private Administrateur administrateur;

    public Parrain(){
    }

    @Override
    public String toString() {
        return "Parrain{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", motDepasse='" + motDepasse + '\'' +
                ", roleUtilisateur=" + roleUtilisateur +
                ", profession='" + profession + '\'' +
                ", adresse='" + adresse + '\'' +
                ", dateCreation=" + dateCreation +
                ", statut=" + statut +
                ", enfants=" + enfants +
                ", administrateur=" + administrateur +
                '}';
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMotDepasse() {
        return motDepasse;
    }

    public void setMotDepasse(String motDepasse) {
        this.motDepasse = motDepasse;
    }

    public Boolean getRoleUtilisateur() {
        return roleUtilisateur;
    }

    public void setRoleUtilisateur(Boolean roleUtilisateur) {
        this.roleUtilisateur = roleUtilisateur;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Boolean getStatut() {
        return statut;
    }

    public void setStatut(Boolean statut) {
        this.statut = statut;
    }

    public List<Enfant> getEnfants() {
        return enfants;
    }

    public void setEnfants(List<Enfant> enfants) {
        this.enfants = enfants;
    }

    public Administrateur getAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(Administrateur administrateur) {
        this.administrateur = administrateur;
    }

    public Parrain(Long id, String nom, String prenom, String email, String telephone, String motDepasse, Boolean roleUtilisateur, String profession, String adresse, LocalDate dateCreation, Boolean statut, List<Enfant> enfants, Administrateur administrateur) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.motDepasse = motDepasse;
        this.roleUtilisateur = roleUtilisateur;
        this.profession = profession;
        this.adresse = adresse;
        this.dateCreation = dateCreation;
        this.statut = statut;
        this.enfants = enfants;
        this.administrateur = administrateur;
    }
}

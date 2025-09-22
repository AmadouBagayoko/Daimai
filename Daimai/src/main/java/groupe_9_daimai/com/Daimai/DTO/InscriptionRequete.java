package groupe_9_daimai.com.Daimai.DTO;


import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDate;


public class InscriptionRequete {



    private String email;


    private String nom;


    private String prenom;


    private String telephone;


    private String motDepasse;


    private Boolean roleUtilisateur;


    private String profession;


    private String adresse;


    private LocalDate dateCreation;


    private  Boolean statut;



    @Override
    public String toString() {
        return "InscriptionRequete{" +
                "email='" + email + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone='" + telephone + '\'' +
                ", motDepasse='" + motDepasse + '\'' +
                ", roleUtilisateur=" + roleUtilisateur +
                ", profession='" + profession + '\'' +
                ", adresse='" + adresse + '\'' +
                ", dateCreation=" + dateCreation +
                ", statut=" + statut +
                ", Telephone='" + Telephone + '\'' +
                '}';
    }

    public InscriptionRequete(String email, String nom, String prenom, String telephone, String motDepasse, Boolean roleUtilisateur, String profession, String adresse, LocalDate dateCreation, Boolean statut, String telephone1) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.motDepasse = motDepasse;
        this.roleUtilisateur = roleUtilisateur;
        this.profession = profession;
        this.adresse = adresse;
        this.dateCreation = dateCreation;
        this.statut = statut;
        Telephone = telephone1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    private String Telephone;
}

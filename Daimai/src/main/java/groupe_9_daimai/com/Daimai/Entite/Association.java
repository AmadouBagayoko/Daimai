package groupe_9_daimai.com.Daimai.Entite;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonManagedReference; // NOUVEL IMPORT

import java.util.List;

@Entity
@Table(name = "Association")
public class Association {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nom;

    @Column
    private String email;

    @Column
    private String adresse;

    @Column
    private String domaine;

    @Column
    private String motDepasse;

    @Column
    private String autorisation;

    @Column
    private String telephone;

    @Column
    private String photo;

    @Column
    private Boolean estvalider;

    @OneToMany(mappedBy = "association")
    @JsonManagedReference
    private List<Enfant> enfants;

    @OneToMany(mappedBy = "association")
    private List<Notification> Notifications;

    @OneToMany(mappedBy = "association")
    private List<Depense> Depenses;

    @ManyToOne
    @JoinColumn(name = "administrateur_id")
    private Administrateur Administrateur;

    public Association(Long id, String nom, String email, String adresse, String domaine, String motDepasse, String autorisation, String telephone, String photo, Boolean statutBloquer, List<Enfant> enfants, List<Notification> notifications, List<Depense> depenses, groupe_9_daimai.com.Daimai.Entite.Administrateur administrateur) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.adresse = adresse;
        this.domaine = domaine;
        this.motDepasse = motDepasse;
        this.autorisation = autorisation;
        this.telephone = telephone;
        this.photo = photo;
        this.estvalider = statutBloquer;
        enfants = enfants;
        Notifications = notifications;
        Depenses = depenses;
        Administrateur = administrateur;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getMotDepasse() {
        return motDepasse;
    }

    public void setMotDepasse(String motDepasse) {
        this.motDepasse = motDepasse;
    }

    public String getAutorisation() {
        return autorisation;
    }

    public void setAutorisation(String autorisation) {
        this.autorisation = autorisation;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Boolean getEstvalider() {
        return estvalider;
    }

    public void setEstvalider(Boolean statutBloquer) {
        this.estvalider = statutBloquer;
    }

    public List<Enfant> getEnfants() {
        return enfants;
    }

    public void setEnfants(List<Enfant> enfants) {
        enfants = enfants;
    }

    public List<Notification> getNotifications() {
        return Notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        Notifications = notifications;
    }

    public List<Depense> getDepenses() {
        return Depenses;
    }

    public void setDepenses(List<Depense> depenses) {
        Depenses = depenses;
    }

    public groupe_9_daimai.com.Daimai.Entite.Administrateur getAdministrateur() {
        return Administrateur;
    }

    public void setAdministrateur(groupe_9_daimai.com.Daimai.Entite.Administrateur administrateur) {
        Administrateur = administrateur;
    }

    public Association() {}

    @Override
    public String toString() {
        return "Association{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", adresse='" + adresse + '\'' +
                ", domaine='" + domaine + '\'' +
                ", motDepasse='" + motDepasse + '\'' +
                ", autorisation='" + autorisation + '\'' +
                ", telephone=" + telephone +
                ", photo='" + photo + '\'' +
                ", estvalider=" + estvalider +
                ", Enfants=" + enfants +
                ", Notifications=" + Notifications +
                ", Depenses=" + Depenses +
                ", Administrateur=" + Administrateur +
                '}';
    }
}

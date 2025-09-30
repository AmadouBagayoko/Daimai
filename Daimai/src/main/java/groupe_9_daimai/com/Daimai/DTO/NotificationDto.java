package groupe_9_daimai.com.Daimai.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import groupe_9_daimai.com.Daimai.Entite.enums.TypeNotifcation;





public class NotificationDto {

    @JsonProperty("recepteur")
    private String recepteur;

    @JsonProperty("envoyeur")
    private String envoyeur;

    @JsonProperty("contenue")
    private String contenue;

    @JsonProperty("typeNotifcation")
    private TypeNotifcation typeNotifcation;

    // Getters et Setters
    public String getRecepteur() {
        return recepteur;
    }

    public void setRecepteur(String recepteur) {
        this.recepteur = recepteur;
    }

    public String getEnvoyeur() {
        return envoyeur;
    }

    public void setEnvoyeur(String envoyeur) {
        this.envoyeur = envoyeur;
    }

    public String getContenue() {
        return contenue;
    }

    public void setContenue(String contenue) {
        this.contenue = contenue;
    }

    public TypeNotifcation getTypeNotifcation() {
        return typeNotifcation;
    }

    public void setTypeNotifcation(TypeNotifcation typeNotifcation) {
        this.typeNotifcation = typeNotifcation;
    }

    public NotificationDto() {}

    public NotificationDto(String recepteur, String envoyeur, String contenue, TypeNotifcation typeNotifcation) {
        this.recepteur = recepteur;
        this.envoyeur = envoyeur;
        this.contenue = contenue;
        this.typeNotifcation = typeNotifcation;
    }

    @Override
    public String toString() {
        return "NotificationDto{" +
                "recepteur='" + recepteur + '\'' +
                ", envoyeur='" + envoyeur + '\'' +
                ", contenue='" + contenue + '\'' +
                ", typeNotifcation=" + typeNotifcation +
                '}';
    }
}
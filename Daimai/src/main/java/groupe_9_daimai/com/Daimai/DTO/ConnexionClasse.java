package groupe_9_daimai.com.Daimai.DTO;


import lombok.*;


public class ConnexionClasse {

    private String Telephone;
    private String motDePasse;

    @Override
    public String toString() {
        return "ConnexionClasse{" +
                "Telephone='" + Telephone + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                '}';
    }

    public ConnexionClasse(String telephone, String motDePasse) {
        Telephone = telephone;
        this.motDePasse = motDePasse;
    }

    public ConnexionClasse(){

    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}

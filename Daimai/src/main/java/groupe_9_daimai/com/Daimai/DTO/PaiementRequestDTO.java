package groupe_9_daimai.com.Daimai.DTO;


import lombok.Data;

@Data
public class PaiementRequestDTO {

    private String modePaiemment;
    private Double montant;
    private String clientReference;
    private String numero;
    private String carte;

    public String getModePaiemment() {
        return modePaiemment;
    }

    public void setModePaiemment(String modePaiemment) {
        this.modePaiemment = modePaiemment;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getClientReference() {
        return clientReference;
    }

    public void setClientReference(String clientReference) {
        this.clientReference = clientReference;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCarte() {
        return carte;
    }

    public void setCarte(String carte) {
        this.carte = carte;
    }

    public PaiementRequestDTO(String modePaiemment, Double montant, String clientReference, String numero, String carte) {
        this.modePaiemment = modePaiemment;
        this.montant = montant;
        this.clientReference = clientReference;
        this.numero = numero;
        this.carte = carte;
    }
}

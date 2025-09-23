package groupe_9_daimai.com.Daimai.DTO;

import groupe_9_daimai.com.Daimai.Entite.enums.CategorieDepense;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class DepenseRequestDTO {

    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être positif")
    private Double montant;

    @NotNull(message = "La catégorie est obligatoire")
    private CategorieDepense categorie;

    private LocalDate dateDepense;

    @NotNull(message = "L'ID de l'association est obligatoire")
    private Long associationId;

    // Constructeurs
    public DepenseRequestDTO() {}

    public DepenseRequestDTO(Double montant, CategorieDepense categorie, LocalDate dateDepense, Long associationId) {
        this.montant = montant;
        this.categorie = categorie;
        this.dateDepense = dateDepense;
        this.associationId = associationId;
    }

    // Getters et Setters
    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public CategorieDepense getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieDepense categorie) {
        this.categorie = categorie;
    }

    public LocalDate getDateDepense() {
        return dateDepense;
    }

    public void setDateDepense(LocalDate dateDepense) {
        this.dateDepense = dateDepense;
    }

    public Long getAssociationId() {
        return associationId;
    }

    public void setAssociationId(Long associationId) {
        this.associationId = associationId;
    }
}
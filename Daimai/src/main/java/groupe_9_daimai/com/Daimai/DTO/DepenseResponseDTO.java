package groupe_9_daimai.com.Daimai.DTO;

import groupe_9_daimai.com.Daimai.Entite.enums.CategorieDepense;

import java.time.LocalDate;

public class DepenseResponseDTO {
    private Long id;
    private Double montant;
    private CategorieDepense categorie;
    private LocalDate dateDepense;
    private Long associationId;
    private String associationNom;
    private Long anneeScolaireId;
    private String anneeScolaireLibelle;
    private Boolean anneeScolaireActive;

    // Constructeurs
    public DepenseResponseDTO() {}

    public DepenseResponseDTO(Long id, Double montant, CategorieDepense categorie, LocalDate dateDepense,
                              Long associationId, String associationNom, Long anneeScolaireId,
                              String anneeScolaireLibelle, Boolean anneeScolaireActive) {
        this.id = id;
        this.montant = montant;
        this.categorie = categorie;
        this.dateDepense = dateDepense;
        this.associationId = associationId;
        this.associationNom = associationNom;
        this.anneeScolaireId = anneeScolaireId;
        this.anneeScolaireLibelle = anneeScolaireLibelle;
        this.anneeScolaireActive = anneeScolaireActive;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }

    public CategorieDepense getCategorie() { return categorie; }
    public void setCategorie(CategorieDepense categorie) { this.categorie = categorie; }

    public LocalDate getDateDepense() { return dateDepense; }
    public void setDateDepense(LocalDate dateDepense) { this.dateDepense = dateDepense; }

    public Long getAssociationId() { return associationId; }
    public void setAssociationId(Long associationId) { this.associationId = associationId; }

    public String getAssociationNom() { return associationNom; }
    public void setAssociationNom(String associationNom) { this.associationNom = associationNom; }

    public Long getAnneeScolaireId() { return anneeScolaireId; }
    public void setAnneeScolaireId(Long anneeScolaireId) { this.anneeScolaireId = anneeScolaireId; }

    public String getAnneeScolaireLibelle() { return anneeScolaireLibelle; }
    public void setAnneeScolaireLibelle(String anneeScolaireLibelle) { this.anneeScolaireLibelle = anneeScolaireLibelle; }

    public Boolean getAnneeScolaireActive() { return anneeScolaireActive; }
    public void setAnneeScolaireActive(Boolean anneeScolaireActive) { this.anneeScolaireActive = anneeScolaireActive; }
}
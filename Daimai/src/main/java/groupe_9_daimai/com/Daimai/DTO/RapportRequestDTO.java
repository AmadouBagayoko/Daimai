package groupe_9_daimai.com.Daimai.DTO;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class RapportRequestDTO {

    @NotNull(message = "L'ID de l'enfant est obligatoire")
    private Long enfantId;

    @NotNull(message = "La date du rapport est obligatoire")
    private LocalDate dateRapport;

    private String notes;
    private String presence;
    private String comportement;
    private Double moyenneGenerale;
    private String photoClasse;

    // Getters et Setters
    public Long getEnfantId() { return enfantId; }
    public void setEnfantId(Long enfantId) { this.enfantId = enfantId; }

    public LocalDate getDateRapport() { return dateRapport; }
    public void setDateRapport(LocalDate dateRapport) { this.dateRapport = dateRapport; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getPresence() { return presence; }
    public void setPresence(String presence) { this.presence = presence; }

    public String getComportement() { return comportement; }
    public void setComportement(String comportement) { this.comportement = comportement; }

    public Double getMoyenneGenerale() { return moyenneGenerale; }
    public void setMoyenneGenerale(Double moyenneGenerale) { this.moyenneGenerale = moyenneGenerale; }

    public String getPhotoClasse() { return photoClasse; }
    public void setPhotoClasse(String photoClasse) { this.photoClasse = photoClasse; }
}
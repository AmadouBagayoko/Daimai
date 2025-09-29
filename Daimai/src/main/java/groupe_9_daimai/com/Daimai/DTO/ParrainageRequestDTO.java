package groupe_9_daimai.com.Daimai.DTO;

import jakarta.validation.constraints.NotNull;

public class ParrainageRequestDTO {

    @NotNull(message = "L'ID de l'enfant est obligatoire")
    private Long enfantId;

    @NotNull(message = "L'ID du parrain est obligatoire")
    private Long parrainId;

    private String messagePersonnalise;

    // Getters et Setters
    public Long getEnfantId() { return enfantId; }
    public void setEnfantId(Long enfantId) { this.enfantId = enfantId; }

    public Long getParrainId() { return parrainId; }
    public void setParrainId(Long parrainId) { this.parrainId = parrainId; }

    public String getMessagePersonnalise() { return messagePersonnalise; }
    public void setMessagePersonnalise(String messagePersonnalise) { this.messagePersonnalise = messagePersonnalise; }
}
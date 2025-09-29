package groupe_9_daimai.com.Daimai.DTO;

public class ParrainageDTO {
    private Long parrainId;
    private Long enfantId;

    public ParrainageDTO() {}

    public ParrainageDTO(Long parrainId, Long enfantId) {
        this.parrainId = parrainId;
        this.enfantId = enfantId;
    }

    public Long getParrainId() {
        return parrainId;
    }

    public void setParrainId(Long parrainId) {
        this.parrainId = parrainId;
    }

    public Long getEnfantId() {
        return enfantId;
    }

    public void setEnfantId(Long enfantId) {
        this.enfantId = enfantId;
    }
}

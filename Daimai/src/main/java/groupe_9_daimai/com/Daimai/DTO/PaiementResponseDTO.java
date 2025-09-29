package groupe_9_daimai.com.Daimai.DTO;

import groupe_9_daimai.com.Daimai.Entite.enums.ModePaiement;
import groupe_9_daimai.com.Daimai.Entite.enums.StatutPaiement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaiementResponseDTO {
    private Long id;
    private Double montant;
    private LocalDate datePaiement;
    private ModePaiement modePaiement;
    private StatutPaiement statutPaiement;
    private Long parrainId;
    private String parrainNom;
    private String parrainPrenom;
    private String codeTransaction;
    private String messageStatut;

    // Constructeur pour JPQL "id + montant"
    public PaiementResponseDTO(Long id, Double montant) {
        this.id = id;
        this.montant = montant;
    }

    // Constructeur pour JPQL "id, montant, datePaiement, modePaiement, statutPaiement, parrainId, parrainNom, parrainPrenom"
    public PaiementResponseDTO(Long id, Double montant, LocalDate datePaiement,
                               ModePaiement modePaiement, StatutPaiement statutPaiement,
                               Long parrainId, String parrainNom, String parrainPrenom) {
        this.id = id;
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.modePaiement = modePaiement;
        this.statutPaiement = statutPaiement;
        this.parrainId = parrainId;
        this.parrainNom = parrainNom;
        this.parrainPrenom = parrainPrenom;
    }
}

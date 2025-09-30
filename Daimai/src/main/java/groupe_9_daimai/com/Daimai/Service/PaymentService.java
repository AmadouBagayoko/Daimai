package groupe_9_daimai.com.Daimai.Service;

import groupe_9_daimai.com.Daimai.Config.PaymentGatewayFactory;
import groupe_9_daimai.com.Daimai.DTO.PaiementReponseDTO;
import groupe_9_daimai.com.Daimai.DTO.PaiementRequestDTO;
import groupe_9_daimai.com.Daimai.Entite.Paiement;
import groupe_9_daimai.com.Daimai.Entite.enums.ModePaiement;
import groupe_9_daimai.com.Daimai.Entite.enums.StatutPaiement;
import groupe_9_daimai.com.Daimai.Repository.PaiementRepository;
import groupe_9_daimai.com.Daimai.Repository.PaymentGateWay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaiementRepository paiementRepository;
    @Autowired
    private PaymentGatewayFactory paymentGatewayFactory;

    public PaiementReponseDTO processPayment(PaiementRequestDTO requestDto) {
        // 1. Enregistrer le paiement en BDD avec un statut PENDING
        Paiement nouveauPaiement = new Paiement();
        nouveauPaiement.setMontant(requestDto.getMontant());
        nouveauPaiement.setDatePaiement(LocalDate.from(LocalDateTime.now()));
        nouveauPaiement.setModePaiement(ModePaiement.valueOf(requestDto.getModePaiemment().toUpperCase()));
        nouveauPaiement.setStatutPaiement(StatutPaiement.En_ATTENTE);
        nouveauPaiement.setClientReference(requestDto.getClientReference());
        paiementRepository.save(nouveauPaiement);

        // 2. Appeler l'API de paiement via la bonne passerelle
        PaymentGateWay gateway = paymentGatewayFactory.getGateway(requestDto.getModePaiemment());
        PaiementReponseDTO responseDto = gateway.processPayment(requestDto);

        // 3. Mettre à jour la transaction avec les informations de la passerelle
        if (responseDto.getTransactionId() != null) {
            nouveauPaiement.setTransactionId(responseDto.getTransactionId());
        }

        // Si la réponse est immédiate (carte bancaire), le statut est SUCCESS
        // Sinon (mobile money), il reste PENDING
        if ("SUCCESS".equalsIgnoreCase(responseDto.getStatus())) {
            nouveauPaiement.setStatutPaiement(StatutPaiement.REUSSI);
        }
        paiementRepository.save(nouveauPaiement);

        return responseDto;
    }

    public void updatePaymentStatus(String transactionId, String newStatus) {
        Paiement paiement = paiementRepository.findPaiementByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Paiement non trouvé avec l'ID de transaction : " + transactionId));

        if ("SUCCESS".equalsIgnoreCase(newStatus)) {
            paiement.setStatutPaiement(StatutPaiement.REUSSI);
        } else if ("FAILED".equalsIgnoreCase(newStatus)) {
            paiement.setStatutPaiement(StatutPaiement.ECHOUER);
        }
        paiementRepository.save(paiement);
    }
}

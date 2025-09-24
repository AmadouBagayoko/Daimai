package groupe_9_daimai.com.Daimai.Config;

import groupe_9_daimai.com.Daimai.Repository.PaymentGateWay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PaymentGatewayFactory {

    private final Map<String, PaymentGateWay> gateways;

    @Autowired
    public PaymentGatewayFactory(ApplicationContext context) {
        this.gateways = context.getBeansOfType(PaymentGateWay.class)
                .entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().toUpperCase(),
                        Map.Entry::getValue
                ));
    }

    public PaymentGateWay getGateway(String modePaiement) {
        PaymentGateWay gateway = gateways.get(modePaiement.toUpperCase());
        if (gateway == null) {
            throw new IllegalArgumentException("Méthode de paiement non supportée : " + modePaiement);
        }
        return gateway;
    }
}

package domain.payment.repository;

import domain.payment.entity.PaymentGatewayJpaEntity;
import org.springframework.stereotype.Repository;
import support.constants.payment.VendorCode;

import java.util.Optional;

@Repository
public interface PaymentGatewayRepository  {
    PaymentGatewayJpaEntity save(PaymentGatewayJpaEntity entity);
    Optional<PaymentGatewayJpaEntity> findByVendorCode(VendorCode vendorCode);
}
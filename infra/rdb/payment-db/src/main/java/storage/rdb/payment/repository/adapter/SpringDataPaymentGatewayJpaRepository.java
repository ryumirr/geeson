package storage.rdb.payment.repository.adapter;

import domain.payment.entity.PaymentGatewayJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import support.constants.payment.VendorCode;

import java.util.Optional;

public interface SpringDataPaymentGatewayJpaRepository extends JpaRepository<PaymentGatewayJpaEntity, Long> {
    Optional<PaymentGatewayJpaEntity> findByVendorCode(VendorCode vendorCode);
}
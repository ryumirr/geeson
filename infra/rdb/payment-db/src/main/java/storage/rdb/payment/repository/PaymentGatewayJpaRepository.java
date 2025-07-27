package storage.rdb.payment.repository;

import domain.payment.entity.PaymentGatewayJpaEntity;
import domain.payment.repository.PaymentGatewayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.payment.repository.adapter.SpringDataPaymentGatewayJpaRepository;
import support.constants.payment.VendorCode;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentGatewayJpaRepository implements PaymentGatewayRepository {
    private final SpringDataPaymentGatewayJpaRepository repository;

    @Override
    public PaymentGatewayJpaEntity save(PaymentGatewayJpaEntity entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<PaymentGatewayJpaEntity> findByVendorCode(VendorCode vendorCode) {
        return repository.findByVendorCode(vendorCode);
    }
}
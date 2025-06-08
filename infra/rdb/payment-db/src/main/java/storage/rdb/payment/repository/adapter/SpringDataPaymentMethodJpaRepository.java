package storage.rdb.payment.repository.adapter;

import domain.payment.entity.PaymentMethodJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataPaymentMethodJpaRepository extends JpaRepository<PaymentMethodJpaEntity, Long> {
    List<PaymentMethodJpaEntity> findAllByCustomerId(Long customerId);
}


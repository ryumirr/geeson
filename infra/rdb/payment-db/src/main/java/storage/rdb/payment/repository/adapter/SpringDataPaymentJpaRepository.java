package storage.rdb.payment.repository.adapter;

import domain.payment.entity.PaymentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpringDataPaymentJpaRepository extends JpaRepository<PaymentJpaEntity, Long> {
    Optional<PaymentJpaEntity> findByOrderId(Long orderId);
    
    @Query("SELECT p FROM PaymentJpaEntity p JOIN p.paymentMethod pm WHERE pm.customerId = :customerId")
    List<PaymentJpaEntity> findByCustomerId(@Param("customerId") Long customerId);
}
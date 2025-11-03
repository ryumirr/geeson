package app.payment.app;

import app.payment.command.PaymentMethodRegisterCommand;
import app.payment.exception.PaymentMethodNotFoundException;
import domain.payment.entity.PaymentMethodJpaEntity;
import domain.payment.repository.PaymentMethodRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import support.constants.payment.PgProviderCode;
import support.masking.CardMasker;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentMethodSelectApp {
    private final PaymentMethodRepository paymentMethodRepository;

    public List<PaymentMethodJpaEntity> getCustomerPaymentMethod(Long customerId) {
        return paymentMethodRepository.findAllByCustomerId(customerId);
    }

    public PaymentMethodJpaEntity getByPaymentMethodId(Long paymentMethodId) {
        return paymentMethodRepository.findByPaymentMethodId(paymentMethodId)
            .orElseThrow(() -> new PaymentMethodNotFoundException("payment method not found: " + paymentMethodId));
    }
}

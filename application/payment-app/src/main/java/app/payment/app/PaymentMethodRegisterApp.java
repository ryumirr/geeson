package app.payment.app;

import app.payment.command.PaymentMethodRegisterCommand;
import domain.payment.entity.PaymentMethodJpaEntity;
import domain.payment.repository.PaymentMethodRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import support.constants.payment.PgProviderCode;
import support.masking.CardMasker;
import support.uuid.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentMethodRegisterApp {
    private final PaymentMethodRepository paymentMethodRepository;
    private final UuidGenerator uuidGenerator;

    public PaymentMethodJpaEntity register(PaymentMethodRegisterCommand command) {
        return paymentMethodRepository.save(
            new PaymentMethodJpaEntity(
                uuidGenerator.nextId(),
                command.customerId(),
                "CARD",
                command.cardCode(),
                PgProviderCode.valueOf(command.provider()),
                CardMasker.mask(command.cardNumber()),
                command.expirationDate(),
                command.billingKey(),
                LocalDateTime.now()
            )
        );
    }
}

package app.order.app;

import app.order.command.CustomerRegisterCommand;
import domain.order.domain.entity.CustomerJpaEntity;
import app.order.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerRegisterApp {
    private final CustomerRepository customerRepository;

    public CustomerJpaEntity registerCustomer(CustomerRegisterCommand command) {
        return customerRepository.save(
            CustomerJpaEntity.builder()
                .name(command.name())
                .email(command.email())
                .phone(command.phone())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()
        );
    }
}

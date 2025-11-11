package app.order.app;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import domain.order.entity.ProductOrderJpaEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import domain.order.repository.ProductOrderRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderUpdateApp {
    private final ProductOrderRepository orderRepository;

    @Transactional
    public void updateOrderStatus(Long orderId) {
        ProductOrderJpaEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        order.changeStatus("ORDERED");

        log.info("Order {} status changed to {}", orderId, "ORDERED");
    }
}

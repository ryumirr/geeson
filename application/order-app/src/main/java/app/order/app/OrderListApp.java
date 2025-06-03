package app.order.app;

import app.order.repository.ProductOrderRepository;
import domain.order.domain.entity.ProductOrderJpaEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderListApp {
    private final ProductOrderRepository productOrderRepository;

    public List<ProductOrderJpaEntity> getAllOrders(int page, int size) {
        return productOrderRepository.getAllOrders(page, size);
    }
}

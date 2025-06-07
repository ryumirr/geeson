package app.order.app;

import domain.order.entity.ProductOrderJpaEntity;
import domain.order.repository.ProductOrderRepository;
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

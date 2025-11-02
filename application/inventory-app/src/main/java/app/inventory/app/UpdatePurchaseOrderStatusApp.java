package app.inventory.app;

import app.inventory.port.in.UpdatePurchaseOrderStatusUseCase;
import domain.inventory.domain.entity.PurchaseOrderJpaEntity;
import domain.inventory.domain.repository.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * 발주 상태 변경 유즈케이스 구현체 (DDD 스타일)
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UpdatePurchaseOrderStatusApp implements UpdatePurchaseOrderStatusUseCase {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public UpdatePurchaseOrderStatusResult handle(UpdatePurchaseOrderStatusCommand command) {
        // 1️⃣ 엔티티 조회
        PurchaseOrderJpaEntity entity = purchaseOrderRepository.findById(command.purchaseOrderId())
                .orElseThrow(() ->
                        new IllegalArgumentException("PurchaseOrder not found: " + command.purchaseOrderId()));

        entity.updateStatus(command.status());

        purchaseOrderRepository.save(entity);

        return new UpdatePurchaseOrderStatusResult(
                true,
                entity.getPurchaseOrderId(),
                entity.getStatus(),
                LocalDateTime.now().format(formatter)
        );
    }
}

package app.inventory.app;

import app.inventory.command.InventoryReservationCommand;
import domain.inventory.domain.entity.InventoryJpaEntity;
import domain.inventory.domain.entity.InventoryReservationJpaEntity;
import domain.inventory.domain.repository.InventoryRepository;
import domain.inventory.domain.repository.InventoryReservationRepository;
import domain.inventory.exception.NotEnoughInventoryException;
import lombok.RequiredArgsConstructor;
import module.enums.ReservationStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryReservationApp {

    private final InventoryRepository inventoryRepository;
    private final InventoryReservationRepository reservationRepository;

    /**
     * 재고 예약 생성
     */
    public InventoryReservationJpaEntity reserveInventory(InventoryReservationCommand command) {
        InventoryJpaEntity inventory = inventoryRepository.findById(command.inventoryId())
                .orElseThrow(() -> new NotEnoughInventoryException("Inventory not found: " + command.inventoryId()));

        if (!inventory.canReserve(command.reservedQuantity())) {
            throw new NotEnoughInventoryException("Not enough inventory for ID: " + command.inventoryId());
        }

        // 수량 차감
        inventory.reserve(command.reservedQuantity());

        // TTL(초 단위) 기반 expiresAt 계산
        LocalDateTime expiresAt = (command.ttlSeconds() != null)
            ? LocalDateTime.now().plusSeconds(command.ttlSeconds())
            : null;

        // 예약 엔티티 저장
        return reservationRepository.save(
                InventoryReservationJpaEntity.create(
                        command.reservationId(),
                        inventory,
                        command.orderId(),
                        command.reservedQuantity(),
                        expiresAt));
    }

    /**
     * 예약 단건 조회
     */
    public InventoryReservationJpaEntity getById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found: " + id));
    }

    /**
     * 특정 재고 ID의 예약 목록 조회
     */
    public List<InventoryReservationJpaEntity> getByInventoryId(Long inventoryId) {
        return reservationRepository.findByInventory_InventoryId(inventoryId);
    }

    /**
     * 예약 상태 변경
     */
    public void updateStatus(Long id, ReservationStatus status) {
        InventoryReservationJpaEntity entity = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found: " + id));
        entity.changeStatus(status);
    }

    /**
     * 특정 주문 ID의 예약 전체 조회
     */
    public List<InventoryReservationJpaEntity> getByOrderId(Long orderId) {
        return reservationRepository.findByOrderId(orderId);
    }
}

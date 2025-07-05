package app.inventory.app;

import app.inventory.command.InventoryReservationCommand;
import domain.inventory.domain.entity.InventoryReservationJpaEntity;
import domain.inventory.domain.repository.InventoryReservationRepository;
import lombok.RequiredArgsConstructor;
import module.enums.ReservationStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryReservationApp {

    private final InventoryReservationRepository reservationRepository;

    /**
     * 재고 예약 생성
     */
    public InventoryReservationJpaEntity reserveInventory(InventoryReservationCommand command) {
        Timestamp expires = command.expiresAt() != null ? Timestamp.valueOf(command.expiresAt()) : null;

        InventoryReservationJpaEntity entity = InventoryReservationJpaEntity.create(
            command.inventoryId(),
            command.orderId(),
            command.reservedQuantity(),
            expires
        );
        return reservationRepository.save(entity);
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
}
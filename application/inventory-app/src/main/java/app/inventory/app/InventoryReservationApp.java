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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryReservationApp {

    private final InventoryReservationRepository reservationRepository;
    private final InventoryRepository inventoryRepository;
    private final InventoryReservationRepository inventoryReservationRepository;

    /**
     * 재고 예약 생성
     */
    public InventoryReservationJpaEntity reserveInventory(InventoryReservationCommand command) {
        List<InventoryJpaEntity> inventories = inventoryRepository.findByProductId(command.productId());

        if(inventories.isEmpty()) throw new NotEnoughInventoryException("Not Enough Inventory product : " + command.productId());

        // 예: 가장 재고가 양이 많은 warehouse
        InventoryJpaEntity selectedInventory = inventories.stream()
            .filter(inv -> inv.canReserve(command.reservedQuantity()))
            .max(Comparator.comparing(inv -> inv.getWareHouse().getCapacity()))
            .orElseThrow(() -> new NotEnoughInventoryException("Not Enough Inventory product : " + command.productId()));

        selectedInventory.reserve(command.reservedQuantity());

        return inventoryReservationRepository.save(InventoryReservationJpaEntity.create(
            command.reservationId(),
            selectedInventory,
            command.orderId(),
            command.reservedQuantity(),
            LocalDateTime.now().plusDays(7)
        ));
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
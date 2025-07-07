package api.inventory.controller;

import app.inventory.app.InventoryReservationApp;
import app.inventory.command.InventoryReservationCommand;
import api.inventory.request.InventoryReservationReq;
import api.inventory.request.UpdateReservationStatusReq;
import api.inventory.response.InventoryReservationRes;
import domain.inventory.domain.entity.InventoryReservationJpaEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import support.uuid.UuidGenerator;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-reservations")
@RequiredArgsConstructor
public class InventoryReservationApi {

    private final InventoryReservationApp reservationApp;
    private final UuidGenerator uuidGenerator;

    /**
     * 재고 예약 생성
     */
    @PostMapping
    public ResponseEntity<InventoryReservationRes> reserve(@RequestBody @Valid InventoryReservationReq req) {
        InventoryReservationJpaEntity entity = reservationApp.reserveInventory(new InventoryReservationCommand(
            uuidGenerator.nextId(),
            req.productId(),
            req.orderId(),
            req.reservedQuantity()
        ));

        InventoryReservationRes res = new InventoryReservationRes(
            entity.getReservationId(),
            entity.getInventory().getInventoryId(),
            entity.getOrderId(),
            entity.getReservedQuantity(),
            entity.getReservedAt(),
            entity.getExpiresAt(),
            entity.getStatus().name()
        );

        return ResponseEntity.status(201).body(res);
    }

    /**
     * 예약 단건 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<InventoryReservationRes> getById(@PathVariable Long id) {
        InventoryReservationJpaEntity entity = reservationApp.getById(id);
        return ResponseEntity.ok(new InventoryReservationRes(
            entity.getReservationId(),
            entity.getInventory().getInventoryId(),
            entity.getOrderId(),
            entity.getReservedQuantity(),
            entity.getReservedAt(),
            entity.getExpiresAt(),
            entity.getStatus().name()
        ));
    }

    /**
     * 특정 재고의 예약 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<InventoryReservationRes>> getByInventoryId(@RequestParam Long inventoryId) {
        List<InventoryReservationRes> result = reservationApp.getByInventoryId(inventoryId).stream()
            .map(entity -> new InventoryReservationRes(
                entity.getReservationId(),
                entity.getInventory().getInventoryId(),
                entity.getOrderId(),
                entity.getReservedQuantity(),
                entity.getReservedAt(),
                entity.getExpiresAt(),
                entity.getStatus().name()
            )).toList();

        return ResponseEntity.ok(result);
    }

    /**
     * 예약 상태 변경
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
        @PathVariable Long id,
        @RequestBody @Valid UpdateReservationStatusReq req
    ) {
        reservationApp.updateStatus(id, req.status());
        return ResponseEntity.noContent().build();
    }
}
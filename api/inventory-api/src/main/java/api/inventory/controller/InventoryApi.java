package api.inventory.controller;

import app.inventory.app.InventoryAddApp;
import app.inventory.app.InventorySelectApp;
import api.inventory.request.AddInventoryReq;
import api.inventory.request.SelectInventoryReq;
import api.inventory.response.AddInventoryRes;
import api.inventory.response.SelectInventoryRes;
import domain.inventory.domain.entity.InventoryJpaEntity;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;
import grpc.shipment.GetShipmentResponse;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryApi {

    private final InventoryAddApp inventoryAddApp;
    private final InventorySelectApp inventorySelectApp;

    /**
     * Add new inventory for a product in a warehouse
     */
    @PostMapping
    public ResponseEntity<AddInventoryRes> addInventory(@RequestBody @Valid AddInventoryReq req) {
        InventoryJpaEntity entity = inventoryAddApp.addInventory(
                req.productId(),
                req.warehouseId(),
                req.totalQuantity(),
                req.reorderLevel(),
                req.reorderQuantity());

        AddInventoryRes res = AddInventoryRes.from(entity);
        return ResponseEntity.status(201).body(res);
    }

    /**
     * Find available inventory for a product with sufficient quantity
     */
    @GetMapping("/available")
    public ResponseEntity<Object> selectInventory(
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") Integer quantity) {
        InventoryJpaEntity inventory = inventorySelectApp.findAvailableInventory(
                productId,
                quantity);
        return ResponseEntity.ok(new SelectInventoryRes(
                inventory.getInventoryId(),
                inventory.getProduct().getProductId(),
                inventory.getWareHouse().getWarehouseId(),
                inventory.getTotalQuantity(),
                inventory.getReservedQuantity(),
                inventory.getReorderLevel(),
                inventory.getReorderQuantity(),
                inventory.getCreatedAt(),
                inventory.getUpdatedAt()));
    }

    /**
     * Test grpc-order
     */
    @GetMapping("/test-shipment")
    public ResponseEntity<Object> testSelectShipment(
            @RequestParam("shipmentId") Long shipmentId) {

        try {
            GetShipmentResponse shipment = inventorySelectApp.getShipment(shipmentId);
            return ResponseEntity.status(201).body(new TestShipmentRes(shipment));
        } catch (Exception e) {
            e.printStackTrace(); // optional: use logger
            return ResponseEntity.status(400)
                    .body("Error fetching shipment");
        }
    }
  
    // @Todo delete this test endpoint after verifying grpc connection
    // Test response class for Shipment
    private static class TestShipmentRes {
        private Long shipmentId;
        private String status;

        public TestShipmentRes(GetShipmentResponse proto) {
            this.shipmentId = proto.getShipmentId();
            this.status = proto.getStatus();
        }

        public Long getShipmentId() {
            return shipmentId;
        }

        public String getStatus() {
            return status;
        }
    }
}

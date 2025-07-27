package api.inventory.controller;

import app.inventory.app.InventoryAddApp;
import app.inventory.app.InventorySelectApp;
import api.inventory.request.AddInventoryReq;
import api.inventory.request.SelectInventoryReq;
import api.inventory.response.AddInventoryRes;
import api.inventory.response.SelectInventoryRes;
import domain.inventory.domain.entity.InventoryJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

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
            req.reorderQuantity()
        );

        AddInventoryRes res = AddInventoryRes.from(entity);
        return ResponseEntity.status(201).body(res);
    }
    
    /**
     * Find available inventory for a product with sufficient quantity
     */
    @GetMapping("/available")
    public ResponseEntity<SelectInventoryRes> selectInventory(
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") Integer quantity) {
        InventoryJpaEntity inventory = inventorySelectApp.findAvailableInventory(
            productId,
            quantity
        );
        
        return ResponseEntity.ok(new SelectInventoryRes(
            inventory.getInventoryId(),
            inventory.getProduct().getProductId(),
            inventory.getWareHouse().getWarehouseId(),
            inventory.getTotalQuantity(),
            inventory.getReservedQuantity(),
            inventory.getReorderLevel(),
            inventory.getReorderQuantity(),
            inventory.getCreatedAt(),
            inventory.getUpdatedAt()
        ));
    }
}
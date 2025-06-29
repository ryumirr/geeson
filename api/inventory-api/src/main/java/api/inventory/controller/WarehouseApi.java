package api.inventory.controller;

import app.inventory.app.WarehouseRegisterApp;
import app.inventory.app.WarehouseListApp;
import app.inventory.app.WarehouseDeleteApp;
import jakarta.validation.Valid;
import domain.inventory.domain.entity.WarehouseJpaEntity;
import api.inventory.request.RegisterWarehousesReq;
import api.inventory.response.RegisterWarehouseRes;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseApi {

    private final WarehouseRegisterApp warehouseRegisterApp;
    private final WarehouseListApp warehouseListApp;
    private final WarehouseDeleteApp warehouseDeleteApp;

    @PostMapping
    public RegisterWarehouseRes register(@RequestBody @Valid RegisterWarehousesReq req) {
        WarehouseJpaEntity warehouse = warehouseRegisterApp.register(req.toOneEntity());
        return RegisterWarehouseRes.from(warehouse);
    }

    @GetMapping
    public List<RegisterWarehouseRes> list() {
        return warehouseListApp.findAll().stream()
                .map(RegisterWarehouseRes::from)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!warehouseListApp.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found");
        }
        warehouseDeleteApp.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
package api.inventory.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import api.inventory.request.RegisterInventoryItemReq;
import api.inventory.response.RegisterInventoryItemRes;
import app.inventory.port.in.CreateInventoryItemUseCase;
import app.inventory.port.in.GetInventoryItemUseCase;
import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/inventory-items")
@RequiredArgsConstructor
public class InventoryItemsApi {

        private final CreateInventoryItemUseCase createInventoryItemUseCase;
        private final GetInventoryItemUseCase getInventoryItemUseCase;

        @PostMapping
        public ResponseEntity<RegisterInventoryItemRes> registerInventoryItem(
                        @RequestBody @Valid RegisterInventoryItemReq req) {

                var result = createInventoryItemUseCase.handle(
                                new CreateInventoryItemUseCase.CreateInventoryItemCommand(
                                                req.inventoryId(),
                                                req.serialNumber(),
                                                req.status()));

                return ResponseEntity.status(HttpStatus.CREATED).body(RegisterInventoryItemRes.from(result));
        }

        @GetMapping("/{id}")
        public ResponseEntity<RegisterInventoryItemRes> findById(@PathVariable Long id) {
                var result = getInventoryItemUseCase.getById(
                                new GetInventoryItemUseCase.GetInventoryItemCommand(id));
                return ResponseEntity.ok(RegisterInventoryItemRes.from(result));
        }

        @GetMapping("/by-serial")
        public ResponseEntity<RegisterInventoryItemRes> findBySerialNumber(
                        @RequestParam("serialNumber") String serialNumber) {

                var result = getInventoryItemUseCase.getBySerialNumber(
                                new GetInventoryItemUseCase.GetInventoryItemBySerialCommand(serialNumber));

                return ResponseEntity.ok(RegisterInventoryItemRes.from(result));
        }

}

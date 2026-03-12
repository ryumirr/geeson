package api.inventory.controller;

import app.inventory.app.CreateWarehouseApp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import api.inventory.request.RegisterWarehousesReq;

@RequiredArgsConstructor
@RestController
@RequestMapping("/warehouses")
public class WarehouseApi {

    private final CreateWarehouseApp createWarehouseApp;

    @PostMapping
    public Object createWarehouse(@RequestBody RegisterWarehousesReq dto) {
        var result = createWarehouseApp.handle(
                new CreateWarehouseApp.CreateWarehouseCommand(
                        dto.name(),
                        dto.location(),
                        dto.capacity()
                )
        );
        return result;
    }
}

package api.product.controller;

import api.product.dto.BrandRegisterRequest;
import api.product.dto.BrandRegisterResponse;
import app.product.app.BrandRegisterApp;
import app.product.command.BrandRegisterCommand;
import domain.product.domain.entity.BrandJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandApi {

    private final BrandRegisterApp brandRegisterApp;

    @PostMapping
    public ResponseEntity<BrandRegisterResponse> registerBrand(@RequestBody BrandRegisterRequest request) {
        // Convert request to command
        BrandRegisterCommand command = BrandRegisterCommand.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        // Register brand
        BrandJpaEntity brand = brandRegisterApp.registerBrand(command);

        // Return response
        return ResponseEntity.ok(new BrandRegisterResponse(
            brand.getBrandId(),
            brand.getName(),
            brand.getDescription(),
            "brand registered successfully"
        ));
    }
}
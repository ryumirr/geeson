package api.product.controller;

import api.product.dto.ProductRegisterRequest;
import api.product.dto.ProductRegisterResponse;
import api.product.dto.ProductSelectResponse;
import app.product.app.ProductRegisterApp;
import app.product.app.ProductSelectApp;
import app.product.command.ProductRegisterCommand;
import domain.product.domain.entity.ProductJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductApi {

    private final ProductRegisterApp productRegisterApp;
    private final ProductSelectApp productSelectApp;

    @PostMapping
    public ResponseEntity<ProductRegisterResponse> registerProduct(@RequestBody ProductRegisterRequest request) {
        // Convert request to command
        ProductRegisterCommand command = ProductRegisterCommand.builder()
                .name(request.getName())
                .sku(request.getSku())
                .brandId(request.getBrandId())
                .categoryId(request.getCategoryId())
                .price(request.getPrice())
                .discountPrice(request.getDiscountPrice())
                .currency(request.getCurrency())
                .build();

        // Register product
        ProductJpaEntity product = productRegisterApp.registerProduct(command);

        // Return response
        return ResponseEntity.ok(new ProductRegisterResponse(
            product.getProductId(),
            product.getName(),
            product.getSku(),
            product.getBrand().getBrandId().toString(),
            product.getIsActive(),
            product.getProductCategoryMap().stream().map(v -> new ProductRegisterResponse.CategoryInfo(
                v.getCategory().getCategoryId(),
                    v.getCategory().getName(),
                v.getCategory().getDescription()
            )).toList(),
            "product registered successfully"
        ));
    }
    
    @GetMapping("/{productId}")
    public ResponseEntity<ProductSelectResponse> selectProduct(@PathVariable Long productId) {
        // Select product
        ProductSelectApp.ProductWithPrice productWithPrice = productSelectApp.selectProduct(productId);
        
        // Extract product and price information
        ProductJpaEntity product = productWithPrice.product();
        var price = productWithPrice.price();
        var categories = productWithPrice.categories();
        
        // Map categories to CategoryInfo objects
        List<api.product.dto.ProductSelectResponse.CategoryInfo> categoryInfos = categories.stream()
            .map(category -> new api.product.dto.ProductSelectResponse.CategoryInfo(
                category.getCategoryId(),
                category.getName(),
                category.getDescription()
            ))
            .collect(Collectors.toList());
        
        // Return response
        return ResponseEntity.ok(new ProductSelectResponse(
            product.getProductId(),
            product.getName(),
            product.getSku(),
            product.getBrand().getBrandId().toString(),
            product.getBrand().getName(),
            product.getIsActive(),
            price.getPrice(),
            price.getDiscountPrice(),
            price.getCurrency(),
            categoryInfos,
            "product retrieved successfully"
        ));
    }
}

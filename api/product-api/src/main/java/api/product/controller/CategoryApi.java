package api.product.controller;

import api.product.dto.CategoryListResponse;
import api.product.dto.CategoryRegisterRequest;
import api.product.dto.CategoryRegisterResponse;
import app.product.app.CategoryRegisterApp;
import app.product.command.CategoryRegisterCommand;
import domain.product.domain.entity.ProductCategoryJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryApi {

    private final CategoryRegisterApp categoryRegisterApp;

    @PostMapping
    public ResponseEntity<CategoryRegisterResponse> registerCategory(@RequestBody CategoryRegisterRequest request) {
        // Convert request to command
        CategoryRegisterCommand command = CategoryRegisterCommand.builder()
                .name(request.getName())
                .description(request.getDescription())
                .parentId(request.getParentId())
                .build();

        // Register category
        ProductCategoryJpaEntity category = categoryRegisterApp.registerCategory(command);

        // Return response
        return ResponseEntity.ok(new CategoryRegisterResponse(
                category.getCategoryId(),
                category.getName(),
                category.getDescription(),
                category.getParentId(),
                category.getIsActive(),
                "category registered successfully"
        ));
    }
    
    /**
     * Retrieves all product categories
     * @return List of all product categories
     */
    @GetMapping
    public ResponseEntity<CategoryListResponse> getAllCategories() {
        // Get all categories from service
        List<ProductCategoryJpaEntity> categories = categoryRegisterApp.getAllCategories();
        
        // Convert entities to DTOs
        List<CategoryListResponse.CategoryItem> categoryItems = categories.stream()
                .map(category -> new CategoryListResponse.CategoryItem(
                        category.getCategoryId(),
                        category.getName(),
                        category.getDescription(),
                        category.getParentId(),
                        category.getDepth(),
                        category.getIsActive()
                ))
                .collect(Collectors.toList());
        
        // Return response
        return ResponseEntity.ok(new CategoryListResponse(
                categoryItems,
                "categories retrieved successfully"
        ));
    }
}
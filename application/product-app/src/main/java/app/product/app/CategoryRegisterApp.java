package app.product.app;

import app.product.command.CategoryRegisterCommand;
import app.product.exception.DuplicateCategoryException;
import domain.product.domain.entity.ProductCategoryJpaEntity;
import domain.product.domain.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryRegisterApp {
    
    private final ProductCategoryRepository productCategoryRepository;
    
    @Transactional
    public ProductCategoryJpaEntity registerCategory(CategoryRegisterCommand command) {
        // Check if category with the same name already exists
        if (productCategoryRepository.findByName(command.getName()).isPresent()) {
            throw new DuplicateCategoryException("Category with name " + command.getName() + " already exists");
        }
        
        // Determine depth based on parentId
        Integer depth = 1; // Default depth for top-level categories
        
        // If parentId is provided, check if parent exists and get its depth
        if (command.getParentId() != null) {
            ProductCategoryJpaEntity parentCategory = productCategoryRepository.findById(command.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent category with ID " + command.getParentId() + " not found"));
            depth = parentCategory.getDepth() + 1;
        }
        
        // Create category entity
        ProductCategoryJpaEntity category = ProductCategoryJpaEntity.builder()
                .name(command.getName())
                .description(command.getDescription())
                .parentId(command.getParentId())
                .depth(depth)
                .isActive(true)
                .build();
        
        // Save category
        return productCategoryRepository.save(category);
    }

    /**
     * Retrieves all product categories
     * @return List of all product categories
     */
    @Transactional(readOnly = true)
    public List<ProductCategoryJpaEntity> getAllCategories() {
        return productCategoryRepository.findAll();
    }
}
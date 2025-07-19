package app.product.app;

import app.product.exception.NoSuchProductException;
import domain.product.domain.entity.ProductCategoryJpaEntity;
import domain.product.domain.entity.ProductCategoryMapJpaEntity;
import domain.product.domain.entity.ProductJpaEntity;
import domain.product.domain.entity.ProductPriceJpaEntity;
import domain.product.domain.repository.ProductCategoryMapRepository;
import domain.product.domain.repository.ProductPriceRepository;
import domain.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductSelectApp {
    
    private final ProductRepository productRepository;
    private final ProductPriceRepository productPriceRepository;
    private final ProductCategoryMapRepository productCategoryMapRepository;

    public ProductWithPrice selectProduct(Long productId) {
        // Find product by ID
        ProductJpaEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchProductException("Product with ID " + productId + " not found"));
        
        // Find product price by ID (same ID as product)
        ProductPriceJpaEntity productPrice = productPriceRepository.findById(productId)
                .orElseThrow(() -> new NoSuchProductException("Price information for product with ID " + productId + " not found"));
        
        // Find product categories
        List<ProductCategoryMapJpaEntity> categoryMaps = productCategoryMapRepository.findByProductId(productId);
        List<ProductCategoryJpaEntity> categories = categoryMaps.stream()
                .map(ProductCategoryMapJpaEntity::getCategory)
                .collect(Collectors.toList());
        
        // Return product with price and category information
        return new ProductWithPrice(product, productPrice, categories);
    }
    
    // Inner class to hold product, price, and category information
    public record ProductWithPrice(
            ProductJpaEntity product, 
            ProductPriceJpaEntity price,
            List<ProductCategoryJpaEntity> categories
    ) {}
}
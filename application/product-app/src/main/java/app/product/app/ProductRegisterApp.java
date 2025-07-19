package app.product.app;

import app.product.command.ProductRegisterCommand;
import app.product.exception.NoSuchBrandException;
import app.product.exception.NoSuchCategoryException;
import app.product.exception.NoSuchProductException;
import domain.product.domain.entity.BrandJpaEntity;
import domain.product.domain.entity.ProductCategoryJpaEntity;
import domain.product.domain.entity.ProductCategoryMapJpaEntity;
import domain.product.domain.entity.ProductJpaEntity;
import domain.product.domain.entity.ProductPriceJpaEntity;
import domain.product.domain.repository.BrandRepository;
import domain.product.domain.repository.ProductCategoryMapRepository;
import domain.product.domain.repository.ProductCategoryRepository;
import domain.product.domain.repository.ProductPriceRepository;
import domain.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import support.uuid.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductRegisterApp {
    
    private final ProductRepository productRepository;
    private final ProductPriceRepository productPriceRepository;
    private final BrandRepository brandRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryMapRepository productCategoryMapRepository;
    private final UuidGenerator uuidGenerator;
    
    @Transactional
    public ProductJpaEntity registerProduct(ProductRegisterCommand command) {
        // Check if product with the same SKU already exists
        if (productRepository.findBySku(command.getSku()) != null) {
            throw new NoSuchProductException("Product with SKU " + command.getSku() + " already exists");
        }
        
        // Check if brand exists
        BrandJpaEntity brand = brandRepository.findById(command.getBrandId())
                .orElseThrow(() -> new NoSuchBrandException("Brand with ID " + command.getBrandId() + " not found"));
        
        // Create product entity
        ProductJpaEntity product = ProductJpaEntity.builder()
            .productId(uuidGenerator.nextId())
            .name(command.getName())
            .sku(command.getSku())
            .brand(brand)
            .isActive(true)
            .build();
        
        // Save product
        ProductJpaEntity savedProduct = productRepository.save(product);
        
        // Create product price entity
        ProductPriceJpaEntity productPrice = ProductPriceJpaEntity.builder()
            .product(savedProduct)
            .price(command.getPrice())
            .discountPrice(command.getDiscountPrice())
            .currency(command.getCurrency())
            .build();
        
        // Save product price
        productPriceRepository.save(productPrice);

        for(Long categoryId : command.getCategoryId()) {
            ProductCategoryJpaEntity productCategory = productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchCategoryException("Category with ID " + categoryId + " not found"));

            ProductCategoryMapJpaEntity save = productCategoryMapRepository.save(new ProductCategoryMapJpaEntity(savedProduct, productCategory));
            savedProduct.getProductCategoryMap().add(save);
        }
        
        return savedProduct;
    }
}

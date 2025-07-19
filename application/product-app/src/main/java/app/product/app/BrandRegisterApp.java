package app.product.app;

import app.product.command.BrandRegisterCommand;
import domain.product.domain.entity.BrandJpaEntity;
import domain.product.domain.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BrandRegisterApp {
    
    private final BrandRepository brandRepository;
    
    @Transactional
    public BrandJpaEntity registerBrand(BrandRegisterCommand command) {
        // Create brand entity
        BrandJpaEntity brand = new BrandJpaEntity();
        brand.setName(command.getName());
        brand.setDescription(command.getDescription());
        
        // Save brand
        return brandRepository.save(brand);
    }
}
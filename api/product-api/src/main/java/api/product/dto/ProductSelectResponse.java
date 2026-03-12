package api.product.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductSelectResponse(
    Long productId,
    String name,
    String sku,
    String brandId,
    String brandName,
    Boolean isActive,
    BigDecimal price,
    BigDecimal discountPrice,
    String currency,
    List<CategoryInfo> categories,
    String message
) {
    public record CategoryInfo(
        Long categoryId,
        String name,
        String description
    ) {}
}
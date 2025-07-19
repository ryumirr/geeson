package api.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductRegisterRequest {
    private String name;
    private String sku;
    private Long brandId;
    private List<Long> categoryId;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private String currency;
}
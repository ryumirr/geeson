package app.product.command;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class ProductRegisterCommand {
    private final String name;
    private final String sku;
    private final Long brandId;
    private final List<Long> categoryId;
    private final BigDecimal price;
    private final BigDecimal discountPrice;
    private final String currency;
}

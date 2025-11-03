package api.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRegisterResponse {
    private Long productId;
    private String name;
    private String sku;
    private String brand;
    private Boolean isActive;
    private List<CategoryInfo> category;
    private String message;

    public record CategoryInfo(
        Long categoryId,
        String name,
        String description
    ) {}
}
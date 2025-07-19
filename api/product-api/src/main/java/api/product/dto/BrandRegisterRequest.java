package api.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandRegisterRequest {
    private String name;
    private String description;
}
package api.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRegisterResponse {
    private Long categoryId;
    private String name;
    private String description;
    private Long parentId;
    private Boolean isActive;
    private String message;
}
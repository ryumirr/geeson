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
public class CategoryListResponse {
    private List<CategoryItem> categories;
    private String message;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryItem {
        private Long categoryId;
        private String name;
        private String description;
        private Long parentId;
        private Integer depth;
        private Boolean isActive;
    }
}
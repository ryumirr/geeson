package app.product.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryRegisterCommand {
    private final String name;
    private final String description;
    private final Long parentId;
}
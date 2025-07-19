package app.product.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BrandRegisterCommand {
    private final String name;
    private final String description;
}
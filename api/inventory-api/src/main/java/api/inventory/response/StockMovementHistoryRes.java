package api.inventory.response;

public record StockMovementHistoryRes(
    String movementType,
    Integer quantity,
    String description
) {
public static StockMovementHistoryRes from(String typeName, Integer quantity, String description) {
    return new StockMovementHistoryRes(
        typeName,
        quantity,
        description
    );
}

}
package app.inventory.port.in;

import app.inventory.dto.AddStockMovementCommand;
import app.inventory.dto.StockMovementResult;

public interface AddStockMovementUseCase {
    StockMovementResult add(AddStockMovementCommand command);
}

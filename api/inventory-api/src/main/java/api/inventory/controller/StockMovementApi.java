package api.inventory.controller;

import app.inventory.app.StockMovementOutApp;
import domain.inventory.domain.entity.StockMovementJpaEntity;
import api.inventory.request.StockMovementOutReq;
import api.inventory.response.StockMovementOutRes;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stock-movements")
public class StockMovementApi {

    private final StockMovementOutApp stockMovementOutApp;

    /**
     * 출고 처리
     * 
     * @param req
     * @return ResponseEntity<StockMovementOutRes>
     */
    @PostMapping("/out")
    public ResponseEntity<StockMovementOutRes> stockOut(@RequestBody StockMovementOutReq req) {
        StockMovementJpaEntity entity = stockMovementOutApp.registerStockOut(
                req.inventoryId(),
                req.quantity(),
                req.description(),
                req.referenceId());
        return ResponseEntity
                .status(200)
                .body(StockMovementOutRes.from(entity));
    }

}

package api.inventory.controller;

import app.inventory.app.StockMovementInApp;
import app.inventory.app.StockMovementOutApp;
import domain.inventory.domain.entity.StockMovementJpaEntity;
import api.inventory.request.StockMovementInReq;
import api.inventory.response.StockMovementInRes;
import api.inventory.request.StockMovementOutReq;
import api.inventory.response.StockMovementOutRes;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stock-movements")
public class StockMovementApi {

    private final StockMovementInApp stockMovementInApp;
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
                req.description());
        return ResponseEntity
                .status(200)
                .body(StockMovementOutRes.from(entity));
    }

    /**
     * 입고 처리
     * 
     * @param req
     * @return ResponseEntity<StockMovementInRes>
     */
    @PostMapping("/in")
    public ResponseEntity<StockMovementInRes> stockIn(@RequestBody StockMovementInReq req) {
        StockMovementJpaEntity saved = stockMovementInApp.registerStockIn(
                req.inventoryId(),
                req.quantity(),
                req.description());
        return ResponseEntity
                .status(200)
                .body(StockMovementInRes.from(saved));
    }
}
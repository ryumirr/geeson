package api.order.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.order.request.UpdateShipmentStatusReq;
import app.order.app.ShipmentApp;
import app.order.command.ShipmentCommand;
import domain.order.entity.ShipmentJpaEntity;
import storage.rdb.order.repository.ShipmentJpaRepository;
import api.order.request.ShipmentReq;
import api.order.response.ShipmentRes;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 배송 관련 API
 * 1. 배송 생성
 * 2. 배송 단건 조회
 * 3. 주문별 배송 조회
 * 4. 배송 상태 변경
 * 5. 배송 목록 조회
 * 6. 운송장 번호 기반 조회
 */
@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentApi {

    private final ShipmentApp shipmentApp;

    /**
     * 배송 생성
     * @param req
     * @return ResponseEntity<ShipmentRes>
     */
    @PostMapping
    public ResponseEntity<ShipmentRes> create(@RequestBody @Valid ShipmentReq req) {
        // @todo Kafka 이벤트 발행
        ShipmentJpaEntity resulted = shipmentApp.createShipment(
            new ShipmentCommand(req.orderId(), req.carrier(), req.trackingNumber()));
        return ResponseEntity.status(201).body(ShipmentRes.from(resulted));
    }

    /**
     * 배송 단건 조회
     * @param id
     * @return ResponseEntity<ShipmentRes>
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShipmentRes> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ShipmentRes.from(shipmentApp.getShipmentById(id)));
    }

    /**
     * 주문별 배송 조회
     * @param orderId
     * @return  ResponseEntity<List<ShipmentRes>>
     */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<ShipmentRes>> getByOrderId(@PathVariable Long orderId) {
        List<ShipmentJpaEntity> entities = shipmentApp.getShipmentsByOrderId(orderId);
        return ResponseEntity.ok(entities.stream().map(ShipmentRes::from).toList());
    }

    /**
     * 배송 상태 변경
     * @param id
     * @param req
     * @return  ResponseEntity<Void>
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestBody UpdateShipmentStatusReq req) {
        // @todo Kafka 이벤트 발행
        shipmentApp.updateStatus(id, req.status());
        return ResponseEntity.noContent().build();
    }

    /**
     * 배송 목록 조회
     * @return  ResponseEntity<List<ShipmentRes>>
     */
    @GetMapping
    public ResponseEntity<List<ShipmentRes>> listAll() {
        List<ShipmentJpaEntity> entities = shipmentApp.listAll();
        return ResponseEntity.ok(entities.stream().map(ShipmentRes::from).toList());
    }

    /**
     * 운송장 번호 기반 조회
     */
    @GetMapping("/track/{trackingNumber}")
    public ResponseEntity<ShipmentRes> getByTrackingNumber(@PathVariable String trackingNumber) {
        Optional<ShipmentJpaEntity> entity = shipmentApp.getByTrackingNumber(trackingNumber);
        return ResponseEntity.ok(ShipmentRes.from(entity));
    }
}
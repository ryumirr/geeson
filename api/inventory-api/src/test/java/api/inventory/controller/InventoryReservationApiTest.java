package api.inventory.controller;

import app.inventory.app.InventoryReservationApp;
import api.inventory.request.InventoryReservationReq;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.inventory.domain.entity.InventoryJpaEntity;
import domain.inventory.domain.entity.InventoryReservationJpaEntity;
import module.enums.ReservationStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import support.uuid.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventoryReservationApi.class)
class InventoryReservationApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InventoryReservationApp reservationApp;

    @MockBean
    private UuidGenerator uuidGenerator;

    @Test
    @DisplayName("inventoryId가 0이면 400을 반환하고 앱 레이어를 호출하지 않는다")
    void reserveRejectsNonPositiveInventoryId() throws Exception {
        InventoryReservationReq request = new InventoryReservationReq(0L, 10L, 3, 300);

        mockMvc.perform(post("/api/inventory-reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());

        verifyNoInteractions(reservationApp);
    }

    @Test
    @DisplayName("reservedQuantity가 누락되면 400을 반환하고 앱 레이어를 호출하지 않는다")
    void reserveRejectsMissingReservedQuantity() throws Exception {
        String requestBody = """
            {
              "inventoryId": 11,
              "orderId": 22,
              "ttlSeconds": 300
            }
            """;

        mockMvc.perform(post("/api/inventory-reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isBadRequest());

        verifyNoInteractions(reservationApp);
    }

    @Test
    @DisplayName("유효한 요청이면 201을 반환한다")
    void reserveCreatesReservationForValidRequest() throws Exception {
        InventoryReservationReq request = new InventoryReservationReq(11L, 22L, 3, 300);
        LocalDateTime reservedAt = LocalDateTime.of(2026, 4, 19, 10, 0);
        LocalDateTime expiresAt = reservedAt.plusMinutes(5);

        given(uuidGenerator.nextId()).willReturn(1000L);
        given(reservationApp.reserveInventory(any())).willReturn(
            InventoryReservationJpaEntity.builder()
                .reservationId(1000L)
                .inventory(InventoryJpaEntity.withId(11L))
                .orderId(22L)
                .reservedQuantity(3)
                .reservedAt(reservedAt)
                .expiresAt(expiresAt)
                .status(ReservationStatus.RESERVED)
                .build()
        );

        mockMvc.perform(post("/api/inventory-reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.reservationId").value(1000L))
            .andExpect(jsonPath("$.inventoryId").value(11L))
            .andExpect(jsonPath("$.orderId").value(22L))
            .andExpect(jsonPath("$.reservedQuantity").value(3))
            .andExpect(jsonPath("$.status").value("RESERVED"));

        verify(reservationApp).reserveInventory(any());
    }

    @Test
    @DisplayName("예약 목록 조회시 status 필터를 앱 레이어로 전달한다")
    void getReservationsByInventoryIdPassesStatusFilter() throws Exception {
        LocalDateTime reservedAt = LocalDateTime.of(2026, 4, 19, 10, 0);
        LocalDateTime expiresAt = reservedAt.plusMinutes(5);
        given(reservationApp.getByInventoryId(11L, ReservationStatus.RESERVED)).willReturn(List.of(
            InventoryReservationJpaEntity.builder()
                .reservationId(1000L)
                .inventory(InventoryJpaEntity.withId(11L))
                .orderId(22L)
                .reservedQuantity(3)
                .reservedAt(reservedAt)
                .expiresAt(expiresAt)
                .status(ReservationStatus.RESERVED)
                .build()
        ));

        mockMvc.perform(get("/api/inventory-reservations")
                .param("inventoryId", "11")
                .param("status", "RESERVED"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].reservationId").value(1000L))
            .andExpect(jsonPath("$[0].status").value("RESERVED"));

        verify(reservationApp).getByInventoryId(eq(11L), eq(ReservationStatus.RESERVED));
    }
}

package api.inventory;

import api.inventory.request.RegisterWarehousesReq;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.inventory.domain.entity.WarehouseJpaEntity;
import domain.inventory.domain.repository.WarehouseRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = api.inventory.InventoryApiMain.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WarehouseApiTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EntityManager em;

    @BeforeEach
    void setUp() {
        warehouseRepository.deleteAll();
    }

    @Test
    @DisplayName("H2: 테이블 확인")
    void checkTables() {
        List<String> tables = em.createNativeQuery(
                "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'").getResultList();

        System.out.println("\uD83D\uDD0D Created tables: " + tables);
    }

    @Nested
    @DisplayName("창고 등록")
    class Register {
        @Test
        @DisplayName("성공")
        void success() throws Exception {
            RegisterWarehousesReq req = new RegisterWarehousesReq("서울창고", "서울 강남구", 1000);

            mockMvc.perform(post("/api/warehouses")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("서울창고"));
        }

        @Test
        @DisplayName("실패 - 이름 누락")
        void fail_name_missing() throws Exception {
            RegisterWarehousesReq req = new RegisterWarehousesReq(null, "서울 강남구", 1000);

            mockMvc.perform(post("/api/warehouses")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("실패 - 용량 음수")
        void fail_negative_capacity() throws Exception {
            RegisterWarehousesReq req = new RegisterWarehousesReq("서울창고", "서울", -10);

            mockMvc.perform(post("/api/warehouses")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Test
    @DisplayName("창고 목록 조회")
    void listWarehouses() throws Exception {
        warehouseRepository.save(WarehouseJpaEntity.create("A창고", "부산", 500));
        warehouseRepository.save(WarehouseJpaEntity.create("B창고", "대전", 800));

        mockMvc.perform(get("/api/warehouses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name", anyOf(is("A창고"), is("B창고"))));
    }

    @Nested
    @DisplayName("창고 삭제")
    class Delete {

        @Test
        @DisplayName("성공")
        void success() throws Exception {
            WarehouseJpaEntity entity = warehouseRepository.save(WarehouseJpaEntity.create("삭제용창고", "제주2", 200));

            mockMvc.perform(delete("/api/warehouses/" + entity.getWarehouseId()))
                    .andExpect(status().isOk());

            mockMvc.perform(get("/api/warehouses"))
                    .andExpect(jsonPath("$.length()").value(0));
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 ID")
        void fail_nonexistent_id() throws Exception {
            mockMvc.perform(delete("/api/warehouses/999"))
                    .andExpect(status().isNotFound());
        }
    }
}
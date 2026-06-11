package api.order.controller;

import app.order.app.OrderListApp;
import app.order.app.OrderRegisterApp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderApi.class)
class OrderApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRegisterApp orderRegisterApp;

    @MockBean
    private OrderListApp orderListApp;

    @Test
    @DisplayName("주문 목록 조회시 status 필터를 앱 레이어로 전달한다")
    void getOrdersPassesStatusFilter() throws Exception {
        given(orderListApp.getAllOrders(0, 10, "ORDERED")).willReturn(List.of());

        mockMvc.perform(get("/api/v1/orders")
                .param("status", "ORDERED"))
            .andExpect(status().isOk())
            .andExpect(content().json("[]"));

        verify(orderListApp).getAllOrders(eq(0), eq(10), eq("ORDERED"));
    }

    @Test
    @DisplayName("고객별 주문 목록 조회시 status 필터를 앱 레이어로 전달한다")
    void getOrdersByCustomerIdPassesStatusFilter() throws Exception {
        given(orderListApp.getOrdersByCustomerId(7L, "SHIPPED")).willReturn(List.of());

        mockMvc.perform(get("/api/v1/orders/")
                .param("customerId", "7")
                .param("status", "SHIPPED"))
            .andExpect(status().isOk())
            .andExpect(content().json("[]"));

        verify(orderListApp).getOrdersByCustomerId(eq(7L), eq("SHIPPED"));
    }
}

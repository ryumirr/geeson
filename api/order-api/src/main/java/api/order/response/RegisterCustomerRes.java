package api.order.response;

public record RegisterCustomerRes (
    Long customerId,
    String customerName,
    String email,
    String phone
) {
}

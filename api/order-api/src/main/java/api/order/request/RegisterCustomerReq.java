package api.order.request;

public record RegisterCustomerReq (
    String name,
    String email,
    String phone
) {
}

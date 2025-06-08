package app.order.command;

public record CustomerRegisterCommand (
    String name,
    String email,
    String phone
) {
}

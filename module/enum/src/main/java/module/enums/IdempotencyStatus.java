package module.enums;

public enum IdempotencyStatus {
    PENDING("Processing..."),
    COMPLETED("Success"),
    FAILED("Error occurred"),
    INVALID("Invalid request");

    private final String description;

    // 👇 커스텀 생성자 정의 (문자열 받음)
    IdempotencyStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
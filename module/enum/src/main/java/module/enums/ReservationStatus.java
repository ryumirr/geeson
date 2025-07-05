package module.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ReservationStatus {
    RESERVED,
    EXPIRED,
    CANCELLED,
    COMPLETED;

    // 문자열 → enum 변환
    @JsonCreator
    public static ReservationStatus from(String value) {
        return ReservationStatus.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
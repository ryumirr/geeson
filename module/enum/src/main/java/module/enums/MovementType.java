package module.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MovementType {
    IN, OUT, TRANSFER;

    // 문자열 → enum 변환
    @JsonCreator
    public static MovementType from(String value) {
        return MovementType.valueOf(value.toUpperCase());
    }

    // enum → 문자열
    @JsonValue
    public String toValue() {
        return name();
    }
}

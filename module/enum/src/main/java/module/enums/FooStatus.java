package module.enums;

import lombok.Getter;

@Getter
public enum FooStatus implements GeesonEnums{
    FOO("0000", "1111");

    private final String code;
    private final String desc;

    FooStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}

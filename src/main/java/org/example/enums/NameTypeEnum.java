package org.example.enums;

public enum NameTypeEnum {
    PERSONAL("personal"),

    COMMON("common");

    private String value;

    NameTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

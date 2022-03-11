package model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {

    AVAILABLE, PENDING, SOLD;

    @JsonValue
    String getValue() {
        return name().toLowerCase();
    }
}

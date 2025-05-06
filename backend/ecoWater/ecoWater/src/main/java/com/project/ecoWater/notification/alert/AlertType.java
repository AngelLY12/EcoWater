package com.project.ecoWater.notification.alert;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum AlertType {
    LOW_LEVEL("low_level"),
    HIGH_CONSUMPTION("high_consumption"),
    HIGH_LEVEL("high_level"),
    MEDIUM_LEVEL("medium_level"),
    FULL_TANK("full_tank"),
    DEFAULT("default");

    private final String value;

    AlertType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

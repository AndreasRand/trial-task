package com.fooddelivery.courierfeeservice.models;

public enum CityType {
    TALLINN(26038L),
    TARTU(26242L),
    PÄRNU(41803L);

    private final Long value;

    CityType(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

}

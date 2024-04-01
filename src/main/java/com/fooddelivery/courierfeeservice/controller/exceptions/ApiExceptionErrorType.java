package com.fooddelivery.courierfeeservice.controller.exceptions;

public enum ApiExceptionErrorType {
    WEATHER_DATA_ABSENT,
    REGIONAL_BASE_FEE_ABSENT,
    DISALLOWED_WIND_SPEED,
    DISALLOWED_WEATHER_PHENOMENON,
    INVALID_REQUEST_DATA,
    INVALID_AIR_TEMP_RANGE,
    INVALID_WIND_SPEED_RANGE,
    INVALID_REGEX_PATTERN
}

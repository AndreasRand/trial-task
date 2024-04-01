package com.fooddelivery.courierfeeservice.controller.exceptions;

public class ApiExceptionErrorMessage {
    public static final String WEATHER_DATA_ABSENT_ERROR_MESSAGE = "No entries for weather data were found.";
    public static final String REGIONAL_BASE_FEE_ABSENT_ERROR_MESSAGE = "No entries for regional base fee were found.";
    public static final String DISALLOWED_WIND_SPEED_ERROR_MESSAGE = "Current wind speed exceeds the maximum appropriate value for this vehicle.";
    public static final String DISALLOWED_WEATHER_PHENOMENON_ERROR_MESSAGE = "Weather phenomenon is not appropriate for this vehicle.";
    public static final String INVALID_REQUEST_DATA_ERROR_MESSAGE = "Request is missing or has invalid fields.";
    public static final String INVALID_AIR_TEMP_RANGE_ERROR_MESSAGE = "Temperature range should not overlap with other conditions and they should be in order to the type.";
    public static final String INVALID_WIND_SEED_RANGE_ERROR_MESSAGE = "Wind speed range should not be overlapping with other conditions, should not be negative and should be in order to the type.";
    public static final String INVALID_REGEX_PATTERN_ERROR_MESSAGE = "Request has an invalid regex pattern";
}

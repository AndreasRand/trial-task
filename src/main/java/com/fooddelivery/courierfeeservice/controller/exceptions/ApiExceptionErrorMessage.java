package com.fooddelivery.courierfeeservice.controller.exceptions;

public class ApiExceptionErrorMessage {
    public static final String WEATHER_DATA_ABSENT_ERROR_MESSAGE = "No entries for weather data were found.";
    public static final String REGIONAL_BASE_FEE_ABSENT_ERROR_MESSAGE = "No entries for regional base fee were found.";
    public static final String DISALLOWED_WIND_SPEED_ERROR_MESSAGE = "Current wind speed exceeds the maximum appropriate value for this vehicle.";
    public static final String DISALLOWED_WEATHER_PHENOMENON_ERROR_MESSAGE = "Weather phenomenon is not appropriate for this vehicle.";
}

package com.fooddelivery.courierfeeservice.models;

public class FeeInputRequest {
    private CityType cityType;
    private VehicleType vehicleType;
    private Long timestamp;

    public CityType getCity() {
        return cityType;
    }

    public void setCity(CityType cityType) {
        this.cityType = cityType;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}

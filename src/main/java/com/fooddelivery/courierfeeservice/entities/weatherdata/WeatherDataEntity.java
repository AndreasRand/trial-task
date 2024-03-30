package com.fooddelivery.courierfeeservice.entities.weatherdata;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "weather_data")
public class WeatherDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "station_name", nullable = false)
    private String stationName;

    @Column(name = "wmo_code", nullable = false)
    private Long wmocode;

    @Column(name = "air_temperature", nullable = false)
    private double airTemp;

    @Column(name = "wind_speed", nullable = false)
    private double windSpeed;

    private String phenomenon;

    @Column(nullable = false)
    private Long timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String station) {
        this.stationName = station;
    }

    public Long getWmocode() {
        return wmocode;
    }

    public void setWmocode(Long wmocode) {
        this.wmocode = wmocode;
    }

    public double getAirTemp() {
        return airTemp;
    }

    public void setAirTemp(double airTemp) {
        this.airTemp = airTemp;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getPhenomenon() {
        return phenomenon;
    }

    public void setPhenomenon(String phenomenon) {
        this.phenomenon = phenomenon;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public WeatherDataEntity() {}

    public WeatherDataEntity(Builder builder) {
        this.id = builder.id;
        this.stationName = builder.stationName;
        this.wmocode = builder.wmocode;
        this.airTemp = builder.airTemp;
        this.windSpeed = builder.windSpeed;
        this.phenomenon = builder.phenomenon;
        this.timestamp = builder.timestamp;
    }

    public static class Builder {
        private Long id;
        private String stationName;
        private Long wmocode;
        private double airTemp;
        private double windSpeed;
        private String phenomenon;
        private Long timestamp;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setStationName(String stationName) {
            this.stationName = stationName;
            return this;
        }

        public Builder setWmocode(Long wmocode) {
            this.wmocode = wmocode;
            return this;
        }

        public Builder setAirTemp(double airTemp) {
            this.airTemp = airTemp;
            return this;
        }

        public Builder setWindSpeed(double windSpeed) {
            this.windSpeed = windSpeed;
            return this;
        }

        public Builder setPhenomenon(String phenomenon) {
            this.phenomenon = phenomenon;
            return this;
        }

        public Builder setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public WeatherDataEntity build() {
            return new WeatherDataEntity(this);
        }
    }
}

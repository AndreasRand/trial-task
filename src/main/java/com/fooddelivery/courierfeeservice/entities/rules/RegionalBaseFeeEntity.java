package com.fooddelivery.courierfeeservice.entities.rules;

import com.fooddelivery.courierfeeservice.models.CityType;
import com.fooddelivery.courierfeeservice.models.VehicleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "regional_base_fees")
public class RegionalBaseFeeEntity implements BaseRuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_type", nullable = false)
    private VehicleType vehicleType;

    @Column(name = "city_type", nullable = false)
    private CityType cityType;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "timestamp", nullable = false)
    private Long timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public CityType getCityType() {
        return cityType;
    }

    public void setCityType(CityType cityType) {
        this.cityType = cityType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public RegionalBaseFeeEntity() {}

    public RegionalBaseFeeEntity(Builder builder) {
        this.id = builder.id;
        this.vehicleType = builder.vehicleType;
        this.cityType = builder.cityType;
        this.amount = builder.amount;
        this.timestamp = builder.timestamp;
    }

    public static class Builder {
        private Long id;
        private VehicleType vehicleType;
        private CityType cityType;
        private double amount;
        private Long timestamp;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setVehicleType(VehicleType vehicleType) {
            this.vehicleType = vehicleType;
            return this;
        }

        public Builder setCityType(CityType cityType) {
            this.cityType = cityType;
            return this;
        }

        public Builder setAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public RegionalBaseFeeEntity build() {
            return new RegionalBaseFeeEntity(this);
        }
    }
}

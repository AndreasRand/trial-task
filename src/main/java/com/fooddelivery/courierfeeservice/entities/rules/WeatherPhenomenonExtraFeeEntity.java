package com.fooddelivery.courierfeeservice.entities.rules;

import com.fooddelivery.courierfeeservice.models.ConditionType;
import com.fooddelivery.courierfeeservice.models.VehicleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "weather_phenomenon_extra_fees")
public class WeatherPhenomenonExtraFeeEntity implements BaseRuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_type", nullable = false)
    private VehicleType vehicleType;

    @Column(name = "condition_type", nullable = false)
    private ConditionType conditionType;

    @Column(name = "phenomenon_pattern", nullable = false)
    private String phenomenonPattern;

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

    public ConditionType getConditionType() {
        return conditionType;
    }

    public void setConditionType(ConditionType conditionType) {
        this.conditionType = conditionType;
    }

    public String getPhenomenonPattern() {
        return phenomenonPattern;
    }

    public void setPhenomenonPattern(String phenomenonPattern) {
        this.phenomenonPattern = phenomenonPattern;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public WeatherPhenomenonExtraFeeEntity() {}

    public WeatherPhenomenonExtraFeeEntity(Builder builder) {
        this.id = builder.id;
        this.vehicleType = builder.vehicleType;
        this.conditionType = builder.conditionType;
        this.phenomenonPattern = builder.phenomenonPattern;
        this.amount = builder.amount;
        this.timestamp = builder.timestamp;
    }

    public static class Builder {
        private Long id;
        private VehicleType vehicleType;
        private ConditionType conditionType;
        private String phenomenonPattern;
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

        public Builder setConditionType(ConditionType conditionType) {
            this.conditionType = conditionType;
            return this;
        }

        public Builder setPhenomenonPattern(String phenomenonPattern) {
            this.phenomenonPattern = phenomenonPattern;
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

        public WeatherPhenomenonExtraFeeEntity build() {
            return new WeatherPhenomenonExtraFeeEntity(this);
        }
    }
}

package com.fooddelivery.courierfeeservice.entities.rules;

import com.fooddelivery.courierfeeservice.models.rules.ConditionType;
import com.fooddelivery.courierfeeservice.models.rules.VehicleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "air_temperature_extra_fees")
public class AirTemperatureExtraFeeEntity extends BaseRuleEntity {
    @Column(name = "condition_type", nullable = false)
    private ConditionType conditionType;

    @Column(name = "max_temperature_requirement", nullable = false)
    private Double maxTempRequirement;

    @Column(name = "min_temperature_requirement")
    private Double minTempRequirement;

    public ConditionType getConditionType() {
        return conditionType;
    }

    public void setConditionType(ConditionType conditionType) {
        this.conditionType = conditionType;
    }

    public Double getMaxTempRequirement() {
        return maxTempRequirement;
    }

    public void setMaxTempRequirement(Double maxTempRequirement) {
        this.maxTempRequirement = maxTempRequirement;
    }

    public Double getMinTempRequirement() {
        return minTempRequirement;
    }

    public void setMinTempRequirement(Double minTempRequirement) {
        this.minTempRequirement = minTempRequirement;
    }

    public AirTemperatureExtraFeeEntity() {}

    public AirTemperatureExtraFeeEntity(Builder builder) {
        this.id = builder.id;
        this.vehicleType = builder.vehicleType;
        this.conditionType = builder.conditionType;
        this.maxTempRequirement = builder.maxTempRequirement;
        this.minTempRequirement = builder.minTempRequirement;
        this.amount = builder.amount;
        this.timestamp = builder.timestamp;
    }

    public static class Builder {
        private Long id;
        private VehicleType vehicleType;
        private ConditionType conditionType;
        private double maxTempRequirement;
        private Double minTempRequirement;
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

        public Builder setMaxTempRequirement(double maxTempRequirement) {
            this.maxTempRequirement = maxTempRequirement;
            return this;
        }

        public Builder setMinTempRequirement(Double minTempRequirement) {
            this.minTempRequirement = minTempRequirement;
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

        public AirTemperatureExtraFeeEntity build() {
            return new AirTemperatureExtraFeeEntity(this);
        }
    }
}

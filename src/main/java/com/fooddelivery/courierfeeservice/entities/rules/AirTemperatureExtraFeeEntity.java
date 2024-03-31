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
@Table(name = "air_temperature_extra_fees")
public class AirTemperatureExtraFeeEntity extends BaseRuleEntity {
    @Column(name = "condition_type", nullable = false)
    private ConditionType conditionType;

    @Column(name = "temperature_requirement", nullable = false)
    private Double tempRequirement;

    public ConditionType getConditionType() {
        return conditionType;
    }

    public void setConditionType(ConditionType conditionType) {
        this.conditionType = conditionType;
    }

    public Double getTempRequirement() {
        return tempRequirement;
    }

    public void setTempRequirement(double tempRequirement) {
        this.tempRequirement = tempRequirement;
    }

    public AirTemperatureExtraFeeEntity() {}

    public AirTemperatureExtraFeeEntity(Builder builder) {
        this.id = builder.id;
        this.vehicleType = builder.vehicleType;
        this.conditionType = builder.conditionType;
        this.tempRequirement = builder.tempRequirement;
        this.amount = builder.amount;
        this.timestamp = builder.timestamp;
    }

    public static class Builder {
        private Long id;
        private VehicleType vehicleType;
        private ConditionType conditionType;
        private double tempRequirement;
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

        public Builder setTempRequirement(double tempRequirement) {
            this.tempRequirement = tempRequirement;
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

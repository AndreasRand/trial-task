package com.fooddelivery.courierfeeservice.entities.rules;

import com.fooddelivery.courierfeeservice.models.rules.ConditionType;
import com.fooddelivery.courierfeeservice.models.rules.VehicleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "wind_speed_extra_fees")
public class WindSpeedExtraFeeEntity extends BaseRuleEntity {
    @Column(name = "condition_type", nullable = false)
    private ConditionType conditionType;

    @Column(name = "wind_speed_requirement", nullable = false)
    private Double windSpeedRequirement;

    public ConditionType getConditionType() {
        return conditionType;
    }

    public void setConditionType(ConditionType conditionType) {
        this.conditionType = conditionType;
    }

    public double getWindSpeedRequirement() {
        return windSpeedRequirement;
    }

    public void setWindSpeedRequirement(double windSpeedRequirement) {
        this.windSpeedRequirement = windSpeedRequirement;
    }

    public WindSpeedExtraFeeEntity() {}

    public WindSpeedExtraFeeEntity(Builder builder) {
        this.id = builder.id;
        this.vehicleType = builder.vehicleType;
        this.conditionType = builder.conditionType;
        this.windSpeedRequirement = builder.windSpeedRequirement;
        this.amount = builder.amount;
        this.timestamp = builder.timestamp;
    }

    public static class Builder {
        private Long id;
        private VehicleType vehicleType;
        private ConditionType conditionType;
        private double windSpeedRequirement;
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

        public Builder setWindSpeedRequirement(double windSpeedRequirement) {
            this.windSpeedRequirement = windSpeedRequirement;
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

        public WindSpeedExtraFeeEntity build() {
            return new WindSpeedExtraFeeEntity(this);
        }
    }
}

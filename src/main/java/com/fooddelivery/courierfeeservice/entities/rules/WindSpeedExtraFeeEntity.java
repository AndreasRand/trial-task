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

    @Column(name = "min_wind_speed_requirement", nullable = false)
    private Double minWindSpeedRequirement;

    @Column(name = "max_wind_speed_requirement")
    private Double maxWindSpeedRequirement;

    public ConditionType getConditionType() {
        return conditionType;
    }

    public void setConditionType(ConditionType conditionType) {
        this.conditionType = conditionType;
    }

    public Double getMinWindSpeedRequirement() {
        return minWindSpeedRequirement;
    }

    public WindSpeedExtraFeeEntity setMinWindSpeedRequirement(Double minWindSpeedRequirement) {
        this.minWindSpeedRequirement = minWindSpeedRequirement;
        return this;
    }

    public Double getMaxWindSpeedRequirement() {
        return maxWindSpeedRequirement;
    }

    public WindSpeedExtraFeeEntity setMaxWindSpeedRequirement(Double maxWindSpeedRequirement) {
        this.maxWindSpeedRequirement = maxWindSpeedRequirement;
        return this;
    }

    public WindSpeedExtraFeeEntity() {}

    public WindSpeedExtraFeeEntity(Builder builder) {
        this.id = builder.id;
        this.vehicleType = builder.vehicleType;
        this.conditionType = builder.conditionType;
        this.minWindSpeedRequirement = builder.minWindSpeedRequirement;
        this.maxWindSpeedRequirement = builder.maxWindSpeedRequirement;
        this.amount = builder.amount;
        this.timestamp = builder.timestamp;
    }

    public static class Builder {
        private Long id;
        private VehicleType vehicleType;
        private ConditionType conditionType;
        private double minWindSpeedRequirement;
        private Double maxWindSpeedRequirement;
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

        public Builder setMinWindSpeedRequirement(double minWindSpeedRequirement) {
            this.minWindSpeedRequirement = minWindSpeedRequirement;
            return this;
        }

        public Builder setMaxWindSpeedRequirement(Double maxWindSpeedRequirement) {
            this.maxWindSpeedRequirement = maxWindSpeedRequirement;
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

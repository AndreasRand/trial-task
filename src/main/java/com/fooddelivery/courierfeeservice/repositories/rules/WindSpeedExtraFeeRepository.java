package com.fooddelivery.courierfeeservice.repositories.rules;

import com.fooddelivery.courierfeeservice.entities.rules.AirTemperatureExtraFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.WindSpeedExtraFeeEntity;
import com.fooddelivery.courierfeeservice.models.rules.ConditionType;
import com.fooddelivery.courierfeeservice.models.rules.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WindSpeedExtraFeeRepository extends JpaRepository<WindSpeedExtraFeeEntity, Long> {
    @Query("SELECT wsef " +
            "FROM WindSpeedExtraFeeEntity wsef " +
            "JOIN (" +
            "   SELECT MAX(wsef2.id) as latest_id FROM WindSpeedExtraFeeEntity wsef2 " +
            "   GROUP BY wsef2.conditionType " +
            ") latest_ids ON wsef.id = latest_ids.latest_id " +
            "WHERE wsef.vehicleType = :vehicleType " +
            "AND wsef.minWindSpeedRequirement <= :windSpeed " +
            "AND (wsef.maxWindSpeedRequirement IS NULL OR wsef.maxWindSpeedRequirement >= :windSpeed)")
    Optional<WindSpeedExtraFeeEntity> findLatestByWindSpeedAndVehicleType(@Param("windSpeed") double windSpeed, @Param("vehicleType") VehicleType vehicleType);

    @Query("SELECT wsef " +
            "FROM WindSpeedExtraFeeEntity wsef " +
            "JOIN (" +
            "   SELECT MAX(wsef2.id) as latest_id FROM WindSpeedExtraFeeEntity wsef2 " +
            "   WHERE wsef2.timestamp <= :timestamp " +
            "   GROUP BY wsef2.conditionType " +
            ") latest_ids ON wsef.id = latest_ids.latest_id " +
            "WHERE wsef.vehicleType = :vehicleType " +
            "AND wsef.minWindSpeedRequirement <= :windSpeed " +
            "AND (wsef.maxWindSpeedRequirement IS NULL OR wsef.maxWindSpeedRequirement >= :windSpeed)")
    Optional<WindSpeedExtraFeeEntity> findByWindSpeedAndVehicleTypeAndTimestamp(@Param("windSpeed") double windSpeed, @Param("vehicleType") VehicleType vehicleType, @Param("timestamp") Long timestamp);

    @Query("SELECT wsef " +
            "FROM WindSpeedExtraFeeEntity wsef " +
            "JOIN (" +
            "   SELECT MAX(wsef.id) as latest_id FROM WindSpeedExtraFeeEntity wsef " +
            "   GROUP BY wsef.conditionType " +
            ") latest_ids ON wsef.id = latest_ids.latest_id " +
            "WHERE wsef.conditionType != :conditionType")
    List<WindSpeedExtraFeeEntity> findLatestByExcludingConditionType(@Param("conditionType") ConditionType conditionType);
}

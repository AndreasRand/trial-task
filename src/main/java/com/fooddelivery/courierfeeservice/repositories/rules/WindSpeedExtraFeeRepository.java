package com.fooddelivery.courierfeeservice.repositories.rules;

import com.fooddelivery.courierfeeservice.entities.rules.WindSpeedExtraFeeEntity;
import com.fooddelivery.courierfeeservice.models.rules.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WindSpeedExtraFeeRepository extends JpaRepository<WindSpeedExtraFeeEntity, Long> {
    @Query("SELECT wsef " +
            "FROM WindSpeedExtraFeeEntity wsef " +
            "JOIN (" +
            "   SELECT MAX(wsef2.id) as latest_id FROM WindSpeedExtraFeeEntity wsef2 " +
            "   GROUP BY wsef2.conditionType " +
            "   ORDER BY latest_id DESC " +
            ") latest_ids ON wsef.id = latest_ids.latest_id " +
            "WHERE wsef.vehicleType = :vehicleType " +
            "AND wsef.windSpeedRequirement < :windSpeed " +
            "ORDER BY wsef.conditionType DESC LIMIT 1")
    Optional<WindSpeedExtraFeeEntity> findLatestByWindSpeedAndVehicleType(@Param("windSpeed") double windSpeed, @Param("vehicleType") VehicleType vehicleType);

    @Query("SELECT wsef " +
            "FROM WindSpeedExtraFeeEntity wsef " +
            "JOIN (" +
            "   SELECT MAX(wsef2.id) as latest_id FROM WindSpeedExtraFeeEntity wsef2 " +
            "   WHERE wsef2.timestamp <= :timestamp " +
            "   GROUP BY wsef2.conditionType " +
            "   ORDER BY latest_id DESC " +
            ") latest_ids ON wsef.id = latest_ids.latest_id " +
            "WHERE wsef.vehicleType = :vehicleType " +
            "AND wsef.windSpeedRequirement < :windSpeed " +
            "ORDER BY wsef.conditionType DESC LIMIT 1")
    Optional<WindSpeedExtraFeeEntity> findByWindSpeedAndVehicleTypeAndTimestamp(@Param("windSpeed") double windSpeed, @Param("vehicleType") VehicleType vehicleType, @Param("timestamp") Long timestamp);
}

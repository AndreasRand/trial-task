package com.fooddelivery.courierfeeservice.repositories.rules;

import com.fooddelivery.courierfeeservice.entities.rules.AirTemperatureExtraFeeEntity;
import com.fooddelivery.courierfeeservice.models.rules.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AirTemperatureExtraFeeRepository extends JpaRepository<AirTemperatureExtraFeeEntity, Long> {
    @Query("SELECT atef " +
            "FROM AirTemperatureExtraFeeEntity atef " +
            "JOIN (" +
            "   SELECT MAX(atef2.id) as latest_id FROM AirTemperatureExtraFeeEntity atef2 " +
            "   GROUP BY atef2.conditionType " +
            "   ORDER BY latest_id DESC " +
            ") latest_ids ON atef.id = latest_ids.latest_id " +
            "WHERE atef.vehicleType = :vehicleType " +
            "AND atef.tempRequirement > :temperature " +
            "ORDER BY atef.conditionType DESC LIMIT 1")
    Optional<AirTemperatureExtraFeeEntity> findLatestByAirTemperatureAndVehicleType(@Param("temperature") double temperature, @Param("vehicleType") VehicleType vehicleType);

    @Query("SELECT atef " +
            "FROM AirTemperatureExtraFeeEntity atef " +
            "JOIN (" +
            "   SELECT MAX(atef2.id) as latest_id FROM AirTemperatureExtraFeeEntity atef2 " +
            "   WHERE atef2.timestamp <= :timestamp " +
            "   GROUP BY atef2.conditionType " +
            "   ORDER BY latest_id DESC " +
            ") latest_ids ON atef.id = latest_ids.latest_id " +
            "WHERE atef.vehicleType = :vehicleType " +
            "AND atef.tempRequirement > :temperature " +
            "ORDER BY atef.conditionType DESC LIMIT 1")
    Optional<AirTemperatureExtraFeeEntity> findByAirTemperatureAndVehicleTypeAndTimestamp(@Param("temperature") double temperature, @Param("vehicleType") VehicleType vehicleType, @Param("timestamp") Long timestamp);
}

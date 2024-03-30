package com.fooddelivery.courierfeeservice.repositories.rules;

import com.fooddelivery.courierfeeservice.entities.rules.WeatherPhenomenonExtraFeeEntity;
import com.fooddelivery.courierfeeservice.models.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WeatherPhenomenonExtraFeeRepository extends JpaRepository<WeatherPhenomenonExtraFeeEntity, Long> {
    @Query(value = "SELECT * " +
            "FROM weather_phenomenon_extra_fees wpef " +
            "JOIN (" +
            "   SELECT MAX(wpef2.id) as latest_id FROM weather_phenomenon_extra_fees wpef2 " +
            "   GROUP BY wpef2.condition_type " +
            "   ORDER BY latest_id DESC " +
            ") latest_ids ON wpef.id = latest_ids.latest_id " +
            "WHERE REGEXP_LIKE(:phenomenon, wpef.phenomenon_pattern) " +
            "AND wpef.vehicle_type = :vehicleType " +
            "ORDER BY wpef.condition_type DESC LIMIT 1", nativeQuery = true) // JPA does not currently support REGEXP_LIKE function, thus nativeQuery is required
    Optional<WeatherPhenomenonExtraFeeEntity> findLatestByPhenomenonAndVehicleType(@Param("phenomenon") String phenomenon, @Param("vehicleType") VehicleType vehicleType);

    @Query(value = "SELECT * " +
            "FROM weather_phenomenon_extra_fees wpef " +
            "JOIN (" +
            "   SELECT MAX(wpef2.id) as latest_id FROM weather_phenomenon_extra_fees wpef2 " +
            "   WHERE wpef2.timestamp <= :timestamp " +
            "   GROUP BY wpef2.condition_type " +
            "   ORDER BY latest_id DESC " +
            ") latest_ids ON wpef.id = latest_ids.latest_id " +
            "WHERE REGEXP_LIKE(:phenomenon, wpef.phenomenon_pattern) " +
            "AND wpef.vehicle_type = :vehicleType " +
            "ORDER BY wpef.condition_type DESC LIMIT 1", nativeQuery = true)
    Optional<WeatherPhenomenonExtraFeeEntity> findLatestByPhenomenonAndVehicleTypeAndTimestamp(@Param("phenomenon") String phenomenon, @Param("vehicleType") VehicleType vehicleType, @Param("timestamp") Long timestamp);
}

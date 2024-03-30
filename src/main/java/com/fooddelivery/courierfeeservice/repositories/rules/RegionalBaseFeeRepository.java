package com.fooddelivery.courierfeeservice.repositories.rules;

import com.fooddelivery.courierfeeservice.entities.rules.RegionalBaseFeeEntity;
import com.fooddelivery.courierfeeservice.models.CityType;
import com.fooddelivery.courierfeeservice.models.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionalBaseFeeRepository extends JpaRepository<RegionalBaseFeeEntity, Long> {
    Optional<RegionalBaseFeeEntity> findTopByVehicleTypeAndCityTypeAndTimestampLessThanEqualOrderByTimestampDesc(
            VehicleType vehicleType, CityType cityType, Long timestamp);

    Optional<RegionalBaseFeeEntity> findTopByVehicleTypeAndCityTypeOrderByIdDesc(
            VehicleType vehicleType, CityType cityType);
}

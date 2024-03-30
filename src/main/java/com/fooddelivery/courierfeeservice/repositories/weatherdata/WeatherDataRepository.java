package com.fooddelivery.courierfeeservice.repositories.weatherdata;

import com.fooddelivery.courierfeeservice.entities.weatherdata.WeatherDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherDataRepository extends JpaRepository<WeatherDataEntity, Long> {
    Optional<WeatherDataEntity> findFirstByWmocodeOrderByIdDesc(Long wmocode);

    Optional<WeatherDataEntity> findFirstByWmocodeAndTimestampLessThanEqualOrderByIdDesc(Long wmocode, Long timestamp);
}

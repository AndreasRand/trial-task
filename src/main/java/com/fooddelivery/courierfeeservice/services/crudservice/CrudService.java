package com.fooddelivery.courierfeeservice.services.crudservice;

import com.fooddelivery.courierfeeservice.entities.rules.AirTemperatureExtraFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.BaseRuleEntity;
import com.fooddelivery.courierfeeservice.entities.rules.RegionalBaseFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.WeatherPhenomenonExtraFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.WindSpeedExtraFeeEntity;
import com.fooddelivery.courierfeeservice.repositories.rules.AirTemperatureExtraFeeRepository;
import com.fooddelivery.courierfeeservice.repositories.rules.RegionalBaseFeeRepository;
import com.fooddelivery.courierfeeservice.repositories.rules.WeatherPhenomenonExtraFeeRepository;
import com.fooddelivery.courierfeeservice.repositories.rules.WindSpeedExtraFeeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class CrudService {
    @Transactional
    public static  <T extends BaseRuleEntity> ResponseEntity<Object> createEntity(JpaRepository<T, ?> repository, T entity) {
        try {
            entity.setId(null);
            entity.setTimestamp(Instant.now().getEpochSecond());
            return ResponseEntity.ok().body(repository.save(entity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional
    public static  <T extends BaseRuleEntity> ResponseEntity<Object> readEntity(JpaRepository<T, Long> repository, Long id) {
        Optional<T> res = repository.findById(id);
        return res.<ResponseEntity<Object>>map(t -> ResponseEntity.ok().body(t)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Transactional
    public static  ResponseEntity<Object> updateEntity(RegionalBaseFeeRepository repository, Long id, RegionalBaseFeeEntity entity) {
        RegionalBaseFeeEntity res = repository.findById(id).orElse(null);
        if (res == null) return ResponseEntity.notFound().build();
        res.setVehicleType(entity.getVehicleType());
        res.setAmount(entity.getAmount());
        res.setTimestamp(Instant.now().getEpochSecond());
        res.setCityType(entity.getCityType());
        try {
            return ResponseEntity.ok().body(repository.save(res));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional
    public static  ResponseEntity<Object> updateEntity(AirTemperatureExtraFeeRepository repository, Long id, AirTemperatureExtraFeeEntity entity) {
        AirTemperatureExtraFeeEntity res = repository.findById(id).orElse(null);
        if (res == null) return ResponseEntity.notFound().build();
        res.setVehicleType(entity.getVehicleType());
        res.setAmount(entity.getAmount());
        res.setTimestamp(Instant.now().getEpochSecond());
        res.setConditionType(entity.getConditionType());
        res.setMaxTempRequirement(entity.getMaxTempRequirement());
        res.setMinTempRequirement(entity.getMinTempRequirement());
        try {
            return ResponseEntity.ok().body(repository.save(res));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional
    public static  ResponseEntity<Object> updateEntity(WindSpeedExtraFeeRepository repository, Long id, WindSpeedExtraFeeEntity entity) {
        WindSpeedExtraFeeEntity res = repository.findById(id).orElse(null);
        if (res == null) return ResponseEntity.notFound().build();
        res.setVehicleType(entity.getVehicleType());
        res.setAmount(entity.getAmount());
        res.setTimestamp(Instant.now().getEpochSecond());
        res.setConditionType(entity.getConditionType());
        res.setMaxWindSpeedRequirement(entity.getMaxWindSpeedRequirement());
        res.setMinWindSpeedRequirement(entity.getMinWindSpeedRequirement());
        try {
            return ResponseEntity.ok().body(repository.save(res));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional
    public static  ResponseEntity<Object> updateEntity(WeatherPhenomenonExtraFeeRepository repository, Long id, WeatherPhenomenonExtraFeeEntity entity) {
        WeatherPhenomenonExtraFeeEntity res = repository.findById(id).orElse(null);
        if (res == null) return ResponseEntity.notFound().build();
        res.setVehicleType(entity.getVehicleType());
        res.setAmount(entity.getAmount());
        res.setTimestamp(Instant.now().getEpochSecond());
        res.setConditionType(entity.getConditionType());
        res.setPhenomenonPattern(entity.getPhenomenonPattern());
        try {
            return ResponseEntity.ok().body(repository.save(res));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional
    public static  <T extends BaseRuleEntity> ResponseEntity<Object> deleteEntity(JpaRepository<T, Long> repository, Long id) {
        Optional<T> res = repository.findById(id);
        if (res.isEmpty()) return ResponseEntity.notFound().build();
        repository.delete(res.get());
        return ResponseEntity.ok().build();
    }
}

package com.fooddelivery.courierfeeservice.services.crudservice;

import com.fooddelivery.courierfeeservice.controller.exceptions.ApiExceptionErrorType;
import com.fooddelivery.courierfeeservice.entities.rules.AirTemperatureExtraFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.BaseRuleEntity;
import com.fooddelivery.courierfeeservice.entities.rules.RegionalBaseFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.WeatherPhenomenonExtraFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.WindSpeedExtraFeeEntity;
import com.fooddelivery.courierfeeservice.models.apiresponse.ErrorResponse;
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

import static com.fooddelivery.courierfeeservice.controller.exceptions.ApiExceptionErrorMessage.INVALID_REQUEST_DATA_ERROR_MESSAGE;

@Service
public class CrudService {
    /**
     * Creates a new entity in the database.
     *
     * @param repository The repository for the entity type.
     * @param entity     The entity object to be created.
     * @param <T>        The type of the entity.
     * @return A ResponseEntity containing the created entity if successful,
     * or a bad request response if an error occurs during creation.
     */
    @Transactional
    public static  <T extends BaseRuleEntity> ResponseEntity<Object> createEntity(JpaRepository<T, ?> repository, T entity) {
        try {
            entity.setId(null);
            entity.setTimestamp(Instant.now().getEpochSecond());
            return ResponseEntity.ok().body(repository.save(entity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(INVALID_REQUEST_DATA_ERROR_MESSAGE, ApiExceptionErrorType.INVALID_REQUEST_DATA.name()));
        }
    }

    /**
     * Retrieves an entity from the database by its ID.
     *
     * @param repository The repository for the entity type.
     * @param id         The ID of the entity to retrieve.
     * @param <T>        The type of the entity.
     * @return A ResponseEntity containing the retrieved entity if found,
     * or a not found response if the entity with the given ID does not exist.
     */
    @Transactional
    public static  <T extends BaseRuleEntity> ResponseEntity<Object> readEntity(JpaRepository<T, Long> repository, Long id) {
        Optional<T> res = repository.findById(id);
        return res.<ResponseEntity<Object>>map(t -> ResponseEntity.ok().body(t)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing entity in the database.
     *
     * @param repository The repository for the entity type.
     * @param id         The ID of the entity to update.
     * @param entity     The updated entity object.
     * @return A ResponseEntity containing the updated entity if successful,
     * or a not found response if the entity with the given ID does not exist,
     * or a bad request response if an error occurs during update.
     */
    @Transactional
    public static  ResponseEntity<Object> updateEntity(RegionalBaseFeeRepository repository, Long id, RegionalBaseFeeEntity entity) {
        RegionalBaseFeeEntity res = repository.findById(id).orElse(null);
        if (res == null) return ResponseEntity.notFound().build();
        res.setVehicleType(entity.getVehicleType());
        res.setAmount(entity.getAmount());
        res.setCityType(entity.getCityType());
        try {
            return ResponseEntity.ok().body(repository.save(res));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(INVALID_REQUEST_DATA_ERROR_MESSAGE, ApiExceptionErrorType.INVALID_REQUEST_DATA.name()));
        }
    }

    /**
     * Updates an existing entity in the database.
     * Overloaded method for updating AirTemperatureExtraFeeEntity.
     *
     * @param repository The repository for the entity type.
     * @param id         The ID of the entity to update.
     * @param entity     The updated entity object.
     * @return A ResponseEntity containing the updated entity if successful,
     * or a not found response if the entity with the given ID does not exist,
     * or a bad request response if an error occurs during update.
     */
    @Transactional
    public static  ResponseEntity<Object> updateEntity(AirTemperatureExtraFeeRepository repository, Long id, AirTemperatureExtraFeeEntity entity) {
        AirTemperatureExtraFeeEntity res = repository.findById(id).orElse(null);
        if (res == null) return ResponseEntity.notFound().build();
        res.setVehicleType(entity.getVehicleType());
        res.setAmount(entity.getAmount());
        res.setConditionType(entity.getConditionType());
        res.setMaxTempRequirement(entity.getMaxTempRequirement());
        res.setMinTempRequirement(entity.getMinTempRequirement());
        try {
            return ResponseEntity.ok().body(repository.save(res));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(INVALID_REQUEST_DATA_ERROR_MESSAGE, ApiExceptionErrorType.INVALID_REQUEST_DATA.name()));
        }
    }

    /**
     * Updates an existing entity in the database.
     * Overloaded method for updating WindSpeedExtraFeeEntity.
     *
     * @param repository The repository for the entity type.
     * @param id         The ID of the entity to update.
     * @param entity     The updated entity object.
     * @return A ResponseEntity containing the updated entity if successful,
     * or a not found response if the entity with the given ID does not exist,
     * or a bad request response if an error occurs during update.
     */
    @Transactional
    public static  ResponseEntity<Object> updateEntity(WindSpeedExtraFeeRepository repository, Long id, WindSpeedExtraFeeEntity entity) {
        WindSpeedExtraFeeEntity res = repository.findById(id).orElse(null);
        if (res == null) return ResponseEntity.notFound().build();
        res.setVehicleType(entity.getVehicleType());
        res.setAmount(entity.getAmount());
        res.setConditionType(entity.getConditionType());
        res.setMaxWindSpeedRequirement(entity.getMaxWindSpeedRequirement());
        res.setMinWindSpeedRequirement(entity.getMinWindSpeedRequirement());
        try {
            return ResponseEntity.ok().body(repository.save(res));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(INVALID_REQUEST_DATA_ERROR_MESSAGE, ApiExceptionErrorType.INVALID_REQUEST_DATA.name()));
        }
    }

    /**
     * Updates an existing entity in the database.
     * Overloaded method for updating WeatherPhenomenonExtraFeeEntity.
     *
     * @param repository The repository for the entity type.
     * @param id         The ID of the entity to update.
     * @param entity     The updated entity object.
     * @return A ResponseEntity containing the updated entity if successful,
     * or a not found response if the entity with the given ID does not exist,
     * or a bad request response if an error occurs during update.
     */
    @Transactional
    public static  ResponseEntity<Object> updateEntity(WeatherPhenomenonExtraFeeRepository repository, Long id, WeatherPhenomenonExtraFeeEntity entity) {
        WeatherPhenomenonExtraFeeEntity res = repository.findById(id).orElse(null);
        if (res == null) return ResponseEntity.notFound().build();
        res.setVehicleType(entity.getVehicleType());
        res.setAmount(entity.getAmount());
        res.setConditionType(entity.getConditionType());
        res.setPhenomenonPattern(entity.getPhenomenonPattern());
        try {
            return ResponseEntity.ok().body(repository.save(res));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(INVALID_REQUEST_DATA_ERROR_MESSAGE, ApiExceptionErrorType.INVALID_REQUEST_DATA.name()));
        }
    }

    /**
     * Deletes an existing entity from the database by its ID.
     *
     * @param repository The repository for the entity type.
     * @param id         The ID of the entity to delete.
     * @param <T>        The type of the entity.
     * @return A ResponseEntity indicating the success of the delete operation,
     * or a not found response if the entity with the given ID does not exist.
     */
    @Transactional
    public static  <T extends BaseRuleEntity> ResponseEntity<Object> deleteEntity(JpaRepository<T, Long> repository, Long id) {
        Optional<T> res = repository.findById(id);
        if (res.isEmpty()) return ResponseEntity.notFound().build();
        repository.delete(res.get());
        return ResponseEntity.ok().build();
    }
}

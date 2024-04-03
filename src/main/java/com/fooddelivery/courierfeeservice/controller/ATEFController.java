package com.fooddelivery.courierfeeservice.controller;

import com.fooddelivery.courierfeeservice.controller.exceptions.ApiExceptionErrorType;
import com.fooddelivery.courierfeeservice.entities.rules.AirTemperatureExtraFeeEntity;
import com.fooddelivery.courierfeeservice.models.apiresponse.ErrorResponse;
import com.fooddelivery.courierfeeservice.models.rules.ConditionType;
import com.fooddelivery.courierfeeservice.repositories.rules.AirTemperatureExtraFeeRepository;
import com.fooddelivery.courierfeeservice.services.crudservice.CrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

import static com.fooddelivery.courierfeeservice.controller.exceptions.ApiExceptionErrorMessage.INVALID_AIR_TEMP_RANGE_ERROR_MESSAGE;

@RestController
@RequestMapping("/api/ATEF")
public class ATEFController {
    private final AirTemperatureExtraFeeRepository airTemperatureExtraFeeRepository;

    public ATEFController(AirTemperatureExtraFeeRepository airTemperatureExtraFeeRepository) {
        this.airTemperatureExtraFeeRepository = airTemperatureExtraFeeRepository;
    }

    @PostMapping
    public ResponseEntity<Object> createATEF(@RequestBody AirTemperatureExtraFeeEntity airTemperatureExtraFeeEntity) {
        if (hasInvalidTemperatureRangeBasedOnOtherConditions(airTemperatureExtraFeeEntity))
            return ResponseEntity.badRequest().body(new ErrorResponse(INVALID_AIR_TEMP_RANGE_ERROR_MESSAGE, ApiExceptionErrorType.INVALID_AIR_TEMP_RANGE.name()));
        return CrudService.createEntity(airTemperatureExtraFeeRepository, airTemperatureExtraFeeEntity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> readATEF(@PathVariable Long id) {
        return CrudService.readEntity(airTemperatureExtraFeeRepository, id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateATEF(@PathVariable Long id, @RequestBody AirTemperatureExtraFeeEntity airTemperatureExtraFeeEntity) {
        if (hasInvalidTemperatureRangeBasedOnOtherConditions(airTemperatureExtraFeeEntity))
            return ResponseEntity.badRequest().body(new ErrorResponse(INVALID_AIR_TEMP_RANGE_ERROR_MESSAGE, ApiExceptionErrorType.INVALID_AIR_TEMP_RANGE.name()));
        return CrudService.updateEntity(airTemperatureExtraFeeRepository, id, airTemperatureExtraFeeEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteATEF(@PathVariable Long id) {
        return CrudService.deleteEntity(airTemperatureExtraFeeRepository, id);
    }

    private boolean hasInvalidTemperatureRangeBasedOnOtherConditions(AirTemperatureExtraFeeEntity insertableEntity) {
        if (insertableEntity.getMaxTempRequirement() == null) return true;
        if (ConditionType.DISALLOWED.equals(insertableEntity.getConditionType()) && insertableEntity.getMinTempRequirement() != null) return true;
        List<AirTemperatureExtraFeeEntity> latestFeesExcludingReplaced = airTemperatureExtraFeeRepository.findLatestByExcludingConditionType(insertableEntity.getConditionType());
        for (AirTemperatureExtraFeeEntity airTemperatureExtraFeeEntity : latestFeesExcludingReplaced) {
            if (isRangesOverlapping(airTemperatureExtraFeeEntity.getMinTempRequirement(), airTemperatureExtraFeeEntity.getMaxTempRequirement(), insertableEntity.getMinTempRequirement(), insertableEntity.getMaxTempRequirement())) return true;
        }
        latestFeesExcludingReplaced.add(insertableEntity);
        latestFeesExcludingReplaced.sort(Comparator.comparing(AirTemperatureExtraFeeEntity::getConditionType));
        return hasGapsInRanges(latestFeesExcludingReplaced);
    }

    private boolean isRangesOverlapping(Double min1, Double max1, Double min2, Double max2) {
        if (min1 == null && min2 == null) return true;

        if (min1 == null) return max1 >= min2;

        if (min2 == null) return max2 >= min1;

        return min1 <= max2 && max1 >= min2;
    }

    private boolean hasGapsInRanges(List<AirTemperatureExtraFeeEntity> entities) {
        for (int i = 0; i < entities.size()-1; i++) {
            if (entities.get(i).getMinTempRequirement()-1 != entities.get(i+1).getMaxTempRequirement()) return true;
        }
        return false;
    }
}

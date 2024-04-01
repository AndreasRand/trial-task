package com.fooddelivery.courierfeeservice.controller;

import com.fooddelivery.courierfeeservice.controller.exceptions.ApiExceptionErrorType;
import com.fooddelivery.courierfeeservice.entities.rules.WindSpeedExtraFeeEntity;
import com.fooddelivery.courierfeeservice.models.apiresponse.ErrorResponse;
import com.fooddelivery.courierfeeservice.models.rules.ConditionType;
import com.fooddelivery.courierfeeservice.repositories.rules.WindSpeedExtraFeeRepository;
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

import static com.fooddelivery.courierfeeservice.controller.exceptions.ApiExceptionErrorMessage.INVALID_WIND_SEED_RANGE_ERROR_MESSAGE;

@RestController
@RequestMapping("/api/WSEF")
public class WSEFController {
    private final WindSpeedExtraFeeRepository windSpeedExtraFeeRepository;

    public WSEFController(WindSpeedExtraFeeRepository windSpeedExtraFeeRepository) {
        this.windSpeedExtraFeeRepository = windSpeedExtraFeeRepository;
    }

    @PostMapping
    public ResponseEntity<Object> insertWSEF(@RequestBody WindSpeedExtraFeeEntity windSpeedExtraFeeEntity) {
        if (hasInvalidWindSpeedRangeBasedOnOtherConditions(windSpeedExtraFeeEntity))
            return ResponseEntity.badRequest().body(new ErrorResponse(INVALID_WIND_SEED_RANGE_ERROR_MESSAGE, ApiExceptionErrorType.INVALID_WIND_SPEED_RANGE.name()));
        return CrudService.createEntity(windSpeedExtraFeeRepository, windSpeedExtraFeeEntity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> readWSEF(@PathVariable Long id) {
        return CrudService.readEntity(windSpeedExtraFeeRepository, id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateWSEF(@PathVariable Long id, @RequestBody WindSpeedExtraFeeEntity windSpeedExtraFeeEntity) {
        if (hasInvalidWindSpeedRangeBasedOnOtherConditions(windSpeedExtraFeeEntity))
            return ResponseEntity.badRequest().body(new ErrorResponse(INVALID_WIND_SEED_RANGE_ERROR_MESSAGE, ApiExceptionErrorType.INVALID_WIND_SPEED_RANGE.name()));
        return CrudService.updateEntity(windSpeedExtraFeeRepository, id, windSpeedExtraFeeEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteWSEF(@PathVariable Long id) {
        return CrudService.deleteEntity(windSpeedExtraFeeRepository, id);
    }

    private boolean hasInvalidWindSpeedRangeBasedOnOtherConditions(WindSpeedExtraFeeEntity insertableEntity) {
        if (insertableEntity.getMinWindSpeedRequirement() == null) return true;
        if (insertableEntity.getMinWindSpeedRequirement() < 0 || (insertableEntity.getMaxWindSpeedRequirement() != null && insertableEntity.getMaxWindSpeedRequirement() < 0)) return true;
        if (ConditionType.DISALLOWED.equals(insertableEntity.getConditionType()) && insertableEntity.getMaxWindSpeedRequirement() != null) return true;
        List<WindSpeedExtraFeeEntity> latestFeesExcludingReplaced = windSpeedExtraFeeRepository.findLatestByExcludingConditionType(insertableEntity.getConditionType());
        for (WindSpeedExtraFeeEntity windSpeedExtraFeeEntity : latestFeesExcludingReplaced) {
            if (isRangesOverlapping(windSpeedExtraFeeEntity.getMinWindSpeedRequirement(), windSpeedExtraFeeEntity.getMaxWindSpeedRequirement(), insertableEntity.getMinWindSpeedRequirement(), insertableEntity.getMaxWindSpeedRequirement())) return true;
        }
        latestFeesExcludingReplaced.add(insertableEntity);
        latestFeesExcludingReplaced.sort(Comparator.comparing(WindSpeedExtraFeeEntity::getConditionType));
        return hasGapsInRanges(latestFeesExcludingReplaced);
    }

    private boolean isRangesOverlapping(Double min1, Double max1, Double min2, Double max2) {
        if (max1 == null && max2 == null) return true;

        if (max1 == null) return min1 <= max2;

        if (max2 == null) return min2 <= max1;

        return min1 <= max2 && max1 >= min2;
    }

    private boolean hasGapsInRanges(List<WindSpeedExtraFeeEntity> entities) {
        for (int i = 0; i < entities.size()-1; i++) {
            if (entities.get(i).getMaxWindSpeedRequirement()+1 != entities.get(i+1).getMinWindSpeedRequirement()) return true;
        }
        return false;
    }
}

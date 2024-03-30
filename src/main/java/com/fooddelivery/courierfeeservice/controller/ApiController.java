package com.fooddelivery.courierfeeservice.controller;

import com.fooddelivery.courierfeeservice.entities.rules.AirTemperatureExtraFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.BaseRuleEntity;
import com.fooddelivery.courierfeeservice.entities.rules.RegionalBaseFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.WeatherPhenomenonExtraFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.WindSpeedExtraFeeEntity;
import com.fooddelivery.courierfeeservice.models.FeeInputRequest;
import com.fooddelivery.courierfeeservice.repositories.rules.AirTemperatureExtraFeeRepository;
import com.fooddelivery.courierfeeservice.repositories.rules.RegionalBaseFeeRepository;
import com.fooddelivery.courierfeeservice.repositories.rules.WeatherPhenomenonExtraFeeRepository;
import com.fooddelivery.courierfeeservice.repositories.rules.WindSpeedExtraFeeRepository;
import com.fooddelivery.courierfeeservice.services.FeeCalculationService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final FeeCalculationService feeCalculationService;
    private final RegionalBaseFeeRepository regionalBaseFeeRepository;
    private final AirTemperatureExtraFeeRepository airTemperatureExtraFeeRepository;
    private final WindSpeedExtraFeeRepository windSpeedExtraFeeRepository;
    private final WeatherPhenomenonExtraFeeRepository weatherPhenomenonExtraFeeRepository;

    public ApiController(FeeCalculationService feeCalculationService, RegionalBaseFeeRepository regionalBaseFeeRepository, AirTemperatureExtraFeeRepository airTemperatureExtraFeeRepository, WindSpeedExtraFeeRepository windSpeedExtraFeeRepository, WeatherPhenomenonExtraFeeRepository weatherPhenomenonExtraFeeRepository) {
        this.feeCalculationService = feeCalculationService;
        this.regionalBaseFeeRepository = regionalBaseFeeRepository;
        this.airTemperatureExtraFeeRepository = airTemperatureExtraFeeRepository;
        this.windSpeedExtraFeeRepository = windSpeedExtraFeeRepository;
        this.weatherPhenomenonExtraFeeRepository = weatherPhenomenonExtraFeeRepository;
    }

    /* TODO change the Response Object to have a JSON style
    * - Error -
    * {
    *   type: "error_type",
    *   error: "error_message"
    * }
    *
    * - Calculated fee -
    *
    * {
    *   amount: 123
    * }
    */

    @PostMapping("/calculateFee")
    public ResponseEntity<Object> calculateFee(@RequestBody FeeInputRequest input) {
        try {
            return ResponseEntity.ok().body(feeCalculationService.calculateTotalFee(input.getCity(), input.getVehicleType(), input.getTimestamp()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/insertATEF")
    public ResponseEntity<Object> insertATEF(@RequestBody AirTemperatureExtraFeeEntity airTemperatureExtraFeeEntity) {
        return insertEntity(airTemperatureExtraFeeRepository, airTemperatureExtraFeeEntity);
    }

    @PutMapping("/insertWSEF")
    public ResponseEntity<Object> insertWSEF(@RequestBody WindSpeedExtraFeeEntity windSpeedExtraFeeEntity) {
        return insertEntity(windSpeedExtraFeeRepository, windSpeedExtraFeeEntity);
    }

    @PutMapping("/insertWPEF")
    public ResponseEntity<Object> insertWPEF(@RequestBody WeatherPhenomenonExtraFeeEntity weatherPhenomenonExtraFeeEntity) {
        return insertEntity(weatherPhenomenonExtraFeeRepository, weatherPhenomenonExtraFeeEntity);
    }

    @PutMapping("/insertRBF")
    public ResponseEntity<Object> insertRBF(@RequestBody RegionalBaseFeeEntity regionalBaseFeeEntity) {
        return insertEntity(regionalBaseFeeRepository, regionalBaseFeeEntity);
    }

    private <T extends BaseRuleEntity> ResponseEntity<Object> insertEntity(JpaRepository<T, ?> repository, T entity) {
        try {
            entity.setId(null);
            entity.setTimestamp(Instant.now().getEpochSecond());
            return ResponseEntity.ok().body(repository.save(entity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

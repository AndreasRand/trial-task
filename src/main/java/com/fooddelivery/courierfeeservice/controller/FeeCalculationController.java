package com.fooddelivery.courierfeeservice.controller;

import com.fooddelivery.courierfeeservice.controller.exceptions.ApiException;
import com.fooddelivery.courierfeeservice.models.apiresponse.ErrorResponse;
import com.fooddelivery.courierfeeservice.models.requestinput.FeeInputRequest;
import com.fooddelivery.courierfeeservice.services.feecalculation.FeeCalculationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calculateFee")
public class FeeCalculationController {
    private final FeeCalculationService feeCalculationService;

    public FeeCalculationController(FeeCalculationService feeCalculationService) {
        this.feeCalculationService = feeCalculationService;
    }

    @PostMapping
    public ResponseEntity<Object> calculateFee(@RequestBody FeeInputRequest input) {
        try {
            return ResponseEntity.ok().body(feeCalculationService.calculateTotalFee(input.getCity(), input.getVehicleType(), input.getTimestamp()));
        } catch (ApiException e) {
            return (switch (e.getType()) {
                case WEATHER_DATA_ABSENT, REGIONAL_BASE_FEE_ABSENT -> ResponseEntity.internalServerError();
                default -> ResponseEntity.badRequest();
            }).body(new ErrorResponse(e.getMessage(), e.getType().name()));
        }
    }
}

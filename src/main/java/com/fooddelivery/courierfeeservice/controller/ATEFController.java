package com.fooddelivery.courierfeeservice.controller;

import com.fooddelivery.courierfeeservice.entities.rules.AirTemperatureExtraFeeEntity;
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

@RestController
@RequestMapping("/api/ATEF")
public class ATEFController {
    private final AirTemperatureExtraFeeRepository airTemperatureExtraFeeRepository;

    public ATEFController(AirTemperatureExtraFeeRepository airTemperatureExtraFeeRepository) {
        this.airTemperatureExtraFeeRepository = airTemperatureExtraFeeRepository;
    }

    @PostMapping
    public ResponseEntity<Object> createATEF(@RequestBody AirTemperatureExtraFeeEntity airTemperatureExtraFeeEntity) {
        return CrudService.createEntity(airTemperatureExtraFeeRepository, airTemperatureExtraFeeEntity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> readATEF(@PathVariable Long id) {
        return CrudService.readEntity(airTemperatureExtraFeeRepository, id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateATEF(@PathVariable Long id, @RequestBody AirTemperatureExtraFeeEntity airTemperatureExtraFeeEntity) {
        return CrudService.updateEntity(airTemperatureExtraFeeRepository, id, airTemperatureExtraFeeEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteATEF(@PathVariable Long id) {
        return CrudService.deleteEntity(airTemperatureExtraFeeRepository, id);
    }
}

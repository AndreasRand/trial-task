package com.fooddelivery.courierfeeservice.controller;

import com.fooddelivery.courierfeeservice.entities.rules.AirTemperatureExtraFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.WeatherPhenomenonExtraFeeEntity;
import com.fooddelivery.courierfeeservice.repositories.rules.WeatherPhenomenonExtraFeeRepository;
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
@RequestMapping("/api/WPEF")
public class WPEFController {
    private final WeatherPhenomenonExtraFeeRepository weatherPhenomenonExtraFeeRepository;

    public WPEFController(WeatherPhenomenonExtraFeeRepository weatherPhenomenonExtraFeeRepository) {
        this.weatherPhenomenonExtraFeeRepository = weatherPhenomenonExtraFeeRepository;
    }

    @PostMapping
    public ResponseEntity<Object> createWPEF(@RequestBody WeatherPhenomenonExtraFeeEntity weatherPhenomenonExtraFeeEntity) {
        return CrudService.createEntity(weatherPhenomenonExtraFeeRepository, weatherPhenomenonExtraFeeEntity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> readWPEF(@PathVariable Long id) {
        return CrudService.readEntity(weatherPhenomenonExtraFeeRepository, id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateWPEF(@PathVariable Long id, @RequestBody WeatherPhenomenonExtraFeeEntity weatherPhenomenonExtraFeeEntity) {
        return CrudService.updateEntity(weatherPhenomenonExtraFeeRepository, id, weatherPhenomenonExtraFeeEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteWPEF(@PathVariable Long id) {
        return CrudService.deleteEntity(weatherPhenomenonExtraFeeRepository, id);
    }
}

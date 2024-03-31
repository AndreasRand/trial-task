package com.fooddelivery.courierfeeservice.controller;

import com.fooddelivery.courierfeeservice.entities.rules.WindSpeedExtraFeeEntity;
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

@RestController
@RequestMapping("/api/WSEF")
public class WSEFController {
    private final WindSpeedExtraFeeRepository windSpeedExtraFeeRepository;

    public WSEFController(WindSpeedExtraFeeRepository windSpeedExtraFeeRepository) {
        this.windSpeedExtraFeeRepository = windSpeedExtraFeeRepository;
    }

    @PostMapping
    public ResponseEntity<Object> insertWSEF(@RequestBody WindSpeedExtraFeeEntity windSpeedExtraFeeEntity) {
        return CrudService.createEntity(windSpeedExtraFeeRepository, windSpeedExtraFeeEntity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> readWSEF(@PathVariable Long id) {
        return CrudService.readEntity(windSpeedExtraFeeRepository, id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateWSEF(@PathVariable Long id, @RequestBody WindSpeedExtraFeeEntity windSpeedExtraFeeEntity) {
        return CrudService.updateEntity(windSpeedExtraFeeRepository, id, windSpeedExtraFeeEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteWSEF(@PathVariable Long id) {
        return CrudService.deleteEntity(windSpeedExtraFeeRepository, id);
    }
}

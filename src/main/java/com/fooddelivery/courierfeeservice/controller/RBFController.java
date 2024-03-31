package com.fooddelivery.courierfeeservice.controller;

import com.fooddelivery.courierfeeservice.entities.rules.RegionalBaseFeeEntity;
import com.fooddelivery.courierfeeservice.repositories.rules.RegionalBaseFeeRepository;
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
@RequestMapping("/api/RBF")
public class RBFController {
    private final RegionalBaseFeeRepository regionalBaseFeeRepository;

    public RBFController(RegionalBaseFeeRepository regionalBaseFeeRepository) {
        this.regionalBaseFeeRepository = regionalBaseFeeRepository;
    }

    @PostMapping
    public ResponseEntity<Object> createRBF(@RequestBody RegionalBaseFeeEntity regionalBaseFeeEntity) {
        return CrudService.createEntity(regionalBaseFeeRepository, regionalBaseFeeEntity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> readRBF(@PathVariable Long id) {
        return CrudService.readEntity(regionalBaseFeeRepository, id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRBF(@PathVariable Long id, @RequestBody RegionalBaseFeeEntity regionalBaseFeeEntity) {
        return CrudService.updateEntity(regionalBaseFeeRepository, id, regionalBaseFeeEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRBF(@PathVariable Long id) {
        return CrudService.deleteEntity(regionalBaseFeeRepository, id);
    }
}

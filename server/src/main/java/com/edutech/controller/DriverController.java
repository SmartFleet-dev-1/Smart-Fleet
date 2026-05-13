package com.edutech.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edutech.dto.DriverDTO;
import com.edutech.entity.Driver;
import com.edutech.service.DriverService;

@RestController
@RequestMapping("/api/drivers")
@CrossOrigin(origins = "*")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping
    public ResponseEntity<DriverDTO> addDriver(@Valid @RequestBody Driver driver) {
        DriverDTO savedDriver = driverService.addDriver(driver);
        return ResponseEntity.ok(savedDriver);
    }

    @GetMapping
    public ResponseEntity<List<DriverDTO>> getAllDrivers() {
        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDTO> getDriverById(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.getDriverById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverDTO> updateDriver(
            @PathVariable Long id,
            @Valid @RequestBody Driver driver) {

        return ResponseEntity.ok(driverService.updateDriver(id, driver));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
        return ResponseEntity.ok("Driver deleted successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<List<DriverDTO>> searchByName(
            @RequestParam String name) {

        return ResponseEntity.ok(driverService.searchByName(name));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<DriverDTO>> filterByAvailability(
            @RequestParam String status) {

        return ResponseEntity.ok(driverService.filterByAvailability(status));
    }

    @GetMapping("/sort")
    public ResponseEntity<List<DriverDTO>> sortByExperience(
            @RequestParam(defaultValue = "asc") String order) {

        return ResponseEntity.ok(driverService.sortByExperience(order));
    }
}
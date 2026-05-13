package com.edutech.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edutech.dto.VehicleDTO;
import com.edutech.entity.Vehicle;
import com.edutech.service.VehicleService;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(origins = "*")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleDTO> addVehicle(@Valid @RequestBody Vehicle vehicle) {
        VehicleDTO savedVehicle = vehicleService.addVehicle(vehicle);
        return ResponseEntity.ok(savedVehicle);
    }

    @GetMapping
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleDTO> updateVehicle(
            @PathVariable Long id,
            @Valid @RequestBody Vehicle vehicle) {

        return ResponseEntity.ok(vehicleService.updateVehicle(id, vehicle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok("Vehicle deleted successfully");
    }

    @GetMapping("/search/number")
    public ResponseEntity<VehicleDTO> searchByVehicleNumber(
            @RequestParam String vehicleNumber) {

        return ResponseEntity.ok(vehicleService.searchByVehicleNumber(vehicleNumber));
    }

    @GetMapping("/search/brand")
    public ResponseEntity<List<VehicleDTO>> searchByBrand(
            @RequestParam String brand) {

        return ResponseEntity.ok(vehicleService.searchByBrand(brand));
    }

    @GetMapping("/filter/status")
    public ResponseEntity<List<VehicleDTO>> filterByStatus(
            @RequestParam String status) {

        return ResponseEntity.ok(vehicleService.filterByStatus(status));
    }

    @GetMapping("/sort/year")
    public ResponseEntity<List<VehicleDTO>> sortByManufacturingYear(
            @RequestParam(defaultValue = "asc") String order) {

        return ResponseEntity.ok(vehicleService.sortByManufacturingYear(order));
    }

    @GetMapping("/sort/mileage")
    public ResponseEntity<List<VehicleDTO>> sortByMileage(
            @RequestParam(defaultValue = "asc") String order) {

        return ResponseEntity.ok(vehicleService.sortByMileage(order));
    }

    @PutMapping("/{vehicleId}/assign-driver/{driverId}")
    public ResponseEntity<VehicleDTO> assignDriver(
            @PathVariable Long vehicleId,
            @PathVariable Long driverId) {

        return ResponseEntity.ok(vehicleService.assignDriver(vehicleId, driverId));
    }
}
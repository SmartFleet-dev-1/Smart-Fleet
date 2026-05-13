package com.edutech.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edutech.dto.InsuranceDTO;
import com.edutech.entity.Insurance;
import com.edutech.service.InsuranceService;

@RestController
@RequestMapping("/api/insurance")
@CrossOrigin(origins = "*")
public class InsuranceController {

    @Autowired
    private InsuranceService insuranceService;

    @PostMapping
    public ResponseEntity<InsuranceDTO> addInsurance(
            @Valid @RequestBody Insurance insurance,
            @RequestParam Long vehicleId) {

        return ResponseEntity.ok(
                insuranceService.addInsurance(insurance, vehicleId)
        );
    }

    @GetMapping
    public ResponseEntity<List<InsuranceDTO>> getAllInsurance() {
        return ResponseEntity.ok(insuranceService.getAllInsurance());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceDTO> getInsuranceById(@PathVariable Long id) {
        return ResponseEntity.ok(insuranceService.getInsuranceById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsuranceDTO> updateInsurance(
            @PathVariable Long id,
            @Valid @RequestBody Insurance insurance,
            @RequestParam Long vehicleId) {

        return ResponseEntity.ok(
                insuranceService.updateInsurance(id, insurance, vehicleId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInsurance(@PathVariable Long id) {
        insuranceService.deleteInsurance(id);
        return ResponseEntity.ok("Insurance deleted successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<List<InsuranceDTO>> searchByProviderName(
            @RequestParam String providerName) {

        return ResponseEntity.ok(
                insuranceService.searchByProviderName(providerName)
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<List<InsuranceDTO>> filterByCoverageType(
            @RequestParam String coverageType) {

        return ResponseEntity.ok(
                insuranceService.filterByCoverageType(coverageType)
        );
    }

    @GetMapping("/sort")
    public ResponseEntity<List<InsuranceDTO>> sortByPremiumAmount(
            @RequestParam(defaultValue = "asc") String order) {

        return ResponseEntity.ok(
                insuranceService.sortByPremiumAmount(order)
        );
    }

    @GetMapping("/expiring")
    public ResponseEntity<List<InsuranceDTO>> getExpiringInsurance() {
        return ResponseEntity.ok(insuranceService.getExpiringInsurance());
    }
}
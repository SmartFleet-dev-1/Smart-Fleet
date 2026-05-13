package com.edutech.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edutech.dto.MaintenanceRecordDTO;
import com.edutech.entity.MaintenanceRecord;
import com.edutech.service.MaintenanceRecordService;

@RestController
@RequestMapping("/api/maintenance")
@CrossOrigin(origins = "*")
public class MaintenanceRecordController {

    @Autowired
    private MaintenanceRecordService maintenanceRecordService;

    @PostMapping
    public ResponseEntity<MaintenanceRecordDTO> addRecord(
            @Valid @RequestBody MaintenanceRecord maintenanceRecord,
            @RequestParam Long vehicleId) {

        return ResponseEntity.ok(
                maintenanceRecordService.addRecord(maintenanceRecord, vehicleId)
        );
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceRecordDTO>> getAllRecords() {
        return ResponseEntity.ok(maintenanceRecordService.getAllRecords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceRecordDTO> getRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(maintenanceRecordService.getRecordById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceRecordDTO> updateRecord(
            @PathVariable Long id,
            @Valid @RequestBody MaintenanceRecord maintenanceRecord,
            @RequestParam Long vehicleId) {

        return ResponseEntity.ok(
                maintenanceRecordService.updateRecord(id, maintenanceRecord, vehicleId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecord(@PathVariable Long id) {
        maintenanceRecordService.deleteRecord(id);
        return ResponseEntity.ok("Maintenance record deleted successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<List<MaintenanceRecordDTO>> searchByServiceType(
            @RequestParam String serviceType) {

        return ResponseEntity.ok(
                maintenanceRecordService.searchByServiceType(serviceType)
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<List<MaintenanceRecordDTO>> filterByServiceDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        return ResponseEntity.ok(
                maintenanceRecordService.filterByServiceDate(date)
        );
    }

    @GetMapping("/sort")
    public ResponseEntity<List<MaintenanceRecordDTO>> sortByServiceCost(
            @RequestParam(defaultValue = "asc") String order) {

        return ResponseEntity.ok(
                maintenanceRecordService.sortByServiceCost(order)
        );
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<MaintenanceRecordDTO>> getRecordsByVehicle(
            @PathVariable Long vehicleId) {

        return ResponseEntity.ok(
                maintenanceRecordService.getRecordsByVehicle(vehicleId)
        );
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<MaintenanceRecordDTO>> getUpcomingServices() {
        return ResponseEntity.ok(maintenanceRecordService.getUpcomingServices());
    }
}
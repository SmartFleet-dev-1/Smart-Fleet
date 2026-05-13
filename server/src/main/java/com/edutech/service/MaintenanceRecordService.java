package com.edutech.service;

import com.edutech.dto.MaintenanceRecordDTO;
import com.edutech.entity.MaintenanceRecord;
import com.edutech.entity.Vehicle;
import com.edutech.exception.InvalidDataException;
import com.edutech.exception.ResourceNotFoundException;
import com.edutech.repository.MaintenanceRecordRepository;
import com.edutech.repository.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceRecordService {

    @Autowired
    private MaintenanceRecordRepository maintenanceRecordRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public MaintenanceRecordDTO addRecord(MaintenanceRecord maintenanceRecord, Long vehicleId) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicle not found with id: " + vehicleId)
                );

        if (maintenanceRecord.getServiceDate() != null &&
                maintenanceRecord.getServiceDate().isAfter(LocalDate.now())) {
            throw new InvalidDataException("Service date cannot be in the future");
        }

        maintenanceRecord.setVehicle(vehicle);

        MaintenanceRecord savedRecord = maintenanceRecordRepository.save(maintenanceRecord);
        return convertToDTO(savedRecord);
    }

    public List<MaintenanceRecordDTO> getAllRecords() {

        return maintenanceRecordRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MaintenanceRecordDTO getRecordById(Long maintenanceId) {

        MaintenanceRecord record = maintenanceRecordRepository.findById(maintenanceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Maintenance record not found with id: " + maintenanceId)
                );

        return convertToDTO(record);
    }

    public MaintenanceRecordDTO updateRecord(Long maintenanceId, MaintenanceRecord updatedRecord, Long vehicleId) {

        MaintenanceRecord existingRecord = maintenanceRecordRepository.findById(maintenanceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Maintenance record not found with id: " + maintenanceId)
                );

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicle not found with id: " + vehicleId)
                );

        if (updatedRecord.getServiceDate() != null &&
                updatedRecord.getServiceDate().isAfter(LocalDate.now())) {
            throw new InvalidDataException("Service date cannot be in the future");
        }

        existingRecord.setServiceDate(updatedRecord.getServiceDate());
        existingRecord.setServiceType(updatedRecord.getServiceType());
        existingRecord.setServiceCenter(updatedRecord.getServiceCenter());
        existingRecord.setServiceCost(updatedRecord.getServiceCost());
        existingRecord.setNextServiceDate(updatedRecord.getNextServiceDate());
        existingRecord.setRemarks(updatedRecord.getRemarks());
        existingRecord.setVehicle(vehicle);

        MaintenanceRecord savedRecord = maintenanceRecordRepository.save(existingRecord);
        return convertToDTO(savedRecord);
    }

    public void deleteRecord(Long maintenanceId) {

        if (!maintenanceRecordRepository.existsById(maintenanceId)) {
            throw new ResourceNotFoundException("Maintenance record not found with id: " + maintenanceId);
        }

        maintenanceRecordRepository.deleteById(maintenanceId);
    }

    public List<MaintenanceRecordDTO> searchByServiceType(String serviceType) {

        return maintenanceRecordRepository.findByServiceTypeContainingIgnoreCase(serviceType)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MaintenanceRecordDTO> filterByServiceDate(LocalDate date) {

        return maintenanceRecordRepository.findByServiceDate(date)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MaintenanceRecordDTO> sortByServiceCost(String order) {

        List<MaintenanceRecord> records;

        if ("desc".equalsIgnoreCase(order)) {
            records = maintenanceRecordRepository.findAllByOrderByServiceCostDesc();
        } else {
            records = maintenanceRecordRepository.findAllByOrderByServiceCostAsc();
        }

        return records.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MaintenanceRecordDTO> getRecordsByVehicle(Long vehicleId) {

        return maintenanceRecordRepository.findByVehicle_VehicleId(vehicleId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MaintenanceRecordDTO> getUpcomingServices() {

        LocalDate today = LocalDate.now();
        LocalDate nextThirtyDays = today.plusDays(30);

        return maintenanceRecordRepository.findByNextServiceDateBetween(today, nextThirtyDays)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private MaintenanceRecordDTO convertToDTO(MaintenanceRecord record) {

        MaintenanceRecordDTO dto = new MaintenanceRecordDTO();

        dto.setMaintenanceId(record.getMaintenanceId());
        dto.setServiceDate(record.getServiceDate());
        dto.setServiceType(record.getServiceType());
        dto.setServiceCenter(record.getServiceCenter());
        dto.setServiceCost(record.getServiceCost());
        dto.setNextServiceDate(record.getNextServiceDate());
        dto.setRemarks(record.getRemarks());

        if (record.getVehicle() != null) {
            dto.setVehicleId(record.getVehicle().getVehicleId());
            dto.setVehicleNumber(record.getVehicle().getVehicleNumber());
        }

        return dto;
    }
}
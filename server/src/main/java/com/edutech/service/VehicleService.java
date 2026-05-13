package com.edutech.service;

import com.edutech.dto.VehicleDTO;
import com.edutech.entity.Driver;
import com.edutech.entity.Vehicle;
import com.edutech.exception.DuplicateResourceException;
import com.edutech.exception.ResourceNotFoundException;
import com.edutech.repository.DriverRepository;
import com.edutech.repository.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private DriverRepository driverRepository;

    public VehicleDTO addVehicle(Vehicle vehicle) {

        if (vehicleRepository.existsByVehicleNumber(vehicle.getVehicleNumber())) {
            throw new DuplicateResourceException("Vehicle number already exists");
        }

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return convertToDTO(savedVehicle);
    }

    public List<VehicleDTO> getAllVehicles() {

        return vehicleRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public VehicleDTO getVehicleById(Long vehicleId) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicle not found with id: " + vehicleId)
                );

        return convertToDTO(vehicle);
    }

    public VehicleDTO updateVehicle(Long vehicleId, Vehicle updatedVehicle) {

        Vehicle existingVehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicle not found with id: " + vehicleId)
                );

        existingVehicle.setVehicleNumber(updatedVehicle.getVehicleNumber());
        existingVehicle.setVehicleType(updatedVehicle.getVehicleType());
        existingVehicle.setBrand(updatedVehicle.getBrand());
        existingVehicle.setModel(updatedVehicle.getModel());
        existingVehicle.setManufacturingYear(updatedVehicle.getManufacturingYear());
        existingVehicle.setFuelType(updatedVehicle.getFuelType());
        existingVehicle.setMileage(updatedVehicle.getMileage());
        existingVehicle.setStatus(updatedVehicle.getStatus());

        Vehicle savedVehicle = vehicleRepository.save(existingVehicle);
        return convertToDTO(savedVehicle);
    }

    public void deleteVehicle(Long vehicleId) {

        if (!vehicleRepository.existsById(vehicleId)) {
            throw new ResourceNotFoundException("Vehicle not found with id: " + vehicleId);
        }

        vehicleRepository.deleteById(vehicleId);
    }

    public VehicleDTO searchByVehicleNumber(String vehicleNumber) {

        Vehicle vehicle = vehicleRepository.findByVehicleNumber(vehicleNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicle not found with number: " + vehicleNumber)
                );

        return convertToDTO(vehicle);
    }

    public List<VehicleDTO> searchByBrand(String brand) {

        return vehicleRepository.findByBrandContainingIgnoreCase(brand)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<VehicleDTO> filterByStatus(String status) {

        return vehicleRepository.findByStatus(status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<VehicleDTO> sortByManufacturingYear(String order) {

        List<Vehicle> vehicles;

        if ("desc".equalsIgnoreCase(order)) {
            vehicles = vehicleRepository.findAllByOrderByManufacturingYearDesc();
        } else {
            vehicles = vehicleRepository.findAllByOrderByManufacturingYearAsc();
        }

        return vehicles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<VehicleDTO> sortByMileage(String order) {

        List<Vehicle> vehicles;

        if ("desc".equalsIgnoreCase(order)) {
            vehicles = vehicleRepository.findAllByOrderByMileageDesc();
        } else {
            vehicles = vehicleRepository.findAllByOrderByMileageAsc();
        }

        return vehicles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public VehicleDTO assignDriver(Long vehicleId, Long driverId) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicle not found with id: " + vehicleId)
                );

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Driver not found with id: " + driverId)
                );

        vehicle.setDriver(driver);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return convertToDTO(savedVehicle);
    }

    private VehicleDTO convertToDTO(Vehicle vehicle) {

        VehicleDTO dto = new VehicleDTO();

        dto.setVehicleId(vehicle.getVehicleId());
        dto.setVehicleNumber(vehicle.getVehicleNumber());
        dto.setVehicleType(vehicle.getVehicleType());
        dto.setBrand(vehicle.getBrand());
        dto.setModel(vehicle.getModel());
        dto.setManufacturingYear(vehicle.getManufacturingYear());
        dto.setFuelType(vehicle.getFuelType());
        dto.setMileage(vehicle.getMileage());
        dto.setStatus(vehicle.getStatus());

        if (vehicle.getDriver() != null) {
            dto.setDriverId(vehicle.getDriver().getDriverId());
            dto.setDriverName(vehicle.getDriver().getDriverName());
        }

        return dto;
    }
}
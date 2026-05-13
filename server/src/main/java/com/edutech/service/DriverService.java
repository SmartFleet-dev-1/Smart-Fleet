package com.edutech.service;

import com.edutech.dto.DriverDTO;
import com.edutech.entity.Driver;
import com.edutech.exception.DuplicateResourceException;
import com.edutech.exception.ResourceNotFoundException;
import com.edutech.repository.DriverRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public DriverDTO addDriver(Driver driver) {

        if (driverRepository.existsByLicenseNumber(driver.getLicenseNumber())) {
            throw new DuplicateResourceException("License number already exists");
        }

        Driver savedDriver = driverRepository.save(driver);
        return convertToDTO(savedDriver);
    }

    public List<DriverDTO> getAllDrivers() {

        return driverRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DriverDTO getDriverById(Long driverId) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Driver not found with id: " + driverId)
                );

        return convertToDTO(driver);
    }

    public DriverDTO updateDriver(Long driverId, Driver updatedDriver) {

        Driver existingDriver = driverRepository.findById(driverId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Driver not found with id: " + driverId)
                );

        existingDriver.setDriverName(updatedDriver.getDriverName());
        existingDriver.setLicenseNumber(updatedDriver.getLicenseNumber());
        existingDriver.setPhoneNumber(updatedDriver.getPhoneNumber());
        existingDriver.setExperienceYears(updatedDriver.getExperienceYears());
        existingDriver.setAddress(updatedDriver.getAddress());
        existingDriver.setAvailabilityStatus(updatedDriver.getAvailabilityStatus());

        Driver savedDriver = driverRepository.save(existingDriver);
        return convertToDTO(savedDriver);
    }

    public void deleteDriver(Long driverId) {

        if (!driverRepository.existsById(driverId)) {
            throw new ResourceNotFoundException("Driver not found with id: " + driverId);
        }

        driverRepository.deleteById(driverId);
    }

    public List<DriverDTO> searchByName(String name) {

        return driverRepository.findByDriverNameContainingIgnoreCase(name)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<DriverDTO> filterByAvailability(String status) {

        return driverRepository.findByAvailabilityStatus(status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<DriverDTO> sortByExperience(String order) {

        List<Driver> drivers;

        if ("desc".equalsIgnoreCase(order)) {
            drivers = driverRepository.findAllByOrderByExperienceYearsDesc();
        } else {
            drivers = driverRepository.findAllByOrderByExperienceYearsAsc();
        }

        return drivers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private DriverDTO convertToDTO(Driver driver) {

        DriverDTO dto = new DriverDTO();

        dto.setDriverId(driver.getDriverId());
        dto.setDriverName(driver.getDriverName());
        dto.setLicenseNumber(driver.getLicenseNumber());
        dto.setPhoneNumber(driver.getPhoneNumber());
        dto.setExperienceYears(driver.getExperienceYears());
        dto.setAddress(driver.getAddress());
        dto.setAvailabilityStatus(driver.getAvailabilityStatus());

        return dto;
    }
}
package com.edutech.service;

import com.edutech.dto.InsuranceDTO;
import com.edutech.entity.Insurance;
import com.edutech.entity.Vehicle;
import com.edutech.exception.DuplicateResourceException;
import com.edutech.exception.InvalidDataException;
import com.edutech.exception.ResourceNotFoundException;
import com.edutech.repository.InsuranceRepository;
import com.edutech.repository.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsuranceService {

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public InsuranceDTO addInsurance(Insurance insurance, Long vehicleId) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicle not found with id: " + vehicleId)
                );

        if (insuranceRepository.existsByPolicyNumber(insurance.getPolicyNumber())) {
            throw new DuplicateResourceException("Policy number already exists");
        }

        if (insuranceRepository.existsByVehicle_VehicleId(vehicleId)) {
            throw new DuplicateResourceException("Vehicle already has insurance");
        }

        if (insurance.getEndDate() != null &&
                insurance.getStartDate() != null &&
                insurance.getEndDate().isBefore(insurance.getStartDate())) {
            throw new InvalidDataException("Insurance end date cannot be before start date");
        }

        insurance.setVehicle(vehicle);

        Insurance savedInsurance = insuranceRepository.save(insurance);
        return convertToDTO(savedInsurance);
    }

    public List<InsuranceDTO> getAllInsurance() {

        return insuranceRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public InsuranceDTO getInsuranceById(Long insuranceId) {

        Insurance insurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Insurance not found with id: " + insuranceId)
                );

        return convertToDTO(insurance);
    }

    public InsuranceDTO updateInsurance(Long insuranceId, Insurance updatedInsurance, Long vehicleId) {

        Insurance existingInsurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Insurance not found with id: " + insuranceId)
                );

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicle not found with id: " + vehicleId)
                );

        if (updatedInsurance.getEndDate() != null &&
                updatedInsurance.getStartDate() != null &&
                updatedInsurance.getEndDate().isBefore(updatedInsurance.getStartDate())) {
            throw new InvalidDataException("Insurance end date cannot be before start date");
        }

        existingInsurance.setProviderName(updatedInsurance.getProviderName());
        existingInsurance.setPolicyNumber(updatedInsurance.getPolicyNumber());
        existingInsurance.setStartDate(updatedInsurance.getStartDate());
        existingInsurance.setEndDate(updatedInsurance.getEndDate());
        existingInsurance.setPremiumAmount(updatedInsurance.getPremiumAmount());
        existingInsurance.setCoverageType(updatedInsurance.getCoverageType());
        existingInsurance.setVehicle(vehicle);

        Insurance savedInsurance = insuranceRepository.save(existingInsurance);
        return convertToDTO(savedInsurance);
    }

    public void deleteInsurance(Long insuranceId) {

        if (!insuranceRepository.existsById(insuranceId)) {
            throw new ResourceNotFoundException("Insurance not found with id: " + insuranceId);
        }

        insuranceRepository.deleteById(insuranceId);
    }

    public List<InsuranceDTO> searchByProviderName(String providerName) {

        return insuranceRepository.findByProviderNameContainingIgnoreCase(providerName)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<InsuranceDTO> filterByCoverageType(String coverageType) {

        return insuranceRepository.findByCoverageType(coverageType)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<InsuranceDTO> sortByPremiumAmount(String order) {

        List<Insurance> insuranceList;

        if ("desc".equalsIgnoreCase(order)) {
            insuranceList = insuranceRepository.findAllByOrderByPremiumAmountDesc();
        } else {
            insuranceList = insuranceRepository.findAllByOrderByPremiumAmountAsc();
        }

        return insuranceList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<InsuranceDTO> getExpiringInsurance() {

        LocalDate today = LocalDate.now();
        LocalDate nextThirtyDays = today.plusDays(30);

        return insuranceRepository.findByEndDateBetween(today, nextThirtyDays)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private InsuranceDTO convertToDTO(Insurance insurance) {

        InsuranceDTO dto = new InsuranceDTO();

        dto.setInsuranceId(insurance.getInsuranceId());
        dto.setProviderName(insurance.getProviderName());
        dto.setPolicyNumber(insurance.getPolicyNumber());
        dto.setStartDate(insurance.getStartDate());
        dto.setEndDate(insurance.getEndDate());
        dto.setPremiumAmount(insurance.getPremiumAmount());
        dto.setCoverageType(insurance.getCoverageType());

        if (insurance.getVehicle() != null) {
            dto.setVehicleId(insurance.getVehicle().getVehicleId());
            dto.setVehicleNumber(insurance.getVehicle().getVehicleNumber());
        }

        return dto;
    }
}
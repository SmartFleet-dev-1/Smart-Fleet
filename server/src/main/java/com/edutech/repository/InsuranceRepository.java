package com.edutech.repository;

import com.edutech.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

    List<Insurance> findByProviderNameContainingIgnoreCase(String providerName);

    List<Insurance> findByCoverageType(String coverageType);

    List<Insurance> findAllByOrderByPremiumAmountAsc();

    List<Insurance> findAllByOrderByPremiumAmountDesc();

    Optional<Insurance> findByPolicyNumber(String policyNumber);

    boolean existsByPolicyNumber(String policyNumber);

    Optional<Insurance> findByVehicle_VehicleId(Long vehicleId);

    boolean existsByVehicle_VehicleId(Long vehicleId);

    List<Insurance> findByEndDateBetween(LocalDate startDate, LocalDate endDate);

    List<Insurance> findAllByOrderByEndDateAsc();
}
package com.edutech.repository;

import com.edutech.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    List<Driver> findByDriverNameContainingIgnoreCase(String driverName);

    Optional<Driver> findByLicenseNumber(String licenseNumber);

    boolean existsByLicenseNumber(String licenseNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    List<Driver> findByAvailabilityStatus(String availabilityStatus);

    List<Driver> findByAvailabilityStatusIgnoreCase(String availabilityStatus);

    List<Driver> findAllByOrderByExperienceYearsAsc();

    List<Driver> findAllByOrderByExperienceYearsDesc();
}
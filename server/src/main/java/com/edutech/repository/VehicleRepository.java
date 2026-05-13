package com.edutech.repository;

import com.edutech.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByVehicleNumber(String vehicleNumber);

    boolean existsByVehicleNumber(String vehicleNumber);

    List<Vehicle> findByBrandContainingIgnoreCase(String brand);

    List<Vehicle> findByStatus(String status);

    List<Vehicle> findAllByOrderByManufacturingYearAsc();

    List<Vehicle> findAllByOrderByManufacturingYearDesc();

    List<Vehicle> findAllByOrderByMileageAsc();

    List<Vehicle> findAllByOrderByMileageDesc();

    List<Vehicle> findByDriver_DriverId(Long driverId);
}
package com.edutech.repository;

import com.edutech.entity.MaintenanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord, Long> {

    List<MaintenanceRecord> findByServiceTypeContainingIgnoreCase(String serviceType);

    List<MaintenanceRecord> findByServiceDate(LocalDate serviceDate);

    List<MaintenanceRecord> findAllByOrderByServiceCostAsc();

    List<MaintenanceRecord> findAllByOrderByServiceCostDesc();

    List<MaintenanceRecord> findByVehicle_VehicleId(Long vehicleId);

    List<MaintenanceRecord> findByNextServiceDateBetween(LocalDate startDate, LocalDate endDate);

    List<MaintenanceRecord> findAllByOrderByServiceDateDesc();
}
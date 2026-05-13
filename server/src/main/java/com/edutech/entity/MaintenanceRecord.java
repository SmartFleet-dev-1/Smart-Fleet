package com.edutech.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "maintenance_record")
public class MaintenanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maintenance_id")
    private Long maintenanceId;

    @NotNull
    @PastOrPresent
    @Column(name = "service_date")
    private LocalDate serviceDate;

    @NotBlank
    @Column(name = "service_type")
    private String serviceType;

    @NotBlank
    @Column(name = "service_center")
    private String serviceCenter;

    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "service_cost")
    private double serviceCost;

    @Column(name = "next_service_date")
    private LocalDate nextServiceDate;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    public MaintenanceRecord() {
    }

    public MaintenanceRecord(Long maintenanceId, LocalDate serviceDate, String serviceType,
                             String serviceCenter, double serviceCost,
                             LocalDate nextServiceDate, String remarks, Vehicle vehicle) {
        this.maintenanceId = maintenanceId;
        this.serviceDate = serviceDate;
        this.serviceType = serviceType;
        this.serviceCenter = serviceCenter;
        this.serviceCost = serviceCost;
        this.nextServiceDate = nextServiceDate;
        this.remarks = remarks;
        this.vehicle = vehicle;
    }

    public Long getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(Long maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceCenter() {
        return serviceCenter;
    }

    public void setServiceCenter(String serviceCenter) {
        this.serviceCenter = serviceCenter;
    }

    public double getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(double serviceCost) {
        this.serviceCost = serviceCost;
    }

    public LocalDate getNextServiceDate() {
        return nextServiceDate;
    }

    public void setNextServiceDate(LocalDate nextServiceDate) {
        this.nextServiceDate = nextServiceDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
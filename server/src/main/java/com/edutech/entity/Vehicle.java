package com.edutech.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Long vehicleId;

    @Column(name = "vehicle_number", unique = true)
    @NotBlank(message = "Vehicle number is required")
    private String vehicleNumber;

    @Column(name = "vehicle_type")
    @NotBlank(message = "Vehicle type is required")
    private String vehicleType;

    @Column(name = "brand")
    @NotBlank(message = "Brand is required")
    private String brand;

    @Column(name = "model")
    @NotBlank(message = "Model is required")
    private String model;

    @Column(name = "manufacturing_year")
    @Min(value = 1900, message = "Manufacturing year must be at least 1900")
    @Max(value = 2100, message = "Manufacturing year must not exceed 2100")
    private Integer manufacturingYear;

    @Column(name = "fuel_type")
    @NotBlank(message = "Fuel type is required")
    private String fuelType;

    @Column(name = "mileage")
    @Min(value = 0, message = "Mileage cannot be negative")
    private Double mileage;

    @Column(name = "status")
    @NotBlank(message = "Status is required")
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MaintenanceRecord> maintenanceRecords;

    @OneToOne(mappedBy = "vehicle", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Insurance insurance;

    public Vehicle() {
    }

    public Vehicle(Long vehicleId, String vehicleNumber, String vehicleType, String brand,
                   String model, Integer manufacturingYear, String fuelType, Double mileage,
                   String status, Driver driver, List<MaintenanceRecord> maintenanceRecords,
                   Insurance insurance) {
        this.vehicleId = vehicleId;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.brand = brand;
        this.model = model;
        this.manufacturingYear = manufacturingYear;
        this.fuelType = fuelType;
        this.mileage = mileage;
        this.status = status;
        this.driver = driver;
        this.maintenanceRecords = maintenanceRecords;
        this.insurance = insurance;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getManufacturingYear() {
        return manufacturingYear;
    }

    public void setManufacturingYear(Integer manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public List<MaintenanceRecord> getMaintenanceRecords() {
        return maintenanceRecords;
    }

    public void setMaintenanceRecords(List<MaintenanceRecord> maintenanceRecords) {
        this.maintenanceRecords = maintenanceRecords;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }
}
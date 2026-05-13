package com.edutech.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "driver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Long driverId;

    @NotBlank
    @Column(name = "driver_name")
    private String driverName;

    @NotBlank
    @Column(name = "license_number", unique = true)
    private String licenseNumber;

    @NotBlank
    @Pattern(regexp = "\\d{10}")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Min(0)
    @Column(name = "experience_years")
    private int experienceYears;

    @NotBlank
    @Column(name = "address")
    private String address;

    @NotBlank
    @Column(name = "availability_status")
    private String availabilityStatus;

    public Driver() {
    }

    public Driver(Long driverId, String driverName, String licenseNumber,
                  String phoneNumber, int experienceYears, String address,
                  String availabilityStatus) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.licenseNumber = licenseNumber;
        this.phoneNumber = phoneNumber;
        this.experienceYears = experienceYears;
        this.address = address;
        this.availabilityStatus = availabilityStatus;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
}
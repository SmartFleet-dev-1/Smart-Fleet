package com.edutech.dto;

public class DriverDTO {

    private Long driverId;
    private String driverName;
    private String licenseNumber;
    private String phoneNumber;
    private Integer experienceYears;
    private String address;
    private String availabilityStatus;

    public DriverDTO() {
    }

    public DriverDTO(Long driverId, String driverName, String licenseNumber,
                     String phoneNumber, Integer experienceYears,
                     String address, String availabilityStatus) {
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

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
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
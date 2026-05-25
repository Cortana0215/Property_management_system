package com.example.propertymanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class RepairRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String submitterName;
    private String contactPhone;
    private String facilityDescription;
    private String status; // e.g., "PENDING", "IN_PROGRESS", "RESOLVED"
    private LocalDateTime submitTime;

    public RepairRequest() {
        this.submitTime = LocalDateTime.now();
        this.status = "PENDING";
    }

    public RepairRequest(String submitterName, String contactPhone, String facilityDescription) {
        this.submitterName = submitterName;
        this.contactPhone = contactPhone;
        this.facilityDescription = facilityDescription;
        this.submitTime = LocalDateTime.now();
        this.status = "PENDING";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSubmitterName() { return submitterName; }
    public void setSubmitterName(String submitterName) { this.submitterName = submitterName; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public String getFacilityDescription() { return facilityDescription; }
    public void setFacilityDescription(String facilityDescription) { this.facilityDescription = facilityDescription; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getSubmitTime() { return submitTime; }
    public void setSubmitTime(LocalDateTime submitTime) { this.submitTime = submitTime; }
}

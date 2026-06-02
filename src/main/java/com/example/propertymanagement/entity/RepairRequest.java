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
    private String imagePath; // Resident submitted image
    
    private Long assignedStaffId;
    private String staffName;
    
    private String status; // "PENDING", "ASSIGNED", "COMPLETED"
    private LocalDateTime submitTime;
    private LocalDateTime assignTime;
    private LocalDateTime completeTime;

    private String staffFeedbackText;
    private String staffFeedbackImage;
    private String residentFeedback;
    private Integer rating; // 1-5 星

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

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public Long getAssignedStaffId() { return assignedStaffId; }
    public void setAssignedStaffId(Long assignedStaffId) { this.assignedStaffId = assignedStaffId; }

    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getSubmitTime() { return submitTime; }
    public void setSubmitTime(LocalDateTime submitTime) { this.submitTime = submitTime; }

    public LocalDateTime getAssignTime() { return assignTime; }
    public void setAssignTime(LocalDateTime assignTime) { this.assignTime = assignTime; }

    public LocalDateTime getCompleteTime() { return completeTime; }
    public void setCompleteTime(LocalDateTime completeTime) { this.completeTime = completeTime; }

    public String getStaffFeedbackText() { return staffFeedbackText; }
    public void setStaffFeedbackText(String staffFeedbackText) { this.staffFeedbackText = staffFeedbackText; }

    public String getStaffFeedbackImage() { return staffFeedbackImage; }
    public void setStaffFeedbackImage(String staffFeedbackImage) { this.staffFeedbackImage = staffFeedbackImage; }

    public String getResidentFeedback() { return residentFeedback; }
    public void setResidentFeedback(String residentFeedback) { this.residentFeedback = residentFeedback; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
}

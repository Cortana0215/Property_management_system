package com.example.propertymanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    private String submitterName;
    private String contactPhone;
    
    private String status; // "PENDING", "PROCESSING", "RESOLVED"
    private LocalDateTime submitTime;
    
    @Column(columnDefinition = "TEXT")
    private String resultFeedback;

    public Complaint() {
        this.submitTime = LocalDateTime.now();
        this.status = "PENDING";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getSubmitterName() { return submitterName; }
    public void setSubmitterName(String submitterName) { this.submitterName = submitterName; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getSubmitTime() { return submitTime; }
    public void setSubmitTime(LocalDateTime submitTime) { this.submitTime = submitTime; }

    public String getResultFeedback() { return resultFeedback; }
    public void setResultFeedback(String resultFeedback) { this.resultFeedback = resultFeedback; }
}

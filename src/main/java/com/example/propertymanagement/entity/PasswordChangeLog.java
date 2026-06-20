package com.example.propertymanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PasswordChangeLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String adminUsername;
    private Long adminId;
    private String targetUsername;
    private Long targetUserId;
    private String targetUserRole;
    private LocalDateTime changeTime;
    private String actionType;
    
    public PasswordChangeLog() {}

    public PasswordChangeLog(String adminUsername, Long adminId, String targetUsername, 
                            Long targetUserId, String targetUserRole, String actionType) {
        this.adminUsername = adminUsername;
        this.adminId = adminId;
        this.targetUsername = targetUsername;
        this.targetUserId = targetUserId;
        this.targetUserRole = targetUserRole;
        this.changeTime = LocalDateTime.now();
        this.actionType = actionType;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getAdminUsername() { return adminUsername; }
    public void setAdminUsername(String adminUsername) { this.adminUsername = adminUsername; }
    
    public Long getAdminId() { return adminId; }
    public void setAdminId(Long adminId) { this.adminId = adminId; }
    
    public String getTargetUsername() { return targetUsername; }
    public void setTargetUsername(String targetUsername) { this.targetUsername = targetUsername; }
    
    public Long getTargetUserId() { return targetUserId; }
    public void setTargetUserId(Long targetUserId) { this.targetUserId = targetUserId; }
    
    public String getTargetUserRole() { return targetUserRole; }
    public void setTargetUserRole(String targetUserRole) { this.targetUserRole = targetUserRole; }
    
    public LocalDateTime getChangeTime() { return changeTime; }
    public void setChangeTime(LocalDateTime changeTime) { this.changeTime = changeTime; }
    
    public String getActionType() { return actionType; }
    public void setActionType(String actionType) { this.actionType = actionType; }
}

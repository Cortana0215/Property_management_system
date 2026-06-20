package com.example.propertymanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance_delete_log")
public class AttendanceDeleteLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long adminId;
    private String adminUsername;
    private Long attendanceRecordId;
    private String recordUserName;
    private String recordUserRole;
    private LocalDateTime clockInTime;
    private LocalDateTime deleteTime;
    private String deleteType; // "SINGLE" 或 "BATCH"
    private Integer batchCount; // 批量删除时记录数量，单条为1
    private String remarks;
    
    public AttendanceDeleteLog() {}

    public AttendanceDeleteLog(Long adminId, String adminUsername, AttendanceRecord record, String deleteType) {
        this.adminId = adminId;
        this.adminUsername = adminUsername;
        this.attendanceRecordId = record.getId();
        this.recordUserName = record.getUserName();
        this.recordUserRole = record.getUserRole();
        this.clockInTime = record.getClockInTime();
        this.deleteTime = LocalDateTime.now();
        this.deleteType = deleteType;
        this.batchCount = 1;
    }

    public AttendanceDeleteLog(Long adminId, String adminUsername, String deleteType, Integer batchCount) {
        this.adminId = adminId;
        this.adminUsername = adminUsername;
        this.deleteType = deleteType;
        this.batchCount = batchCount;
        this.deleteTime = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getAdminId() { return adminId; }
    public void setAdminId(Long adminId) { this.adminId = adminId; }
    
    public String getAdminUsername() { return adminUsername; }
    public void setAdminUsername(String adminUsername) { this.adminUsername = adminUsername; }
    
    public Long getAttendanceRecordId() { return attendanceRecordId; }
    public void setAttendanceRecordId(Long attendanceRecordId) { this.attendanceRecordId = attendanceRecordId; }
    
    public String getRecordUserName() { return recordUserName; }
    public void setRecordUserName(String recordUserName) { this.recordUserName = recordUserName; }
    
    public String getRecordUserRole() { return recordUserRole; }
    public void setRecordUserRole(String recordUserRole) { this.recordUserRole = recordUserRole; }
    
    public LocalDateTime getClockInTime() { return clockInTime; }
    public void setClockInTime(LocalDateTime clockInTime) { this.clockInTime = clockInTime; }
    
    public LocalDateTime getDeleteTime() { return deleteTime; }
    public void setDeleteTime(LocalDateTime deleteTime) { this.deleteTime = deleteTime; }
    
    public String getDeleteType() { return deleteType; }
    public void setDeleteType(String deleteType) { this.deleteType = deleteType; }
    
    public Integer getBatchCount() { return batchCount; }
    public void setBatchCount(Integer batchCount) { this.batchCount = batchCount; }
    
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}

package com.example.propertymanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String userName;
    private String userRole; // "ADMIN" or "STAFF"

    private LocalDateTime clockInTime;
    private LocalDateTime clockOutTime;
    private String clockInStatus; // "正常", "迟到"
    private String clockOutStatus; // "正常", "早退"

    public AttendanceRecord() {}

    public AttendanceRecord(Long userId, String userName, String userRole, LocalDateTime clockInTime, String clockInStatus) {
        this.userId = userId;
        this.userName = userName;
        this.userRole = userRole;
        this.clockInTime = clockInTime;
        this.clockInStatus = clockInStatus;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserRole() { return userRole; }
    public void setUserRole(String userRole) { this.userRole = userRole; }

    public LocalDateTime getClockInTime() { return clockInTime; }
    public void setClockInTime(LocalDateTime clockInTime) { this.clockInTime = clockInTime; }

    public LocalDateTime getClockOutTime() { return clockOutTime; }
    public void setClockOutTime(LocalDateTime clockOutTime) { this.clockOutTime = clockOutTime; }

    public String getClockInStatus() { return clockInStatus; }
    public void setClockInStatus(String clockInStatus) { this.clockInStatus = clockInStatus; }

    public String getClockOutStatus() { return clockOutStatus; }
    public void setClockOutStatus(String clockOutStatus) { this.clockOutStatus = clockOutStatus; }
}

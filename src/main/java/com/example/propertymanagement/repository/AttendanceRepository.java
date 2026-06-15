package com.example.propertymanagement.repository;

import com.example.propertymanagement.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<AttendanceRecord, Long> {
    List<AttendanceRecord> findByUserIdAndUserRoleOrderByClockInTimeDesc(Long userId, String userRole);
    List<AttendanceRecord> findAllByOrderByClockInTimeDesc();
    Optional<AttendanceRecord> findTopByUserIdAndUserRoleAndClockOutTimeIsNullOrderByClockInTimeDesc(Long userId, String userRole);
}

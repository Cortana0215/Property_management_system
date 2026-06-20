package com.example.propertymanagement.repository;

import com.example.propertymanagement.entity.AttendanceDeleteLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AttendanceDeleteLogRepository extends JpaRepository<AttendanceDeleteLog, Long> {
    List<AttendanceDeleteLog> findAllByOrderByDeleteTimeDesc();
    List<AttendanceDeleteLog> findByAdminIdOrderByDeleteTimeDesc(Long adminId);
}

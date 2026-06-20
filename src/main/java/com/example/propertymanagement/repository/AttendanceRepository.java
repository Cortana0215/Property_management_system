package com.example.propertymanagement.repository;

import com.example.propertymanagement.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceRecord, Long> {
    List<AttendanceRecord> findByUserIdAndUserRoleOrderByClockInTimeDesc(Long userId, String userRole);
    List<AttendanceRecord> findAllByOrderByClockInTimeDesc();
    Optional<AttendanceRecord> findTopByUserIdAndUserRoleAndClockOutTimeIsNullOrderByClockInTimeDesc(Long userId, String userRole);
    
    // 逻辑删除相关查询（只查询未删除的记录）
    @Query("SELECT a FROM AttendanceRecord a WHERE a.deleted = false OR a.deleted IS NULL ORDER BY a.clockInTime DESC")
    List<AttendanceRecord> findAllActiveRecords();
    
    @Query("SELECT a FROM AttendanceRecord a WHERE (a.deleted = false OR a.deleted IS NULL) AND a.userId = :userId AND a.userRole = :userRole ORDER BY a.clockInTime DESC")
    List<AttendanceRecord> findActiveByUserIdAndUserRole(@Param("userId") Long userId, @Param("userRole") String userRole);
    
    // 批量逻辑删除
    @Modifying
    @Query("UPDATE AttendanceRecord a SET a.deleted = true, a.deletedTime = CURRENT_TIMESTAMP, a.deletedByAdminId = :adminId, a.deletedByAdminName = :adminName WHERE a.id IN :ids")
    int batchSoftDelete(@Param("ids") List<Long> ids, @Param("adminId") Long adminId, @Param("adminName") String adminName);
}

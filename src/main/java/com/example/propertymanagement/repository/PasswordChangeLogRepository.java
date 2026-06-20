package com.example.propertymanagement.repository;

import com.example.propertymanagement.entity.PasswordChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PasswordChangeLogRepository extends JpaRepository<PasswordChangeLog, Long> {
    List<PasswordChangeLog> findAllByOrderByChangeTimeDesc();
    List<PasswordChangeLog> findByAdminIdOrderByChangeTimeDesc(Long adminId);
    List<PasswordChangeLog> findByTargetUserIdOrderByChangeTimeDesc(Long targetUserId);
    List<PasswordChangeLog> findByTargetUserRoleOrderByChangeTimeDesc(String role);
}

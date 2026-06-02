package com.example.propertymanagement.repository;

import com.example.propertymanagement.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByTargetRoleInOrderByCreateTimeDesc(List<String> roles);
}

package com.example.propertymanagement.repository;

import com.example.propertymanagement.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByTargetRoleInOrderByCreateTimeDesc(List<String> roles);
}

package com.example.propertymanagement.repository;

import com.example.propertymanagement.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findBySubmitterNameOrderBySubmitTimeDesc(String submitterName);
}

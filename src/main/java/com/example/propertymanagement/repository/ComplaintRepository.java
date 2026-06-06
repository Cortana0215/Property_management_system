package com.example.propertymanagement.repository;

import com.example.propertymanagement.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findBySubmitterNameOrderBySubmitTimeDesc(String submitterName);
}

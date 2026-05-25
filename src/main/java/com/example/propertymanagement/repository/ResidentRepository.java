package com.example.propertymanagement.repository;

import com.example.propertymanagement.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {
    Resident findByUsername(String username);
}

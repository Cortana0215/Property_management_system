package com.example.propertymanagement.repository;

import com.example.propertymanagement.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}

package com.example.propertymanagement.repository;

import com.example.propertymanagement.entity.Building;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class BuildingRepositoryTest {

    @Autowired
    private BuildingRepository buildingRepository;

    @Test
    void testSaveAndFindById() {
        Building building = new Building();
        building.setName("A栋_" + System.currentTimeMillis());
        building.setDescription("20层楼");
        
        Building saved = buildingRepository.save(building);
        
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getDescription()).isEqualTo("20层楼");
    }

    @Test
    void testFindAll() {
        long initialCount = buildingRepository.count();
        
        Building building1 = new Building();
        building1.setName("C栋_" + System.currentTimeMillis());
        buildingRepository.save(building1);
        
        Building building2 = new Building();
        building2.setName("D栋_" + System.currentTimeMillis());
        buildingRepository.save(building2);
        
        assertThat(buildingRepository.findAll().size()).isEqualTo((int) initialCount + 2);
    }

    @Test
    void testDelete() {
        Building building = new Building();
        building.setName("E栋_" + System.currentTimeMillis());
        Building saved = buildingRepository.save(building);
        
        buildingRepository.deleteById(saved.getId());
        
        assertThat(buildingRepository.findById(saved.getId())).isEmpty();
    }
}

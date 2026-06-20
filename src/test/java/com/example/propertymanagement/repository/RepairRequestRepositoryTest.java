package com.example.propertymanagement.repository;

import com.example.propertymanagement.entity.RepairRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class RepairRequestRepositoryTest {

    @Autowired
    private RepairRequestRepository repairRequestRepository;

    @Test
    void testSaveAndFindById() {
        RepairRequest request = new RepairRequest();
        request.setSubmitterName("张三");
        request.setContactPhone("13800138000");
        request.setFacilityDescription("水龙头坏了");
        
        RepairRequest saved = repairRequestRepository.save(request);
        
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getSubmitterName()).isEqualTo("张三");
        assertThat(saved.getStatus()).isEqualTo("PENDING");
    }

    @Test
    void testFindAll() {
        long initialCount = repairRequestRepository.count();
        
        RepairRequest request1 = new RepairRequest();
        request1.setSubmitterName("李四");
        request1.setContactPhone("13900139000");
        request1.setFacilityDescription("灯不亮");
        repairRequestRepository.save(request1);
        
        assertThat(repairRequestRepository.findAll().size()).isEqualTo((int) initialCount + 1);
    }

    @Test
    void testDelete() {
        RepairRequest request = new RepairRequest();
        request.setSubmitterName("王五");
        request.setContactPhone("13700137000");
        request.setFacilityDescription("门锁坏了");
        RepairRequest saved = repairRequestRepository.save(request);
        
        repairRequestRepository.deleteById(saved.getId());
        
        assertThat(repairRequestRepository.findById(saved.getId())).isEmpty();
    }
}

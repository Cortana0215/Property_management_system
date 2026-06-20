package com.example.propertymanagement.repository;

import com.example.propertymanagement.entity.Resident;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ResidentRepositoryTest {

    @Autowired
    private ResidentRepository residentRepository;

    @Test
    void testSaveAndFindById() {
        Resident resident = new Resident();
        resident.setName("张三");
        resident.setPhone("13800138000");
        resident.setUsername("zhangsan_" + System.currentTimeMillis());
        resident.setPassword("password123");
        
        Resident saved = residentRepository.save(resident);
        
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("张三");
    }

    @Test
    void testFindByUsername() {
        String uniqueUsername = "lisi_" + System.currentTimeMillis();
        Resident resident = new Resident();
        resident.setName("李四");
        resident.setUsername(uniqueUsername);
        resident.setPassword("password");
        
        residentRepository.save(resident);
        
        Resident found = residentRepository.findByUsername(uniqueUsername);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("李四");
    }

    @Test
    void testFindByUsernameNotFound() {
        Resident found = residentRepository.findByUsername("nonexistent_" + System.currentTimeMillis());
        assertThat(found).isNull();
    }

    @Test
    void testCount() {
        long initialCount = residentRepository.count();
        
        Resident resident = new Resident();
        resident.setName("测试");
        resident.setUsername("test_" + System.currentTimeMillis());
        residentRepository.save(resident);
        
        assertThat(residentRepository.count()).isEqualTo(initialCount + 1);
    }

    @Test
    void testDelete() {
        Resident resident = new Resident();
        resident.setName("王五");
        resident.setUsername("wangwu_" + System.currentTimeMillis());
        Resident saved = residentRepository.save(resident);
        
        residentRepository.deleteById(saved.getId());
        
        assertThat(residentRepository.findById(saved.getId())).isEmpty();
    }
}

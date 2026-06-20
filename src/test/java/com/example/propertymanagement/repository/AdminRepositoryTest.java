package com.example.propertymanagement.repository;

import com.example.propertymanagement.entity.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    void testSaveAndFindById() {
        Admin admin = new Admin();
        admin.setUsername("admin_" + System.currentTimeMillis());
        admin.setPassword("password123");
        
        Admin saved = adminRepository.save(admin);
        
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void testFindByUsername() {
        String uniqueUsername = "findmin_" + System.currentTimeMillis();
        Admin admin = new Admin();
        admin.setUsername(uniqueUsername);
        admin.setPassword("password");
        
        adminRepository.save(admin);
        
        Admin found = adminRepository.findByUsername(uniqueUsername);
        assertThat(found).isNotNull();
    }

    @Test
    void testCount() {
        long initialCount = adminRepository.count();
        
        Admin admin = new Admin();
        admin.setUsername("testadmin_" + System.currentTimeMillis());
        adminRepository.save(admin);
        
        assertThat(adminRepository.count()).isEqualTo(initialCount + 1);
    }
}

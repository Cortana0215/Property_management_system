package com.example.propertymanagement.repository;

import com.example.propertymanagement.entity.Staff;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class StaffRepositoryTest {

    @Autowired
    private StaffRepository staffRepository;

    @Test
    void testSaveAndFindById() {
        Staff staff = new Staff();
        staff.setName("张三");
        staff.setPhone("13800138000");
        staff.setUsername("zhangsan_" + System.currentTimeMillis());
        staff.setPassword("password123");
        
        Staff saved = staffRepository.save(staff);
        
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("张三");
    }

    @Test
    void testFindByUsername() {
        String uniqueUsername = "lisi_" + System.currentTimeMillis();
        Staff staff = new Staff();
        staff.setName("李四");
        staff.setUsername(uniqueUsername);
        staff.setPassword("password");
        
        staffRepository.save(staff);
        
        Staff found = staffRepository.findByUsername(uniqueUsername);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("李四");
    }

    @Test
    void testFindByUsernameNotFound() {
        Staff found = staffRepository.findByUsername("nonexistent_" + System.currentTimeMillis());
        assertThat(found).isNull();
    }

    @Test
    void testCount() {
        long initialCount = staffRepository.count();
        
        Staff staff = new Staff();
        staff.setName("测试");
        staff.setUsername("test_" + System.currentTimeMillis());
        staffRepository.save(staff);
        
        assertThat(staffRepository.count()).isEqualTo(initialCount + 1);
    }

    @Test
    void testDelete() {
        Staff staff = new Staff();
        staff.setName("王五");
        staff.setUsername("wangwu_" + System.currentTimeMillis());
        Staff saved = staffRepository.save(staff);
        
        staffRepository.deleteById(saved.getId());
        
        assertThat(staffRepository.findById(saved.getId())).isEmpty();
    }
}

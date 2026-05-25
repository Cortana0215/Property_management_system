package com.example.propertymanagement.component;

import com.example.propertymanagement.entity.Admin;
import com.example.propertymanagement.entity.Resident;
import com.example.propertymanagement.repository.AdminRepository;
import com.example.propertymanagement.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ResidentRepository residentRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize default admin if none exists
        if (adminRepository.count() == 0) {
            adminRepository.save(new Admin("admin", "123456"));
            System.out.println("Default admin user created: admin / 123456");
        }

        // Initialize a default resident for testing if none exists
        if (residentRepository.count() == 0) {
            residentRepository.save(new Resident("测试业主", "13800138000", "1栋1单元101", "user", "123456"));
            System.out.println("Default resident user created: user / 123456");
        }
    }
}

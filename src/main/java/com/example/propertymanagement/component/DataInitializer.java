package com.example.propertymanagement.component;

import com.example.propertymanagement.entity.*;
import com.example.propertymanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public void run(String... args) throws Exception {
        // 1. 初始化唯一超级管理员 (避免系统无法登录)
        if (adminRepository.count() == 0) {
            adminRepository.save(new Admin("admin", "123456"));
            System.out.println("系统初始化：已创建默认管理员账户 (admin/123456)");
        }

        // 2. 初始化一些基础楼栋结构，方便测试（如果不想要也可以删掉，这里保留最基础的一栋楼）
        if (buildingRepository.count() == 0) {
            Building b = buildingRepository.save(new Building("1号楼", "普通住宅"));
            for (int f = 1; f <= 3; f++) {
                for (int r = 1; r <= 2; r++) {
                    roomRepository.save(new Room(f + "0" + r, "1单元", 90.0, b));
                }
            }
            System.out.println("系统初始化：已创建基础楼栋框架");
        }
        
        // 移除了其他所有预设的职工、住户、报修和投诉数据。
    }
}

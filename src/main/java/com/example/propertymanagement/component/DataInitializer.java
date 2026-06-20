package com.example.propertymanagement.component;

import com.example.propertymanagement.entity.*;
import com.example.propertymanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 1. 初始化管理员账户 (支持不同权限级别测试)
        if (adminRepository.count() == 0) {
            // 超级管理员
            Admin superAdmin = new Admin("admin", passwordEncoder.encode("123456"));
            adminRepository.save(superAdmin);
            System.out.println("系统初始化：已创建超级管理员 (admin/123456)");
            
            // 普通管理员
            Admin normalAdmin = new Admin("admin2", passwordEncoder.encode("654321"));
            adminRepository.save(normalAdmin);
            System.out.println("系统初始化：已创建普通管理员 (admin2/654321)");
            
            // 审计管理员
            Admin auditAdmin = new Admin("audit", passwordEncoder.encode("audit123"));
            adminRepository.save(auditAdmin);
            System.out.println("系统初始化：已创建审计管理员 (audit/audit123)");
        }

        // 2. 初始化楼栋结构
        if (buildingRepository.count() == 0) {
            // 创建多栋楼用于测试
            Building building1 = buildingRepository.save(new Building("1号楼", "普通住宅楼，共6层"));
            Building building2 = buildingRepository.save(new Building("2号楼", "小高层住宅楼，共12层"));
            Building building3 = buildingRepository.save(new Building("3号楼", "商业办公楼"));
            System.out.println("系统初始化：已创建3栋测试楼栋");

            // 为每栋楼创建房间
            createRoomsForBuilding(building1, 6, 4);  // 6层，每层4户
            createRoomsForBuilding(building2, 12, 4); // 12层，每层4户
            createRoomsForBuilding(building3, 8, 6);  // 8层，每层6户
            System.out.println("系统初始化：已创建房间数据");
        }

        // 3. 初始化住户账户（不同状态）
        if (residentRepository.count() == 0) {
            // 获取第一个楼栋的房间用于分配
            Building building1 = buildingRepository.findAll().get(0);
            
            // 正常住户
            Resident resident1 = new Resident("张三", "13800138001", "1-101", "zhangsan", passwordEncoder.encode("user123"));
            residentRepository.save(resident1);
            
            Resident resident2 = new Resident("李四", "13800138002", "1-102", "lisi", passwordEncoder.encode("user456"));
            residentRepository.save(resident2);
            
            Resident resident3 = new Resident("王五", "13800138003", "1-201", "wangwu", passwordEncoder.encode("user789"));
            residentRepository.save(resident3);
            
            Resident resident4 = new Resident("赵六", "13800138004", "2-302", "zhaoliu", passwordEncoder.encode("zhaoliu123"));
            residentRepository.save(resident4);
            
            Resident resident5 = new Resident("钱七", "13800138005", "2-501", "qianqi", passwordEncoder.encode("qianqi456"));
            residentRepository.save(resident5);
            
            System.out.println("系统初始化：已创建5个住户账户");
        }

        // 4. 初始化职工账户（不同角色）
        if (staffRepository.count() == 0) {
            Staff staff1 = new Staff("孙磊", "13900139001", "staff", passwordEncoder.encode("staff123"));
            staffRepository.save(staff1);
            
            Staff staff2 = new Staff("周婷", "13900139002", "repairman", passwordEncoder.encode("repair123"));
            staffRepository.save(staff2);
            
            Staff staff3 = new Staff("吴刚", "13900139003", "security", passwordEncoder.encode("security123"));
            staffRepository.save(staff3);
            
            Staff staff4 = new Staff("郑雪", "13900139004", "manager", passwordEncoder.encode("manager123"));
            staffRepository.save(staff4);
            
            System.out.println("系统初始化：已创建4个职工账户");
        }
    }

    private void createRoomsForBuilding(Building building, int floors, int roomsPerFloor) {
        for (int floor = 1; floor <= floors; floor++) {
            for (int room = 1; room <= roomsPerFloor; room++) {
                String roomNumber = String.format("%d%02d", floor, room);
                roomRepository.save(new Room(roomNumber, "1单元", 85.0 + (floor * 2), building));
            }
        }
    }
}

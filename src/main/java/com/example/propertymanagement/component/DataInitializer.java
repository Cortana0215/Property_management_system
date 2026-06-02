package com.example.propertymanagement.component;

import com.example.propertymanagement.entity.*;
import com.example.propertymanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RepairRequestRepository repairRequestRepository;

    @Override
    public void run(String... args) throws Exception {
        Random random = new Random();

        // 1. 初始化管理员
        if (adminRepository.count() == 0) {
            adminRepository.save(new Admin("admin", "123456"));
            adminRepository.save(new Admin("root", "123456"));
            System.out.println("成功创建 2 个预设管理员账户");
        }

        // 2. 初始化楼栋与房屋
        if (buildingRepository.count() == 0) {
            String[] bNames = {"荷花园", "兰花阁", "梅花轩", "竹林居", "菊香苑"};
            for (int i = 0; i < bNames.length; i++) {
                Building b = buildingRepository.save(new Building((i + 1) + "栋 (" + bNames[i] + ")", "高层住宅"));
                for (int u = 1; u <= 2; u++) { // 2个单元
                    for (int f = 1; f <= 5; f++) { // 5层
                        for (int r = 1; r <= 2; r++) { // 每层2户
                            String roomNo = f + "0" + r;
                            roomRepository.save(new Room(roomNo, u + "单元", 89.5 + (random.nextDouble() * 40), b));
                        }
                    }
                }
            }
            System.out.println("成功创建 5 栋楼及 100 间预设房屋");
        }

        // 3. 初始化物业职工 (10名)
        if (staffRepository.count() <= 1) {
            String[] staffNames = {"张师傅", "李师傅", "王工", "赵工", "孙师傅", "周工", "吴师傅", "郑工", "陈师傅", "褚工"};
            for (int i = 0; i < staffNames.length; i++) {
                String username = "staff" + (i + 1);
                if (staffRepository.findByUsername(username) == null) {
                    staffRepository.save(new Staff(staffNames[i], "1390000000" + i, username, "123456"));
                }
            }
            System.out.println("成功创建 10 名预设物业职工");
        }

        // 4. 初始化住户 (10名)
        if (residentRepository.count() <= 1) {
            String[] resNames = {"赵业主", "钱业主", "孙业主", "李业主", "周业主", "吴业主", "郑业主", "王业主", "冯业主", "陈业主"};
            List<Room> allRooms = roomRepository.findAll();
            for (int i = 0; i < resNames.length; i++) {
                String username = "user" + (i + 1);
                if (residentRepository.findByUsername(username) == null) {
                    Resident resident = new Resident(resNames[i], "1380000000" + i, "暂无", username, "123456");
                    // 随机绑定一个房间
                    if (!allRooms.isEmpty()) {
                        Room room = allRooms.get(i * 2); // 确保每个分配不同的房
                        resident.addRoom(room);
                        room.setStatus("OCCUPIED");
                        roomRepository.save(room);
                    }
                    residentRepository.save(resident);
                }
            }
            System.out.println("成功创建 10 名预设住户并完成房屋绑定");
        }

        // 5. 初始化报修工单 (10个)
        if (repairRequestRepository.count() == 0) {
            String[] problems = {"路灯不亮", "楼道声控灯故障", "水管漏水", "电梯异响", "门禁卡失效", "绿化带杂草过多", "垃圾桶破损", "墙皮脱落", "公共洗手间堵塞", "监控摄像头黑屏"};
            List<Resident> residents = residentRepository.findAll();
            List<Staff> staffs = staffRepository.findAll();
            
            for (int i = 0; i < 10; i++) {
                Resident r = residents.get(i % residents.size());
                RepairRequest req = new RepairRequest(r.getName(), r.getPhone(), problems[i]);
                req.setSubmitTime(LocalDateTime.now().minusDays(random.nextInt(10)));
                
                if (i < 4) {
                    req.setStatus("PENDING");
                } else if (i < 7) {
                    req.setStatus("ASSIGNED");
                    Staff s = staffs.get(random.nextInt(staffs.size()));
                    req.setAssignedStaffId(s.getId());
                    req.setStaffName(s.getName());
                    req.setAssignTime(req.getSubmitTime().plusHours(2));
                } else {
                    req.setStatus("COMPLETED");
                    Staff s = staffs.get(random.nextInt(staffs.size()));
                    req.setAssignedStaffId(s.getId());
                    req.setStaffName(s.getName());
                    req.setAssignTime(req.getSubmitTime().plusHours(1));
                    req.setCompleteTime(req.getAssignTime().plusDays(1));
                    req.setStaffFeedbackText("已现场核实并修复完成。");
                }
                repairRequestRepository.save(req);
            }
            System.out.println("成功创建 10 条预设报修工单");
        }
    }
}

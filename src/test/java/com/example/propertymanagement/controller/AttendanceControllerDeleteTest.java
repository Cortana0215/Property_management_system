package com.example.propertymanagement.controller;

import com.example.propertymanagement.entity.Admin;
import com.example.propertymanagement.entity.AttendanceRecord;
import com.example.propertymanagement.entity.Staff;
import com.example.propertymanagement.repository.AdminRepository;
import com.example.propertymanagement.repository.AttendanceRepository;
import com.example.propertymanagement.repository.AttendanceDeleteLogRepository;
import com.example.propertymanagement.repository.StaffRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AttendanceControllerDeleteTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AttendanceDeleteLogRepository deleteLogRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private MockHttpSession adminSession;
    private MockHttpSession staffSession;
    private Admin testAdmin;
    private Staff testStaff;

    @BeforeEach
    void setUp() throws Exception {
        // 清理数据
        deleteLogRepository.deleteAll();
        attendanceRepository.deleteAll();
        
        // 创建测试管理员
        testAdmin = new Admin();
        testAdmin.setUsername("deleteadmin_" + System.currentTimeMillis());
        testAdmin.setPassword(passwordEncoder.encode("admin123"));
        testAdmin = adminRepository.save(testAdmin);
        
        // 创建测试职工
        testStaff = new Staff();
        testStaff.setName("测试职工");
        testStaff.setPhone("13900000000");
        testStaff.setUsername("deletestaff_" + System.currentTimeMillis());
        testStaff.setPassword(passwordEncoder.encode("staff123"));
        testStaff = staffRepository.save(testStaff);
        
        // 创建管理员会话
        adminSession = new MockHttpSession();
        adminSession.setAttribute("loggedInAdmin", testAdmin);
        adminSession.setAttribute("role", "ADMIN");
        
        // 创建职工会话
        staffSession = new MockHttpSession();
        staffSession.setAttribute("loggedInStaff", testStaff);
        staffSession.setAttribute("role", "STAFF");
    }

    @Test
    void testDeleteRecord_Success() throws Exception {
        // 创建测试考勤记录
        AttendanceRecord record = new AttendanceRecord(testStaff.getId(), "测试职工", "STAFF", 
                LocalDateTime.now(), "正常");
        record = attendanceRepository.save(record);
        
        // 执行删除
        MvcResult result = mockMvc.perform(post("/attendance/delete/" + record.getId())
                .session(adminSession))
                .andExpect(status().isOk())
                .andReturn();
        
        String response = result.getResponse().getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
        
        assertTrue((Boolean) responseMap.get("success"));
        
        // 验证记录已标记为删除
        AttendanceRecord deletedRecord = attendanceRepository.findById(record.getId()).orElseThrow();
        assertTrue(deletedRecord.getDeleted());
        assertEquals(testAdmin.getId(), deletedRecord.getDeletedByAdminId());
        assertEquals(testAdmin.getUsername(), deletedRecord.getDeletedByAdminName());
        
        // 验证操作日志已创建
        assertEquals(1, deleteLogRepository.count());
    }

    @Test
    void testDeleteRecord_NotFound() throws Exception {
        MvcResult result = mockMvc.perform(post("/attendance/delete/99999")
                .session(adminSession))
                .andExpect(status().isBadRequest())
                .andReturn();
        
        String response = result.getResponse().getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
        
        assertFalse((Boolean) responseMap.get("success"));
    }

    @Test
    void testDeleteRecord_AlreadyDeleted() throws Exception {
        // 创建并删除考勤记录
        AttendanceRecord record = new AttendanceRecord(testStaff.getId(), "测试职工", "STAFF", 
                LocalDateTime.now(), "正常");
        record.setDeleted(true);
        record.setDeletedTime(LocalDateTime.now());
        record = attendanceRepository.save(record);
        
        MvcResult result = mockMvc.perform(post("/attendance/delete/" + record.getId())
                .session(adminSession))
                .andExpect(status().isBadRequest())
                .andReturn();
        
        String response = result.getResponse().getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
        
        assertFalse((Boolean) responseMap.get("success"));
    }

    @Test
    void testDeleteRecord_NoPermission_StaffRole() throws Exception {
        // 创建测试考勤记录
        AttendanceRecord record = new AttendanceRecord(testStaff.getId(), "测试职工", "STAFF", 
                LocalDateTime.now(), "正常");
        record = attendanceRepository.save(record);
        
        // 职工尝试删除
        MvcResult result = mockMvc.perform(post("/attendance/delete/" + record.getId())
                .session(staffSession))
                .andExpect(status().isForbidden())
                .andReturn();
        
        String response = result.getResponse().getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
        
        assertFalse((Boolean) responseMap.get("success"));
    }

    @Test
    void testDeleteRecord_NoSession() throws Exception {
        // 无会话尝试删除（Spring Security默认返回403）
        MvcResult result = mockMvc.perform(post("/attendance/delete/1"))
                .andExpect(status().isForbidden())
                .andReturn();
        
        // 验证返回错误
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    void testBatchDelete_Success() throws Exception {
        // 创建多条测试考勤记录
        AttendanceRecord record1 = new AttendanceRecord(testStaff.getId(), "测试职工", "STAFF", 
                LocalDateTime.now(), "正常");
        AttendanceRecord record2 = new AttendanceRecord(testStaff.getId(), "测试职工", "STAFF", 
                LocalDateTime.now().minusHours(1), "迟到");
        AttendanceRecord record3 = new AttendanceRecord(testStaff.getId(), "测试职工", "STAFF", 
                LocalDateTime.now().minusDays(1), "正常");
        
        List<AttendanceRecord> records = attendanceRepository.saveAll(Arrays.asList(record1, record2, record3));
        List<Long> ids = Arrays.asList(records.get(0).getId(), records.get(1).getId());
        
        // 执行批量删除
        MvcResult result = mockMvc.perform(post("/attendance/delete-batch")
                .session(adminSession)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("ids=" + ids.get(0) + "&ids=" + ids.get(1)))
                .andExpect(status().isOk())
                .andReturn();
        
        String response = result.getResponse().getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
        
        assertTrue((Boolean) responseMap.get("success"));
        assertEquals(2, responseMap.get("deletedCount"));
        
        // 验证记录已标记为删除
        for (Long id : ids) {
            AttendanceRecord deletedRecord = attendanceRepository.findById(id).orElseThrow();
            assertTrue(deletedRecord.getDeleted());
            assertEquals(testAdmin.getId(), deletedRecord.getDeletedByAdminId());
        }
        
        // 验证未被删除的记录仍然正常
        AttendanceRecord notDeleted = attendanceRepository.findById(records.get(2).getId()).orElseThrow();
        assertFalse(notDeleted.getDeleted());
        
        // 验证操作日志已创建
        assertEquals(1, deleteLogRepository.count());
    }

    @Test
    void testBatchDelete_EmptyIds() throws Exception {
        MvcResult result = mockMvc.perform(post("/attendance/delete-batch")
                .session(adminSession)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("ids="))
                .andExpect(status().isBadRequest())
                .andReturn();
        
        String response = result.getResponse().getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
        
        assertFalse((Boolean) responseMap.get("success"));
    }

    @Test
    void testBatchDelete_NoPermission_StaffRole() throws Exception {
        MvcResult result = mockMvc.perform(post("/attendance/delete-batch")
                .session(staffSession)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("ids=1&ids=2"))
                .andExpect(status().isForbidden())
                .andReturn();
        
        String response = result.getResponse().getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
        
        assertFalse((Boolean) responseMap.get("success"));
    }

    @Test
    void testAttendanceRecordsPage_Admin() throws Exception {
        // 创建测试考勤记录
        AttendanceRecord record1 = new AttendanceRecord(testStaff.getId(), "测试职工", "STAFF", 
                LocalDateTime.now(), "正常");
        AttendanceRecord record2 = new AttendanceRecord(testStaff.getId(), "测试职工", "STAFF", 
                LocalDateTime.now().minusDays(1), "迟到");
        AttendanceRecord deletedRecord = new AttendanceRecord(testAdmin.getId(), testAdmin.getUsername(), "ADMIN", 
                LocalDateTime.now().minusDays(2), "正常");
        deletedRecord.setDeleted(true);
        deletedRecord.setDeletedTime(LocalDateTime.now());
        deletedRecord.setDeletedByAdminId(testAdmin.getId());
        
        attendanceRepository.saveAll(Arrays.asList(record1, record2, deletedRecord));
        
        // 管理员只能看到未删除的记录
        MvcResult result = mockMvc.perform(get("/attendance/records")
                .session(adminSession))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/attendance"))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("测试职工"));
        assertEquals(2, attendanceRepository.findAllActiveRecords().size());
    }

    @Test
    void testAttendanceRecordsPage_Staff() throws Exception {
        // 创建测试考勤记录
        AttendanceRecord record = new AttendanceRecord(testStaff.getId(), "测试职工", "STAFF", 
                LocalDateTime.now(), "正常");
        AttendanceRecord deletedRecord = new AttendanceRecord(testStaff.getId(), "测试职工", "STAFF", 
                LocalDateTime.now().minusDays(1), "正常");
        deletedRecord.setDeleted(true);
        
        attendanceRepository.saveAll(Arrays.asList(record, deletedRecord));
        
        // 职工只能看到自己的未删除记录
        MvcResult result = mockMvc.perform(get("/attendance/records")
                .session(staffSession))
                .andExpect(status().isOk())
                .andExpect(view().name("staff/attendance"))
                .andReturn();
        
        List<AttendanceRecord> activeRecords = attendanceRepository.findActiveByUserIdAndUserRole(
                testStaff.getId(), "STAFF");
        assertEquals(1, activeRecords.size());
    }
}

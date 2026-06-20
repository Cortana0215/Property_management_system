package com.example.propertymanagement.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class AttendanceRecordTest {

    @Test
    void testDefaultConstructor() {
        AttendanceRecord record = new AttendanceRecord();
        assertNotNull(record);
        // 验证默认值
        assertFalse(record.getDeleted());
    }

    @Test
    void testParameterizedConstructor() {
        LocalDateTime clockIn = LocalDateTime.now();
        AttendanceRecord record = new AttendanceRecord(1L, "张三", "STAFF", clockIn, "正常");
        
        assertEquals(1L, record.getUserId());
        assertEquals("张三", record.getUserName());
        assertEquals("STAFF", record.getUserRole());
        assertEquals(clockIn, record.getClockInTime());
        assertEquals("正常", record.getClockInStatus());
        // 验证构造函数中设置的默认值
        assertFalse(record.getDeleted());
    }

    @Test
    void testSetAndGetId() {
        AttendanceRecord record = new AttendanceRecord();
        record.setId(1L);
        assertEquals(1L, record.getId());
    }

    @Test
    void testSetAndGetUserId() {
        AttendanceRecord record = new AttendanceRecord();
        record.setUserId(5L);
        assertEquals(5L, record.getUserId());
    }

    @Test
    void testSetAndGetUserName() {
        AttendanceRecord record = new AttendanceRecord();
        record.setUserName("李四");
        assertEquals("李四", record.getUserName());
    }

    @Test
    void testSetAndGetUserRole() {
        AttendanceRecord record = new AttendanceRecord();
        record.setUserRole("ADMIN");
        assertEquals("ADMIN", record.getUserRole());
    }

    @Test
    void testSetAndGetClockInTime() {
        AttendanceRecord record = new AttendanceRecord();
        LocalDateTime time = LocalDateTime.now();
        record.setClockInTime(time);
        assertEquals(time, record.getClockInTime());
    }

    @Test
    void testSetAndGetClockOutTime() {
        AttendanceRecord record = new AttendanceRecord();
        LocalDateTime time = LocalDateTime.now();
        record.setClockOutTime(time);
        assertEquals(time, record.getClockOutTime());
    }

    @Test
    void testSetAndGetClockInStatus() {
        AttendanceRecord record = new AttendanceRecord();
        record.setClockInStatus("迟到");
        assertEquals("迟到", record.getClockInStatus());
    }

    @Test
    void testSetAndGetClockOutStatus() {
        AttendanceRecord record = new AttendanceRecord();
        record.setClockOutStatus("早退");
        assertEquals("早退", record.getClockOutStatus());
    }

    // 逻辑删除字段测试
    @Test
    void testSetAndGetDeleted() {
        AttendanceRecord record = new AttendanceRecord();
        assertFalse(record.getDeleted()); // 默认值
        
        record.setDeleted(true);
        assertTrue(record.getDeleted());
        
        record.setDeleted(false);
        assertFalse(record.getDeleted());
    }

    @Test
    void testSetAndGetDeletedTime() {
        AttendanceRecord record = new AttendanceRecord();
        LocalDateTime time = LocalDateTime.now();
        record.setDeletedTime(time);
        assertEquals(time, record.getDeletedTime());
    }

    @Test
    void testSetAndGetDeletedByAdminId() {
        AttendanceRecord record = new AttendanceRecord();
        record.setDeletedByAdminId(10L);
        assertEquals(10L, record.getDeletedByAdminId());
    }

    @Test
    void testSetAndGetDeletedByAdminName() {
        AttendanceRecord record = new AttendanceRecord();
        record.setDeletedByAdminName("admin");
        assertEquals("admin", record.getDeletedByAdminName());
    }

    @Test
    void testLogicalDeletionFlow() {
        // 测试逻辑删除流程
        AttendanceRecord record = new AttendanceRecord(1L, "张三", "STAFF", LocalDateTime.now(), "正常");
        record.setId(100L);
        
        // 初始状态
        assertFalse(record.getDeleted());
        assertNull(record.getDeletedTime());
        assertNull(record.getDeletedByAdminId());
        assertNull(record.getDeletedByAdminName());
        
        // 执行逻辑删除
        LocalDateTime deleteTime = LocalDateTime.now();
        record.setDeleted(true);
        record.setDeletedTime(deleteTime);
        record.setDeletedByAdminId(5L);
        record.setDeletedByAdminName("testadmin");
        
        // 验证删除状态
        assertTrue(record.getDeleted());
        assertEquals(deleteTime, record.getDeletedTime());
        assertEquals(5L, record.getDeletedByAdminId());
        assertEquals("testadmin", record.getDeletedByAdminName());
    }
}

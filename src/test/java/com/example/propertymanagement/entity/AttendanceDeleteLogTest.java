package com.example.propertymanagement.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class AttendanceDeleteLogTest {

    @Test
    void testDefaultConstructor() {
        AttendanceDeleteLog log = new AttendanceDeleteLog();
        assertNotNull(log);
    }

    @Test
    void testSingleDeleteConstructor() {
        LocalDateTime clockIn = LocalDateTime.now();
        AttendanceRecord record = new AttendanceRecord(1L, "张三", "STAFF", clockIn, "正常");
        record.setId(100L);
        
        AttendanceDeleteLog log = new AttendanceDeleteLog(10L, "admin", record, "SINGLE");
        
        assertEquals(10L, log.getAdminId());
        assertEquals("admin", log.getAdminUsername());
        assertEquals(100L, log.getAttendanceRecordId());
        assertEquals("张三", log.getRecordUserName());
        assertEquals("STAFF", log.getRecordUserRole());
        assertEquals(clockIn, log.getClockInTime());
        assertEquals("SINGLE", log.getDeleteType());
        assertEquals(1, log.getBatchCount());
        assertNotNull(log.getDeleteTime());
    }

    @Test
    void testBatchDeleteConstructor() {
        AttendanceDeleteLog log = new AttendanceDeleteLog(10L, "admin", "BATCH", 5);
        
        assertEquals(10L, log.getAdminId());
        assertEquals("admin", log.getAdminUsername());
        assertEquals("BATCH", log.getDeleteType());
        assertEquals(5, log.getBatchCount());
        assertNotNull(log.getDeleteTime());
    }

    @Test
    void testSetAndGetId() {
        AttendanceDeleteLog log = new AttendanceDeleteLog();
        log.setId(1L);
        assertEquals(1L, log.getId());
    }

    @Test
    void testSetAndGetAdminId() {
        AttendanceDeleteLog log = new AttendanceDeleteLog();
        log.setAdminId(5L);
        assertEquals(5L, log.getAdminId());
    }

    @Test
    void testSetAndGetAdminUsername() {
        AttendanceDeleteLog log = new AttendanceDeleteLog();
        log.setAdminUsername("testadmin");
        assertEquals("testadmin", log.getAdminUsername());
    }

    @Test
    void testSetAndGetAttendanceRecordId() {
        AttendanceDeleteLog log = new AttendanceDeleteLog();
        log.setAttendanceRecordId(99L);
        assertEquals(99L, log.getAttendanceRecordId());
    }

    @Test
    void testSetAndGetRecordUserName() {
        AttendanceDeleteLog log = new AttendanceDeleteLog();
        log.setRecordUserName("李四");
        assertEquals("李四", log.getRecordUserName());
    }

    @Test
    void testSetAndGetRecordUserRole() {
        AttendanceDeleteLog log = new AttendanceDeleteLog();
        log.setRecordUserRole("ADMIN");
        assertEquals("ADMIN", log.getRecordUserRole());
    }

    @Test
    void testSetAndGetClockInTime() {
        AttendanceDeleteLog log = new AttendanceDeleteLog();
        LocalDateTime time = LocalDateTime.now();
        log.setClockInTime(time);
        assertEquals(time, log.getClockInTime());
    }

    @Test
    void testSetAndGetDeleteTime() {
        AttendanceDeleteLog log = new AttendanceDeleteLog();
        LocalDateTime time = LocalDateTime.now();
        log.setDeleteTime(time);
        assertEquals(time, log.getDeleteTime());
    }

    @Test
    void testSetAndGetDeleteType() {
        AttendanceDeleteLog log = new AttendanceDeleteLog();
        log.setDeleteType("BATCH");
        assertEquals("BATCH", log.getDeleteType());
    }

    @Test
    void testSetAndGetBatchCount() {
        AttendanceDeleteLog log = new AttendanceDeleteLog();
        log.setBatchCount(10);
        assertEquals(10, log.getBatchCount());
    }

    @Test
    void testSetAndGetRemarks() {
        AttendanceDeleteLog log = new AttendanceDeleteLog();
        log.setRemarks("测试删除备注");
        assertEquals("测试删除备注", log.getRemarks());
    }
}

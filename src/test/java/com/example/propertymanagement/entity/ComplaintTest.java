package com.example.propertymanagement.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ComplaintTest {

    @Test
    void testDefaultConstructor() {
        Complaint complaint = new Complaint();
        assertNotNull(complaint);
        assertEquals("PENDING", complaint.getStatus());
        assertNotNull(complaint.getSubmitTime());
    }

    @Test
    void testSetAndGetId() {
        Complaint complaint = new Complaint();
        complaint.setId(1L);
        assertEquals(1L, complaint.getId());
    }

    @Test
    void testSetAndGetTitle() {
        Complaint complaint = new Complaint();
        complaint.setTitle("噪音投诉");
        assertEquals("噪音投诉", complaint.getTitle());
    }

    @Test
    void testSetAndGetContent() {
        Complaint complaint = new Complaint();
        complaint.setContent("楼上太吵了");
        assertEquals("楼上太吵了", complaint.getContent());
    }

    @Test
    void testSetAndGetSubmitterName() {
        Complaint complaint = new Complaint();
        complaint.setSubmitterName("张三");
        assertEquals("张三", complaint.getSubmitterName());
    }

    @Test
    void testSetAndGetContactPhone() {
        Complaint complaint = new Complaint();
        complaint.setContactPhone("13800138000");
        assertEquals("13800138000", complaint.getContactPhone());
    }

    @Test
    void testSetAndGetStatus() {
        Complaint complaint = new Complaint();
        complaint.setStatus("RESOLVED");
        assertEquals("RESOLVED", complaint.getStatus());
    }

    @Test
    void testSetAndGetSubmitTime() {
        Complaint complaint = new Complaint();
        LocalDateTime time = LocalDateTime.now();
        complaint.setSubmitTime(time);
        assertEquals(time, complaint.getSubmitTime());
    }

    @Test
    void testSetAndGetResultFeedback() {
        Complaint complaint = new Complaint();
        complaint.setResultFeedback("已处理完成");
        assertEquals("已处理完成", complaint.getResultFeedback());
    }
}

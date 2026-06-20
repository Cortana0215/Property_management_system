package com.example.propertymanagement.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class RepairRequestTest {

    @Test
    void testDefaultConstructor() {
        RepairRequest request = new RepairRequest();
        assertNotNull(request);
        assertEquals("PENDING", request.getStatus());
        assertNotNull(request.getSubmitTime());
    }

    @Test
    void testParameterizedConstructor() {
        RepairRequest request = new RepairRequest("张三", "13800138000", "水龙头坏了");
        assertEquals("张三", request.getSubmitterName());
        assertEquals("13800138000", request.getContactPhone());
        assertEquals("水龙头坏了", request.getFacilityDescription());
        assertEquals("PENDING", request.getStatus());
    }

    @Test
    void testSetAndGetId() {
        RepairRequest request = new RepairRequest();
        request.setId(1L);
        assertEquals(1L, request.getId());
    }

    @Test
    void testSetAndGetSubmitterName() {
        RepairRequest request = new RepairRequest();
        request.setSubmitterName("李四");
        assertEquals("李四", request.getSubmitterName());
    }

    @Test
    void testSetAndGetContactPhone() {
        RepairRequest request = new RepairRequest();
        request.setContactPhone("13900139000");
        assertEquals("13900139000", request.getContactPhone());
    }

    @Test
    void testSetAndGetFacilityDescription() {
        RepairRequest request = new RepairRequest();
        request.setFacilityDescription("马桶堵塞");
        assertEquals("马桶堵塞", request.getFacilityDescription());
    }

    @Test
    void testSetAndGetImagePath() {
        RepairRequest request = new RepairRequest();
        request.setImagePath("/uploads/repair.jpg");
        assertEquals("/uploads/repair.jpg", request.getImagePath());
    }

    @Test
    void testSetAndGetStatus() {
        RepairRequest request = new RepairRequest();
        request.setStatus("ASSIGNED");
        assertEquals("ASSIGNED", request.getStatus());
    }

    @Test
    void testSetAndGetAssignedStaffId() {
        RepairRequest request = new RepairRequest();
        request.setAssignedStaffId(5L);
        assertEquals(5L, request.getAssignedStaffId());
    }

    @Test
    void testSetAndGetStaffName() {
        RepairRequest request = new RepairRequest();
        request.setStaffName("王师傅");
        assertEquals("王师傅", request.getStaffName());
    }

    @Test
    void testSetAndGetSubmitTime() {
        RepairRequest request = new RepairRequest();
        LocalDateTime time = LocalDateTime.now();
        request.setSubmitTime(time);
        assertEquals(time, request.getSubmitTime());
    }

    @Test
    void testSetAndGetAssignTime() {
        RepairRequest request = new RepairRequest();
        LocalDateTime time = LocalDateTime.now();
        request.setAssignTime(time);
        assertEquals(time, request.getAssignTime());
    }

    @Test
    void testSetAndGetCompleteTime() {
        RepairRequest request = new RepairRequest();
        LocalDateTime time = LocalDateTime.now();
        request.setCompleteTime(time);
        assertEquals(time, request.getCompleteTime());
    }

    @Test
    void testSetAndGetStaffFeedbackText() {
        RepairRequest request = new RepairRequest();
        request.setStaffFeedbackText("已修复");
        assertEquals("已修复", request.getStaffFeedbackText());
    }

    @Test
    void testSetAndGetStaffFeedbackImage() {
        RepairRequest request = new RepairRequest();
        request.setStaffFeedbackImage("/uploads/feedback.jpg");
        assertEquals("/uploads/feedback.jpg", request.getStaffFeedbackImage());
    }

    @Test
    void testSetAndGetResidentFeedback() {
        RepairRequest request = new RepairRequest();
        request.setResidentFeedback("服务很好");
        assertEquals("服务很好", request.getResidentFeedback());
    }

    @Test
    void testSetAndGetRating() {
        RepairRequest request = new RepairRequest();
        request.setRating(5);
        assertEquals(5, request.getRating());
    }
}

package com.example.propertymanagement.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class NoticeTest {

    @Test
    void testDefaultConstructor() {
        Notice notice = new Notice();
        assertNotNull(notice);
        assertNotNull(notice.getCreateTime());
    }

    @Test
    void testParameterizedConstructor() {
        Notice notice = new Notice("标题", "内容", "ALL", "管理员");
        assertEquals("标题", notice.getTitle());
        assertEquals("内容", notice.getContent());
        assertEquals("ALL", notice.getTargetRole());
        assertEquals("管理员", notice.getPublisherName());
        assertNotNull(notice.getCreateTime());
    }

    @Test
    void testSetAndGetId() {
        Notice notice = new Notice();
        notice.setId(1L);
        assertEquals(1L, notice.getId());
    }

    @Test
    void testSetAndGetTitle() {
        Notice notice = new Notice();
        notice.setTitle("新公告");
        assertEquals("新公告", notice.getTitle());
    }

    @Test
    void testSetAndGetContent() {
        Notice notice = new Notice();
        notice.setContent("公告内容");
        assertEquals("公告内容", notice.getContent());
    }

    @Test
    void testSetAndGetTargetRole() {
        Notice notice = new Notice();
        notice.setTargetRole("RESIDENT");
        assertEquals("RESIDENT", notice.getTargetRole());
    }

    @Test
    void testSetAndGetCreateTime() {
        Notice notice = new Notice();
        LocalDateTime time = LocalDateTime.now();
        notice.setCreateTime(time);
        assertEquals(time, notice.getCreateTime());
    }

    @Test
    void testSetAndGetPublisherName() {
        Notice notice = new Notice();
        notice.setPublisherName("管理员");
        assertEquals("管理员", notice.getPublisherName());
    }
}

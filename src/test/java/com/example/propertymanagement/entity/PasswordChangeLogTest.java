package com.example.propertymanagement.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class PasswordChangeLogTest {

    @Test
    void testDefaultConstructor() {
        PasswordChangeLog log = new PasswordChangeLog();
        assertNotNull(log);
    }

    @Test
    void testParameterizedConstructor() {
        LocalDateTime beforeCreate = LocalDateTime.now();
        PasswordChangeLog log = new PasswordChangeLog(
            "admin", 1L, "user1", 2L, "RESIDENT", "RESET_PASSWORD"
        );
        LocalDateTime afterCreate = LocalDateTime.now();
        
        assertEquals("admin", log.getAdminUsername());
        assertEquals(1L, log.getAdminId());
        assertEquals("user1", log.getTargetUsername());
        assertEquals(2L, log.getTargetUserId());
        assertEquals("RESIDENT", log.getTargetUserRole());
        assertEquals("RESET_PASSWORD", log.getActionType());
        assertNotNull(log.getChangeTime());
        assertTrue(log.getChangeTime().isAfter(beforeCreate.minusSeconds(1)));
        assertTrue(log.getChangeTime().isBefore(afterCreate.plusSeconds(1)));
    }

    @Test
    void testSetAndGetId() {
        PasswordChangeLog log = new PasswordChangeLog();
        log.setId(100L);
        assertEquals(100L, log.getId());
    }

    @Test
    void testSetAndGetAdminUsername() {
        PasswordChangeLog log = new PasswordChangeLog();
        log.setAdminUsername("superadmin");
        assertEquals("superadmin", log.getAdminUsername());
    }

    @Test
    void testSetAndGetAdminId() {
        PasswordChangeLog log = new PasswordChangeLog();
        log.setAdminId(5L);
        assertEquals(5L, log.getAdminId());
    }

    @Test
    void testSetAndGetTargetUsername() {
        PasswordChangeLog log = new PasswordChangeLog();
        log.setTargetUsername("targetuser");
        assertEquals("targetuser", log.getTargetUsername());
    }

    @Test
    void testSetAndGetTargetUserId() {
        PasswordChangeLog log = new PasswordChangeLog();
        log.setTargetUserId(10L);
        assertEquals(10L, log.getTargetUserId());
    }

    @Test
    void testSetAndGetTargetUserRole() {
        PasswordChangeLog log = new PasswordChangeLog();
        log.setTargetUserRole("STAFF");
        assertEquals("STAFF", log.getTargetUserRole());
    }

    @Test
    void testSetAndGetChangeTime() {
        PasswordChangeLog log = new PasswordChangeLog();
        LocalDateTime now = LocalDateTime.now();
        log.setChangeTime(now);
        assertEquals(now, log.getChangeTime());
    }

    @Test
    void testSetAndGetActionType() {
        PasswordChangeLog log = new PasswordChangeLog();
        log.setActionType("PASSWORD_CHANGE");
        assertEquals("PASSWORD_CHANGE", log.getActionType());
    }
}

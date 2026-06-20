package com.example.propertymanagement.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    @Test
    void testDefaultConstructor() {
        Admin admin = new Admin();
        assertNotNull(admin);
    }

    @Test
    void testParameterizedConstructor() {
        Admin admin = new Admin("admin", "password123");
        assertEquals("admin", admin.getUsername());
        assertEquals("password123", admin.getPassword());
    }

    @Test
    void testSetAndGetId() {
        Admin admin = new Admin();
        admin.setId(1L);
        assertEquals(1L, admin.getId());
    }

    @Test
    void testSetAndGetUsername() {
        Admin admin = new Admin();
        admin.setUsername("superadmin");
        assertEquals("superadmin", admin.getUsername());
    }

    @Test
    void testSetAndGetPassword() {
        Admin admin = new Admin();
        admin.setPassword("securepass");
        assertEquals("securepass", admin.getPassword());
    }
}

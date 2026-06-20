package com.example.propertymanagement.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StaffTest {

    @Test
    void testDefaultConstructor() {
        Staff staff = new Staff();
        assertNotNull(staff);
    }

    @Test
    void testParameterizedConstructor() {
        Staff staff = new Staff("张三", "13800138000", "zhangsan", "password");
        assertEquals("张三", staff.getName());
        assertEquals("13800138000", staff.getPhone());
        assertEquals("zhangsan", staff.getUsername());
        assertEquals("password", staff.getPassword());
    }

    @Test
    void testSetAndGetId() {
        Staff staff = new Staff();
        staff.setId(1L);
        assertEquals(1L, staff.getId());
    }

    @Test
    void testSetAndGetName() {
        Staff staff = new Staff();
        staff.setName("李四");
        assertEquals("李四", staff.getName());
    }

    @Test
    void testSetAndGetPhone() {
        Staff staff = new Staff();
        staff.setPhone("13900139000");
        assertEquals("13900139000", staff.getPhone());
    }

    @Test
    void testSetAndGetUsername() {
        Staff staff = new Staff();
        staff.setUsername("li4");
        assertEquals("li4", staff.getUsername());
    }

    @Test
    void testSetAndGetPassword() {
        Staff staff = new Staff();
        staff.setPassword("securepwd");
        assertEquals("securepwd", staff.getPassword());
    }
}

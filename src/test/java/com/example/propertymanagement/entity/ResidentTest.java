package com.example.propertymanagement.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResidentTest {

    @Test
    void testResidentCreation() {
        Resident resident = new Resident("张三", "13800138000", "A栋101", "zhangsan", "password123");
        
        assertEquals("张三", resident.getName());
        assertEquals("13800138000", resident.getPhone());
        assertEquals("A栋101", resident.getApartmentNumber());
        assertEquals("zhangsan", resident.getUsername());
        assertEquals("password123", resident.getPassword());
    }

    @Test
    void testDefaultConstructor() {
        Resident resident = new Resident();
        assertNotNull(resident);
        assertNotNull(resident.getRooms());
        assertTrue(resident.getRooms().isEmpty());
    }

    @Test
    void testSetAndGetId() {
        Resident resident = new Resident();
        resident.setId(1L);
        assertEquals(1L, resident.getId());
    }

    @Test
    void testSetAndGetName() {
        Resident resident = new Resident();
        resident.setName("李四");
        assertEquals("李四", resident.getName());
    }

    @Test
    void testSetAndGetPhone() {
        Resident resident = new Resident();
        resident.setPhone("13900139000");
        assertEquals("13900139000", resident.getPhone());
    }

    @Test
    void testSetAndGetUsername() {
        Resident resident = new Resident();
        resident.setUsername("lisi");
        assertEquals("lisi", resident.getUsername());
    }

    @Test
    void testSetAndGetPassword() {
        Resident resident = new Resident();
        resident.setPassword("securepass");
        assertEquals("securepass", resident.getPassword());
    }

    @Test
    void testRoomsManagement() {
        Resident resident = new Resident();
        Room room = new Room();
        room.setRoomNumber("B栋202");
        
        resident.addRoom(room);
        
        assertEquals(1, resident.getRooms().size());
        assertTrue(resident.getRooms().contains(room));
        assertTrue(room.getResidents().contains(resident));
    }

    @Test
    void testRemoveRoom() {
        Resident resident = new Resident();
        Room room = new Room();
        room.setRoomNumber("C栋303");
        
        resident.addRoom(room);
        resident.removeRoom(room);
        
        assertTrue(resident.getRooms().isEmpty());
        assertFalse(room.getResidents().contains(resident));
    }
}

package com.example.propertymanagement.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @Test
    void testDefaultConstructor() {
        Room room = new Room();
        assertNotNull(room);
        assertEquals("VACANT", room.getStatus());
        assertNotNull(room.getResidents());
        assertTrue(room.getResidents().isEmpty());
    }

    @Test
    void testParameterizedConstructor() {
        Building building = new Building("A栋", "20层");
        Room room = new Room("101", "1单元", 80.5, building);
        
        assertEquals("101", room.getRoomNumber());
        assertEquals("1单元", room.getUnitNumber());
        assertEquals(80.5, room.getArea());
        assertEquals(building, room.getBuilding());
        assertEquals("VACANT", room.getStatus());
    }

    @Test
    void testSetAndGetId() {
        Room room = new Room();
        room.setId(1L);
        assertEquals(1L, room.getId());
    }

    @Test
    void testSetAndGetRoomNumber() {
        Room room = new Room();
        room.setRoomNumber("202");
        assertEquals("202", room.getRoomNumber());
    }

    @Test
    void testSetAndGetUnitNumber() {
        Room room = new Room();
        room.setUnitNumber("2单元");
        assertEquals("2单元", room.getUnitNumber());
    }

    @Test
    void testSetAndGetArea() {
        Room room = new Room();
        room.setArea(120.0);
        assertEquals(120.0, room.getArea());
    }

    @Test
    void testSetAndGetStatus() {
        Room room = new Room();
        room.setStatus("OCCUPIED");
        assertEquals("OCCUPIED", room.getStatus());
    }

    @Test
    void testSetAndGetBuilding() {
        Room room = new Room();
        Building building = new Building();
        building.setName("C栋");
        room.setBuilding(building);
        assertEquals(building, room.getBuilding());
    }

    @Test
    void testResidentsManagement() {
        Room room = new Room();
        Resident resident = new Resident();
        resident.setName("张三");
        
        room.getResidents().add(resident);
        
        assertEquals(1, room.getResidents().size());
        assertTrue(room.getResidents().contains(resident));
    }
}

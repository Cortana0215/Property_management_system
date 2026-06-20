package com.example.propertymanagement.entity;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {

    @Test
    void testDefaultConstructor() {
        Building building = new Building();
        assertNotNull(building);
        assertNotNull(building.getRooms());
    }

    @Test
    void testParameterizedConstructor() {
        Building building = new Building("A栋", "共20层");
        assertEquals("A栋", building.getName());
        assertEquals("共20层", building.getDescription());
    }

    @Test
    void testSetAndGetId() {
        Building building = new Building();
        building.setId(1L);
        assertEquals(1L, building.getId());
    }

    @Test
    void testSetAndGetName() {
        Building building = new Building();
        building.setName("B栋");
        assertEquals("B栋", building.getName());
    }

    @Test
    void testSetAndGetDescription() {
        Building building = new Building();
        building.setDescription("共15层");
        assertEquals("共15层", building.getDescription());
    }

    @Test
    void testSetAndGetRooms() {
        Building building = new Building();
        Room room = new Room();
        room.setRoomNumber("101");
        building.setRooms(new ArrayList<>());
        building.getRooms().add(room);
        
        assertEquals(1, building.getRooms().size());
        assertEquals("101", building.getRooms().get(0).getRoomNumber());
    }
}

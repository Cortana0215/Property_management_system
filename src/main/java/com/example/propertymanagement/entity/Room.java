package com.example.propertymanagement.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomNumber; // e.g., "101"
    private Double area; // Square meters
    private String unitNumber; // e.g., "1单元"
    private String status; // "VACANT", "OCCUPIED"

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @ManyToMany(mappedBy = "rooms")
    private List<Resident> residents = new ArrayList<>();

    public Room() {
        this.status = "VACANT";
    }

    public Room(String roomNumber, String unitNumber, Double area, Building building) {
        this.roomNumber = roomNumber;
        this.unitNumber = unitNumber;
        this.area = area;
        this.building = building;
        this.status = "VACANT";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public Double getArea() { return area; }
    public void setArea(Double area) { this.area = area; }

    public String getUnitNumber() { return unitNumber; }
    public void setUnitNumber(String unitNumber) { this.unitNumber = unitNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Building getBuilding() { return building; }
    public void setBuilding(Building building) { this.building = building; }

    public List<Resident> getResidents() { return residents; }
    public void setResidents(List<Resident> residents) { this.residents = residents; }
}

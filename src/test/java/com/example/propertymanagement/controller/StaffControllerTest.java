package com.example.propertymanagement.controller;

import com.example.propertymanagement.entity.Staff;
import com.example.propertymanagement.repository.StaffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class StaffControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StaffRepository staffRepository;

    private MockHttpSession staffSession;

    @BeforeEach
    void setUp() {
        staffSession = new MockHttpSession();
        
        Staff staff = new Staff();
        staff.setUsername("stafftester_" + System.currentTimeMillis());
        staff.setPassword("password");
        staff.setName("测试职工");
        staff = staffRepository.save(staff);
        
        staffSession.setAttribute("loggedInStaff", staff);
    }

    @Test
    void testStaffDashboardAccess() throws Exception {
        mockMvc.perform(get("/staff/dashboard").session(staffSession))
                .andExpect(status().isOk())
                .andExpect(view().name("staff/dashboard"));
    }

    @Test
    void testStaffNoticesAccess() throws Exception {
        mockMvc.perform(get("/staff/notices").session(staffSession))
                .andExpect(status().isOk())
                .andExpect(view().name("staff/notices"));
    }

    @Test
    void testStaffAttendanceAccess() throws Exception {
        mockMvc.perform(get("/staff/attendance").session(staffSession))
                .andExpect(status().isOk())
                .andExpect(view().name("staff/attendance"));
    }
}

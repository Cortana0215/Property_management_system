package com.example.propertymanagement.controller;

import com.example.propertymanagement.entity.Admin;
import com.example.propertymanagement.repository.AdminRepository;
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
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdminRepository adminRepository;

    private MockHttpSession adminSession;

    @BeforeEach
    void setUp() {
        adminSession = new MockHttpSession();
        
        Admin admin = new Admin();
        admin.setUsername("admintester_" + System.currentTimeMillis());
        admin.setPassword("password");
        admin = adminRepository.save(admin);
        
        adminSession.setAttribute("loggedInAdmin", admin);
    }

    @Test
    void testDashboardAccess() throws Exception {
        mockMvc.perform(get("/admin/dashboard").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/dashboard"));
    }

    @Test
    void testDashboardWithoutLogin() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testResidentsPageAccess() throws Exception {
        mockMvc.perform(get("/admin/residents").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/residents"));
    }

    @Test
    void testStaffPageAccess() throws Exception {
        mockMvc.perform(get("/admin/staff").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/staff"));
    }

    @Test
    void testBuildingsPageAccess() throws Exception {
        mockMvc.perform(get("/admin/buildings").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/buildings"));
    }

    @Test
    void testRoomsPageAccess() throws Exception {
        mockMvc.perform(get("/admin/rooms").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/rooms"));
    }

    @Test
    void testNoticesPageAccess() throws Exception {
        mockMvc.perform(get("/admin/notices").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/notices"));
    }

    @Test
    void testComplaintsPageAccess() throws Exception {
        mockMvc.perform(get("/admin/complaints").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/complaints"));
    }

    @Test
    void testAdminsPageAccess() throws Exception {
        mockMvc.perform(get("/admin/admins").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admins"));
    }

    @Test
    void testRepairsPageAccess() throws Exception {
        mockMvc.perform(get("/admin/repairs").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/repairs"));
    }

    @Test
    void testUserManagementPageAccess() throws Exception {
        mockMvc.perform(get("/admin/user-management").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/user-management"));
    }
}

package com.example.propertymanagement.controller;

import com.example.propertymanagement.entity.Resident;
import com.example.propertymanagement.repository.ResidentRepository;
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
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResidentRepository residentRepository;

    private MockHttpSession userSession;

    @BeforeEach
    void setUp() {
        userSession = new MockHttpSession();
        
        Resident resident = new Resident();
        resident.setUsername("testuser_" + System.currentTimeMillis());
        resident.setPassword("password");
        resident.setName("测试用户");
        resident = residentRepository.save(resident);
        
        userSession.setAttribute("loggedInUser", resident);
    }

    @Test
    void testUserIndexAccess() throws Exception {
        mockMvc.perform(get("/").session(userSession))
                .andExpect(status().isOk())
                .andExpect(view().name("user/index"));
    }

    @Test
    void testUserNoticesAccess() throws Exception {
        mockMvc.perform(get("/notices").session(userSession))
                .andExpect(status().isOk())
                .andExpect(view().name("user/notices"));
    }

    @Test
    void testUserServicesAccess() throws Exception {
        mockMvc.perform(get("/services").session(userSession))
                .andExpect(status().isOk())
                .andExpect(view().name("user/services"));
    }

    @Test
    void testUserProfileAccess() throws Exception {
        mockMvc.perform(get("/profile").session(userSession))
                .andExpect(status().isOk())
                .andExpect(view().name("user/profile"));
    }

    @Test
    void testUserComplaintListAccess() throws Exception {
        mockMvc.perform(get("/complaints").session(userSession))
                .andExpect(status().isOk())
                .andExpect(view().name("user/complaint-list"));
    }

    @Test
    void testUserComplaintFormAccess() throws Exception {
        mockMvc.perform(get("/complaint/new").session(userSession))
                .andExpect(status().isOk())
                .andExpect(view().name("user/complaint-form"));
    }
}

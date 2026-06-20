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
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdminRepository adminRepository;

    private MockHttpSession adminSession;

    @BeforeEach
    void setUp() {
        adminSession = new MockHttpSession();
        
        Admin admin = new Admin();
        admin.setUsername("testadmin_" + System.currentTimeMillis());
        admin.setPassword("password");
        admin = adminRepository.save(admin);
        
        adminSession.setAttribute("loggedInAdmin", admin);
    }

    @Test
    void testLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testAdminLoginSuccess() throws Exception {
        mockMvc.perform(post("/admin/login")
                .param("username", "testadmin")
                .param("password", "password")
                .session(adminSession))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testLogout() throws Exception {
        mockMvc.perform(post("/logout").session(adminSession))
                .andExpect(status().is3xxRedirection());
    }
}

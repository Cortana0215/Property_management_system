package com.example.propertymanagement.interceptor;

import com.example.propertymanagement.entity.Admin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.jupiter.api.Assertions.*;

class AdminInterceptorTest {

    private AdminInterceptor adminInterceptor;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private Object handler;

    @BeforeEach
    void setUp() {
        adminInterceptor = new AdminInterceptor();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        handler = new Object();
    }

    @Test
    void testPreHandleWithAdminLoggedIn() throws Exception {
        Admin admin = new Admin();
        admin.setId(1L);
        admin.setUsername("testadmin");
        session.setAttribute("loggedInAdmin", admin);
        request.setSession(session);

        boolean result = adminInterceptor.preHandle(request, response, handler);

        assertTrue(result);
    }

    @Test
    void testPreHandleWithoutSession() throws Exception {
        boolean result = adminInterceptor.preHandle(request, response, handler);

        assertFalse(result);
        assertEquals(302, response.getStatus());
        assertEquals("/login", response.getRedirectedUrl());
    }

    @Test
    void testPreHandleWithNoAdminInSession() throws Exception {
        session.setAttribute("loggedInUser", "someUser");
        request.setSession(session);

        boolean result = adminInterceptor.preHandle(request, response, handler);

        assertFalse(result);
        assertEquals(302, response.getStatus());
    }
}

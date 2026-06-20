package com.example.propertymanagement.interceptor;

import com.example.propertymanagement.entity.Staff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.jupiter.api.Assertions.*;

class StaffInterceptorTest {

    private StaffInterceptor staffInterceptor;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private Object handler;

    @BeforeEach
    void setUp() {
        staffInterceptor = new StaffInterceptor();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        handler = new Object();
    }

    @Test
    void testPreHandleWithStaffLoggedIn() throws Exception {
        Staff staff = new Staff();
        staff.setId(1L);
        staff.setName("测试职工");
        session.setAttribute("loggedInStaff", staff);
        request.setSession(session);

        boolean result = staffInterceptor.preHandle(request, response, handler);

        assertTrue(result);
    }

    @Test
    void testPreHandleWithoutSession() throws Exception {
        boolean result = staffInterceptor.preHandle(request, response, handler);

        assertFalse(result);
        assertEquals(302, response.getStatus());
        assertEquals("/login", response.getRedirectedUrl());
    }

    @Test
    void testPreHandleWithNoStaffInSession() throws Exception {
        session.setAttribute("loggedInUser", "someUser");
        request.setSession(session);

        boolean result = staffInterceptor.preHandle(request, response, handler);

        assertFalse(result);
        assertEquals(302, response.getStatus());
    }
}

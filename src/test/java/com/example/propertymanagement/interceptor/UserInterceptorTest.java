package com.example.propertymanagement.interceptor;

import com.example.propertymanagement.entity.Resident;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.jupiter.api.Assertions.*;

class UserInterceptorTest {

    private UserInterceptor userInterceptor;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private Object handler;

    @BeforeEach
    void setUp() {
        userInterceptor = new UserInterceptor();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        handler = new Object();
    }

    @Test
    void testPreHandleWithUserLoggedIn() throws Exception {
        Resident resident = new Resident();
        resident.setId(1L);
        resident.setName("测试住户");
        session.setAttribute("loggedInUser", resident);
        request.setSession(session);

        boolean result = userInterceptor.preHandle(request, response, handler);

        assertTrue(result);
    }

    @Test
    void testPreHandleWithoutSession() throws Exception {
        boolean result = userInterceptor.preHandle(request, response, handler);

        assertFalse(result);
        assertEquals(302, response.getStatus());
        assertEquals("/login", response.getRedirectedUrl());
    }

    @Test
    void testPreHandleWithNoUserInSession() throws Exception {
        session.setAttribute("loggedInAdmin", "someAdmin");
        request.setSession(session);

        boolean result = userInterceptor.preHandle(request, response, handler);

        assertFalse(result);
        assertEquals(302, response.getStatus());
    }

    @Test
    void testAllowedPathsExcluded() throws Exception {
        MockHttpServletRequest excludeRequest = new MockHttpServletRequest();
        excludeRequest.setRequestURI("/login");
        
        boolean result = userInterceptor.preHandle(excludeRequest, response, handler);

        assertTrue(result);
    }
}

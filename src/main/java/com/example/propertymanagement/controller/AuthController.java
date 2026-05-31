package com.example.propertymanagement.controller;

import com.example.propertymanagement.entity.Admin;
import com.example.propertymanagement.entity.Resident;
import com.example.propertymanagement.entity.Staff;
import com.example.propertymanagement.repository.AdminRepository;
import com.example.propertymanagement.repository.ResidentRepository;
import com.example.propertymanagement.repository.StaffRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private StaffRepository staffRepository;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // Redirect legacy URLs to the new unified login
    @GetMapping({"/admin/login", "/user/login"})
    public String legacyLoginRedirect() {
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username, 
                              @RequestParam String password, 
                              @RequestParam String role,
                              HttpSession session, Model model) {
        if ("ADMIN".equals(role)) {
            Admin admin = adminRepository.findByUsername(username);
            if (admin != null && admin.getPassword().equals(password)) {
                session.setAttribute("loggedInAdmin", admin);
                session.setAttribute("role", "ADMIN");
                return "redirect:/admin/dashboard";
            }
        } else if ("RESIDENT".equals(role)) {
            Resident resident = residentRepository.findByUsername(username);
            if (resident != null && resident.getPassword().equals(password)) {
                session.setAttribute("loggedInUser", resident);
                session.setAttribute("role", "RESIDENT");
                return "redirect:/";
            }
        } else if ("STAFF".equals(role)) {
            Staff staff = staffRepository.findByUsername(username);
            if (staff != null && staff.getPassword().equals(password)) {
                session.setAttribute("loggedInStaff", staff);
                session.setAttribute("role", "STAFF");
                return "redirect:/staff/dashboard";
            }
        }
        model.addAttribute("error", "用户名、密码或角色错误");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

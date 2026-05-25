package com.example.propertymanagement.controller;

import com.example.propertymanagement.entity.Admin;
import com.example.propertymanagement.entity.Resident;
import com.example.propertymanagement.repository.AdminRepository;
import com.example.propertymanagement.repository.ResidentRepository;
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

    // --- User (Resident) Login ---
    @GetMapping("/login")
    public String userLoginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String userLoginSubmit(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        Resident resident = residentRepository.findByUsername(username);
        if (resident != null && resident.getPassword().equals(password)) {
            session.setAttribute("loggedInUser", resident);
            return "redirect:/";
        }
        model.addAttribute("error", "用户名或密码错误");
        return "user/login";
    }

    @GetMapping("/logout")
    public String userLogout(HttpSession session) {
        session.removeAttribute("loggedInUser");
        return "redirect:/login";
    }

    // --- Admin Login ---
    @GetMapping("/admin/login")
    public String adminLoginForm() {
        return "admin/login";
    }

    @PostMapping("/admin/login")
    public String adminLoginSubmit(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            session.setAttribute("loggedInAdmin", admin);
            return "redirect:/admin/dashboard";
        }
        model.addAttribute("error", "管理员用户名或密码错误");
        return "admin/login";
    }

    @GetMapping("/admin/logout")
    public String adminLogout(HttpSession session) {
        session.removeAttribute("loggedInAdmin");
        return "redirect:/admin/login";
    }
}

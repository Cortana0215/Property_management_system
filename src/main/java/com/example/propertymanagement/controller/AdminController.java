package com.example.propertymanagement.controller;

import com.example.propertymanagement.entity.Admin;
import com.example.propertymanagement.entity.Resident;
import com.example.propertymanagement.entity.Staff;
import com.example.propertymanagement.entity.RepairRequest;
import com.example.propertymanagement.repository.AdminRepository;
import com.example.propertymanagement.repository.ResidentRepository;
import com.example.propertymanagement.repository.StaffRepository;
import com.example.propertymanagement.repository.RepairRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private RepairRequestRepository repairRequestRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StaffRepository staffRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("residentCount", residentRepository.count());
        model.addAttribute("staffCount", staffRepository.count());
        model.addAttribute("pendingRepairs", repairRequestRepository.findAll().stream().filter(r -> "PENDING".equals(r.getStatus())).count());
        return "admin/dashboard";
    }

    // --- Resident Management ---

    @GetMapping("/residents")
    public String listResidents(Model model) {
        model.addAttribute("residents", residentRepository.findAll());
        return "admin/residents";
    }

    @PostMapping("/residents/add")
    public String addResident(@ModelAttribute Resident resident) {
        residentRepository.save(resident);
        return "redirect:/admin/residents";
    }

    @PostMapping("/residents/delete/{id}")
    public String deleteResident(@PathVariable Long id) {
        residentRepository.deleteById(id);
        return "redirect:/admin/residents";
    }

    // --- Staff Management ---
    @GetMapping("/staff")
    public String listStaff(Model model) {
        model.addAttribute("staffList", staffRepository.findAll());
        return "admin/staff";
    }

    @PostMapping("/staff/add")
    public String addStaff(@ModelAttribute Staff staff) {
        staffRepository.save(staff);
        return "redirect:/admin/staff";
    }

    @PostMapping("/staff/delete/{id}")
    public String deleteStaff(@PathVariable Long id) {
        staffRepository.deleteById(id);
        return "redirect:/admin/staff";
    }

    // --- Admin Management ---
    @GetMapping("/admins")
    public String listAdmins(Model model) {
        model.addAttribute("admins", adminRepository.findAll());
        return "admin/admins";
    }

    @PostMapping("/admins/add")
    public String addAdmin(@ModelAttribute Admin admin) {
        adminRepository.save(admin);
        return "redirect:/admin/admins";
    }

    // --- Repair Management ---

    @GetMapping("/repairs")
    public String listRepairs(Model model) {
        model.addAttribute("repairs", repairRequestRepository.findAll());
        model.addAttribute("staffList", staffRepository.findAll());
        return "admin/repairs";
    }

    @PostMapping("/repairs/assign/{id}")
    public String assignRepair(@PathVariable Long id, @RequestParam Long staffId) {
        RepairRequest request = repairRequestRepository.findById(id).orElse(null);
        Staff staff = staffRepository.findById(staffId).orElse(null);
        if (request != null && staff != null) {
            request.setAssignedStaffId(staffId);
            request.setStaffName(staff.getName());
            request.setStatus("ASSIGNED");
            request.setAssignTime(LocalDateTime.now());
            repairRequestRepository.save(request);
        }
        return "redirect:/admin/repairs";
    }

    @PostMapping("/repairs/update/{id}")
    public String updateRepairStatus(@PathVariable Long id, @RequestParam String status) {
        RepairRequest request = repairRequestRepository.findById(id).orElse(null);
        if (request != null) {
            request.setStatus(status);
            repairRequestRepository.save(request);
        }
        return "redirect:/admin/repairs";
    }
}

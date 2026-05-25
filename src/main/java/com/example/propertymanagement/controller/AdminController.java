package com.example.propertymanagement.controller;

import com.example.propertymanagement.entity.Resident;
import com.example.propertymanagement.entity.RepairRequest;
import com.example.propertymanagement.repository.ResidentRepository;
import com.example.propertymanagement.repository.RepairRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private RepairRequestRepository repairRequestRepository;

    @GetMapping("/dashboard")
    public String dashboard() {
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

    // --- Repair Management ---

    @GetMapping("/repairs")
    public String listRepairs(Model model) {
        model.addAttribute("repairs", repairRequestRepository.findAll());
        return "admin/repairs";
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

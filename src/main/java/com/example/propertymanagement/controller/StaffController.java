package com.example.propertymanagement.controller;

import com.example.propertymanagement.entity.RepairRequest;
import com.example.propertymanagement.entity.Staff;
import com.example.propertymanagement.repository.RepairRequestRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private RepairRequestRepository repairRequestRepository;

    private final String UPLOAD_DIR = "uploads/";

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Staff staff = (Staff) session.getAttribute("loggedInStaff");
        if (staff == null) return "redirect:/login";

        model.addAttribute("myTasks", repairRequestRepository.findAll().stream()
                .filter(r -> staff.getId().equals(r.getAssignedStaffId()) && !"COMPLETED".equals(r.getStatus()))
                .collect(Collectors.toList()));
        
        model.addAttribute("completedTasks", repairRequestRepository.findAll().stream()
                .filter(r -> staff.getId().equals(r.getAssignedStaffId()) && "COMPLETED".equals(r.getStatus()))
                .collect(Collectors.toList()));

        return "staff/dashboard";
    }

    @PostMapping("/tasks/complete/{id}")
    public String completeTask(@PathVariable Long id, 
                               @RequestParam String feedback, 
                               @RequestParam(value = "image", required = false) MultipartFile image) {
        RepairRequest request = repairRequestRepository.findById(id).orElse(null);
        if (request != null) {
            request.setStaffFeedbackText(feedback);
            request.setStatus("COMPLETED");
            request.setCompleteTime(LocalDateTime.now());

            if (image != null && !image.isEmpty()) {
                try {
                    String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                    Path path = Paths.get(UPLOAD_DIR + fileName);
                    Files.createDirectories(path.getParent());
                    Files.write(path, image.getBytes());
                    request.setStaffFeedbackImage("/uploads/" + fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            repairRequestRepository.save(request);
        }
        return "redirect:/staff/dashboard";
    }
}

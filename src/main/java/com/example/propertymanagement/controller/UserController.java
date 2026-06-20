package com.example.propertymanagement.controller;

import com.example.propertymanagement.entity.Complaint;
import com.example.propertymanagement.entity.RepairRequest;
import com.example.propertymanagement.entity.Resident;
import com.example.propertymanagement.repository.ComplaintRepository;
import com.example.propertymanagement.repository.NoticeRepository;
import com.example.propertymanagement.repository.RepairRequestRepository;
import com.example.propertymanagement.repository.ResidentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private com.example.propertymanagement.repository.RoomRepository roomRepository;

    @Autowired
    private RepairRequestRepository repairRequestRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String UPLOAD_DIR = "uploads/";

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        Resident sessionResident = (Resident) session.getAttribute("loggedInUser");
        if (sessionResident != null) {
            Resident resident = residentRepository.findById(sessionResident.getId()).orElse(sessionResident);
            model.addAttribute("myRequests", repairRequestRepository.findAll().stream()
                    .filter(r -> resident.getName().equals(r.getSubmitterName()))
                    .collect(Collectors.toList()));
            model.addAttribute("myRooms", resident.getRooms());
            // Fetch notices directly for the dashboard
            model.addAttribute("notices", noticeRepository.findByTargetRoleInOrderByCreateTimeDesc(java.util.Arrays.asList("ALL", "RESIDENT")));
        }
        return "user/index";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Resident resident = (Resident) session.getAttribute("loggedInUser");
        if (resident == null) return "redirect:/login";
        
        // Re-fetch to ensure we have the latest info
        resident = residentRepository.findById(resident.getId()).orElse(resident);
        model.addAttribute("resident", resident);
        return "user/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute Resident updatedResident, HttpSession session) {
        Resident current = (Resident) session.getAttribute("loggedInUser");
        if (current != null) {
            Resident resident = residentRepository.findById(current.getId()).orElse(null);
            if (resident != null) {
                resident.setName(updatedResident.getName());
                resident.setPhone(updatedResident.getPhone());
                if (updatedResident.getPassword() != null && !updatedResident.getPassword().isEmpty()) {
                    resident.setPassword(passwordEncoder.encode(updatedResident.getPassword()));
                }
                residentRepository.save(resident);
                session.setAttribute("loggedInUser", resident);
            }
        }
        return "redirect:/profile";
    }

    @GetMapping("/report")
    public String reportForm(Model model) {
        model.addAttribute("repairRequest", new RepairRequest());
        return "user/report";
    }

    @PostMapping("/report")
    public String submitReport(@ModelAttribute RepairRequest repairRequest, 
                               @RequestParam(value = "image", required = false) MultipartFile image,
                               Model model) {
        if (image != null && !image.isEmpty()) {
            try {
                String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, image.getBytes());
                repairRequest.setImagePath("/uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        repairRequestRepository.save(repairRequest);
        model.addAttribute("message", "报修提交成功！物业人员将尽快处理。");
        return "user/report-success";
    }

    @PostMapping("/repair/rate/{id}")
    public String rateRepair(@PathVariable Long id, @RequestParam Integer rating, @RequestParam String feedback) {
        RepairRequest request = repairRequestRepository.findById(id).orElse(null);
        if (request != null && "COMPLETED".equals(request.getStatus())) {
            request.setRating(rating);
            request.setResidentFeedback(feedback);
            repairRequestRepository.save(request);
        }
        return "redirect:/";
    }

    // --- Complaint ---
    @GetMapping("/complaints")
    public String complaintList(HttpSession session, Model model) {
        Resident resident = (Resident) session.getAttribute("loggedInUser");
        model.addAttribute("complaints", complaintRepository.findBySubmitterNameOrderBySubmitTimeDesc(resident.getName()));
        return "user/complaint-list";
    }

    @GetMapping("/complaint/new")
    public String complaintForm() {
        return "user/complaint-form";
    }

    @PostMapping("/complaint/submit")
    public String submitComplaint(@ModelAttribute Complaint complaint, HttpSession session) {
        Resident resident = (Resident) session.getAttribute("loggedInUser");
        complaint.setSubmitterName(resident.getName());
        complaint.setContactPhone(resident.getPhone());
        complaintRepository.save(complaint);
        return "redirect:/complaints";
    }

    // --- Notices ---
    @GetMapping("/notices")
    public String listNotices(Model model) {
        model.addAttribute("notices", noticeRepository.findByTargetRoleInOrderByCreateTimeDesc(java.util.Arrays.asList("ALL", "RESIDENT")));
        return "user/notices";
    }

    // --- Services ---
    @GetMapping("/services")
    public String services() {
        return "user/services";
    }
}

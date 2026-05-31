package com.example.propertymanagement.controller;

import com.example.propertymanagement.entity.RepairRequest;
import com.example.propertymanagement.repository.RepairRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private RepairRequestRepository repairRequestRepository;

    private final String UPLOAD_DIR = "uploads/";

    @GetMapping("/")
    public String index(jakarta.servlet.http.HttpSession session, Model model) {
        com.example.propertymanagement.entity.Resident resident = (com.example.propertymanagement.entity.Resident) session.getAttribute("loggedInUser");
        if (resident != null) {
            model.addAttribute("myRequests", repairRequestRepository.findAll().stream()
                    .filter(r -> resident.getName().equals(r.getSubmitterName()))
                    .collect(java.util.stream.Collectors.toList()));
        }
        return "user/index";
    }

    @GetMapping("/report")
    public String reportForm(Model model) {
        model.addAttribute("repairRequest", new RepairRequest());
        return "user/report";
    }

    @PostMapping("/report")
    public String submitReport(@ModelAttribute RepairRequest repairRequest, 
                               @RequestParam("image") org.springframework.web.multipart.MultipartFile image,
                               Model model) {
        if (!image.isEmpty()) {
            try {
                String fileName = java.util.UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                java.nio.file.Path path = java.nio.file.Paths.get(UPLOAD_DIR + fileName);
                java.nio.file.Files.createDirectories(path.getParent());
                java.nio.file.Files.write(path, image.getBytes());
                repairRequest.setImagePath("/uploads/" + fileName);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
        repairRequestRepository.save(repairRequest);
        model.addAttribute("message", "报修提交成功！物业人员将尽快处理。");
        return "user/report-success";
    }
}

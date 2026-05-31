package com.example.propertymanagement.controller;

import com.example.propertymanagement.entity.RepairRequest;
import com.example.propertymanagement.entity.Resident;
import com.example.propertymanagement.repository.RepairRequestRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private RepairRequestRepository repairRequestRepository;

    private final String UPLOAD_DIR = "uploads/";

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        Resident resident = (Resident) session.getAttribute("loggedInUser");
        if (resident != null) {
            model.addAttribute("myRequests", repairRequestRepository.findAll().stream()
                    .filter(r -> resident.getName().equals(r.getSubmitterName()))
                    .collect(Collectors.toList()));
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
                               @RequestParam("image") MultipartFile image,
                               Model model) {
        if (!image.isEmpty()) {
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
}

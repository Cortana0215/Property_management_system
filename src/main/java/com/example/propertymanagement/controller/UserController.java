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

    @GetMapping("/")
    public String index() {
        return "user/index";
    }

    @GetMapping("/report")
    public String reportForm(Model model) {
        model.addAttribute("repairRequest", new RepairRequest());
        return "user/report";
    }

    @PostMapping("/report")
    public String submitReport(@ModelAttribute RepairRequest repairRequest, Model model) {
        repairRequestRepository.save(repairRequest);
        model.addAttribute("message", "报修提交成功！物业人员将尽快处理。");
        return "user/report-success";
    }
}

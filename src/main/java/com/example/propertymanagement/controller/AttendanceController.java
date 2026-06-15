package com.example.propertymanagement.controller;

import com.example.propertymanagement.entity.Admin;
import com.example.propertymanagement.entity.AttendanceRecord;
import com.example.propertymanagement.entity.Staff;
import com.example.propertymanagement.repository.AttendanceRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @PostMapping("/clock-in")
    public String clockIn(HttpSession session, RedirectAttributes ra) {
        String role = (String) session.getAttribute("role");
        Long userId = null;
        String userName = null;

        if ("ADMIN".equals(role)) {
            Admin admin = (Admin) session.getAttribute("loggedInAdmin");
            userId = admin.getId();
            userName = admin.getUsername();
        } else if ("STAFF".equals(role)) {
            Staff staff = (Staff) session.getAttribute("loggedInStaff");
            userId = staff.getId();
            userName = staff.getName();
        }

        if (userId != null) {
            Optional<AttendanceRecord> activeRecord = attendanceRepository.findTopByUserIdAndUserRoleAndClockOutTimeIsNullOrderByClockInTimeDesc(userId, role);
            if (activeRecord.isEmpty()) {
                LocalDateTime now = LocalDateTime.now();
                String status = now.getHour() < 9 ? "正常" : "迟到";
                AttendanceRecord record = new AttendanceRecord(userId, userName, role, now, status);
                attendanceRepository.save(record);
                ra.addFlashAttribute("message", "打卡上班成功！" + (status.equals("迟到") ? "（迟到）" : ""));
            } else {
                ra.addFlashAttribute("error", "您已经处于打卡状态！");
            }
        }

        return "redirect:" + getDashboardUrl(role);
    }

    @PostMapping("/clock-out")
    public String clockOut(HttpSession session, RedirectAttributes ra) {
        String role = (String) session.getAttribute("role");
        Long userId = null;

        if ("ADMIN".equals(role)) {
            Admin admin = (Admin) session.getAttribute("loggedInAdmin");
            userId = admin.getId();
        } else if ("STAFF".equals(role)) {
            Staff staff = (Staff) session.getAttribute("loggedInStaff");
            userId = staff.getId();
        }

        if (userId != null) {
            Optional<AttendanceRecord> activeRecord = attendanceRepository.findTopByUserIdAndUserRoleAndClockOutTimeIsNullOrderByClockInTimeDesc(userId, role);
            if (activeRecord.isPresent()) {
                LocalDateTime now = LocalDateTime.now();
                String status = now.getHour() >= 18 ? "正常" : "早退";
                AttendanceRecord record = activeRecord.get();
                record.setClockOutTime(now);
                record.setClockOutStatus(status);
                attendanceRepository.save(record);
                ra.addFlashAttribute("message", "打卡下班成功！" + (status.equals("早退") ? "（早退）" : ""));
            } else {
                ra.addFlashAttribute("error", "未找到对应的上班打卡记录！");
            }
        }

        return "redirect:" + getDashboardUrl(role);
    }

    @GetMapping("/records")
    public String listRecords(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        if ("ADMIN".equals(role)) {
            model.addAttribute("records", attendanceRepository.findAllByOrderByClockInTimeDesc());
            return "admin/attendance";
        } else if ("STAFF".equals(role)) {
            Staff staff = (Staff) session.getAttribute("loggedInStaff");
            model.addAttribute("records", attendanceRepository.findByUserIdAndUserRoleOrderByClockInTimeDesc(staff.getId(), "STAFF"));
            return "staff/attendance";
        }
        return "redirect:/login";
    }

    private String getDashboardUrl(String role) {
        if ("ADMIN".equals(role)) return "/admin/dashboard";
        if ("STAFF".equals(role)) return "/staff/dashboard";
        return "/login";
    }
}

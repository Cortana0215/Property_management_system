package com.example.propertymanagement.controller;

import com.example.propertymanagement.entity.Admin;
import com.example.propertymanagement.entity.AttendanceRecord;
import com.example.propertymanagement.entity.AttendanceDeleteLog;
import com.example.propertymanagement.entity.Staff;
import com.example.propertymanagement.repository.AttendanceRepository;
import com.example.propertymanagement.repository.AttendanceDeleteLogRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AttendanceDeleteLogRepository deleteLogRepository;

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
            // 查询时排除已删除记录
            List<AttendanceRecord> activeRecords = attendanceRepository.findActiveByUserIdAndUserRole(userId, role);
            Optional<AttendanceRecord> activeRecord = activeRecords.stream()
                    .filter(r -> r.getClockOutTime() == null)
                    .findFirst();
            
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
            // 查询时排除已删除记录
            List<AttendanceRecord> activeRecords = attendanceRepository.findActiveByUserIdAndUserRole(userId, role);
            Optional<AttendanceRecord> activeRecord = activeRecords.stream()
                    .filter(r -> r.getClockOutTime() == null)
                    .findFirst();
            
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
            model.addAttribute("records", attendanceRepository.findAllActiveRecords());
            return "admin/attendance";
        } else if ("STAFF".equals(role)) {
            Staff staff = (Staff) session.getAttribute("loggedInStaff");
            model.addAttribute("records", attendanceRepository.findActiveByUserIdAndUserRole(staff.getId(), "STAFF"));
            return "staff/attendance";
        }
        return "redirect:/login";
    }

    /**
     * 删除单条考勤记录（逻辑删除）
     * 权限要求：仅管理员可访问
     */
    @PostMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Map<String, Object>> deleteRecord(
            @PathVariable Long id,
            HttpSession session) {
        
        Map<String, Object> response = new HashMap<>();
        
        // 权限验证
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            response.put("success", false);
            response.put("message", "无权限执行此操作，仅管理员可删除考勤记录");
            return ResponseEntity.status(403).body(response);
        }
        
        Admin admin = (Admin) session.getAttribute("loggedInAdmin");
        if (admin == null) {
            response.put("success", false);
            response.put("message", "请先登录");
            return ResponseEntity.status(401).body(response);
        }
        
        try {
            Optional<AttendanceRecord> recordOpt = attendanceRepository.findById(id);
            if (recordOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "考勤记录不存在");
                return ResponseEntity.badRequest().body(response);
            }
            
            AttendanceRecord record = recordOpt.get();
            
            // 检查是否已被删除
            if (Boolean.TRUE.equals(record.getDeleted())) {
                response.put("success", false);
                response.put("message", "该记录已被删除");
                return ResponseEntity.badRequest().body(response);
            }
            
            // 执行逻辑删除
            record.setDeleted(true);
            record.setDeletedTime(LocalDateTime.now());
            record.setDeletedByAdminId(admin.getId());
            record.setDeletedByAdminName(admin.getUsername());
            attendanceRepository.save(record);
            
            // 记录操作日志
            AttendanceDeleteLog log = new AttendanceDeleteLog(admin.getId(), admin.getUsername(), record, "SINGLE");
            deleteLogRepository.save(log);
            
            response.put("success", true);
            response.put("message", "删除成功");
            response.put("recordId", id);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "删除失败：" + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 批量删除考勤记录（逻辑删除）
     * 权限要求：仅管理员可访问
     */
    @PostMapping("/delete-batch")
    @Transactional
    public ResponseEntity<Map<String, Object>> deleteBatch(
            @RequestParam("ids") List<Long> ids,
            HttpSession session) {
        
        Map<String, Object> response = new HashMap<>();
        
        // 权限验证
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            response.put("success", false);
            response.put("message", "无权限执行此操作，仅管理员可删除考勤记录");
            return ResponseEntity.status(403).body(response);
        }
        
        Admin admin = (Admin) session.getAttribute("loggedInAdmin");
        if (admin == null) {
            response.put("success", false);
            response.put("message", "请先登录");
            return ResponseEntity.status(401).body(response);
        }
        
        // 参数验证
        if (ids == null || ids.isEmpty()) {
            response.put("success", false);
            response.put("message", "请选择要删除的记录");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            // 批量逻辑删除
            int deletedCount = attendanceRepository.batchSoftDelete(ids, admin.getId(), admin.getUsername());
            
            // 记录操作日志
            AttendanceDeleteLog log = new AttendanceDeleteLog(admin.getId(), admin.getUsername(), "BATCH", deletedCount);
            deleteLogRepository.save(log);
            
            response.put("success", true);
            response.put("message", "成功删除 " + deletedCount + " 条记录");
            response.put("deletedCount", deletedCount);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "批量删除失败：" + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    private String getDashboardUrl(String role) {
        if ("ADMIN".equals(role)) return "/admin/dashboard";
        if ("STAFF".equals(role)) return "/staff/dashboard";
        return "/login";
    }
}

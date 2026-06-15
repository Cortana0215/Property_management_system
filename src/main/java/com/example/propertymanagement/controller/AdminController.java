package com.example.propertymanagement.controller;

import com.example.propertymanagement.entity.*;
import com.example.propertymanagement.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Optional;

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

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("loggedInAdmin");
        if (admin != null) {
            Optional<AttendanceRecord> activeRecord = attendanceRepository.findTopByUserIdAndUserRoleAndClockOutTimeIsNullOrderByClockInTimeDesc(admin.getId(), "ADMIN");
            model.addAttribute("activeAttendance", activeRecord.orElse(null));
        }
        
        model.addAttribute("residentCount", residentRepository.count());
        model.addAttribute("staffCount", staffRepository.count());
        model.addAttribute("pendingRepairs", repairRequestRepository.findAll().stream().filter(r -> "PENDING".equals(r.getStatus())).count());
        return "admin/dashboard";
    }

    // --- Resident Management ---

    @GetMapping("/residents")
    public String listResidents(Model model) {
        model.addAttribute("residents", residentRepository.findAll());
        model.addAttribute("vacantRooms", roomRepository.findAll().stream()
                .filter(r -> "VACANT".equals(r.getStatus()))
                .collect(java.util.stream.Collectors.toList()));
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

    @PostMapping("/residents/bind")
    @org.springframework.transaction.annotation.Transactional
    public String bindResidentRoom(@RequestParam Long residentId, @RequestParam Long roomId) {
        Resident resident = residentRepository.findById(residentId).orElse(null);
        Room room = roomRepository.findById(roomId).orElse(null);
        if (resident != null && room != null) {
            resident.addRoom(room);
            room.setStatus("OCCUPIED");
            residentRepository.save(resident);
            roomRepository.save(room);
        }
        return "redirect:/admin/residents";
    }

    @PostMapping("/residents/unbind")
    @org.springframework.transaction.annotation.Transactional
    public String unbindResidentRoom(@RequestParam Long residentId, @RequestParam Long roomId) {
        Resident resident = residentRepository.findById(residentId).orElse(null);
        Room room = roomRepository.findById(roomId).orElse(null);
        if (resident != null && room != null) {
            resident.removeRoom(room);
            room.setStatus("VACANT");
            residentRepository.save(resident);
            roomRepository.save(room);
        }
        return "redirect:/admin/residents";
    }

    // --- Building Management ---
    @GetMapping("/buildings")
    public String listBuildings(Model model) {
        model.addAttribute("buildings", buildingRepository.findAll());
        return "admin/buildings";
    }

    @PostMapping("/buildings/add")
    public String addBuilding(@ModelAttribute Building building) {
        buildingRepository.save(building);
        return "redirect:/admin/buildings";
    }

    @PostMapping("/buildings/delete/{id}")
    public String deleteBuilding(@PathVariable Long id) {
        buildingRepository.deleteById(id);
        return "redirect:/admin/buildings";
    }

    // --- Room Management ---
    @GetMapping("/rooms")
    public String listRooms(@RequestParam(required = false) Long buildingId, Model model) {
        if (buildingId != null) {
            model.addAttribute("rooms", roomRepository.findByBuildingId(buildingId));
            model.addAttribute("selectedBuilding", buildingRepository.findById(buildingId).orElse(null));
        } else {
            model.addAttribute("rooms", roomRepository.findAll());
        }
        model.addAttribute("buildings", buildingRepository.findAll());
        return "admin/rooms";
    }

    @PostMapping("/rooms/add")
    public String addRoom(@ModelAttribute Room room, @RequestParam Long buildingId) {
        Building building = buildingRepository.findById(buildingId).orElse(null);
        if (building != null) {
            room.setBuilding(building);
            roomRepository.save(room);
        }
        return "redirect:/admin/rooms?buildingId=" + buildingId;
    }

    @PostMapping("/rooms/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        Room room = roomRepository.findById(id).orElse(null);
        Long bId = (room != null && room.getBuilding() != null) ? room.getBuilding().getId() : null;
        roomRepository.deleteById(id);
        return bId != null ? "redirect:/admin/rooms?buildingId=" + bId : "redirect:/admin/rooms";
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
    public String assignRepair(@PathVariable Long id, @RequestParam Long staffId, RedirectAttributes ra) {
        RepairRequest request = repairRequestRepository.findById(id).orElse(null);
        Staff staff = staffRepository.findById(staffId).orElse(null);
        if (request != null && staff != null) {
            request.setAssignedStaffId(staffId);
            request.setStaffName(staff.getName());
            request.setStatus("ASSIGNED");
            request.setAssignTime(LocalDateTime.now());
            repairRequestRepository.save(request);
            ra.addFlashAttribute("message", "指派成功");
        }
        return "redirect:/admin/repairs";
    }

    @PostMapping("/repairs/delete/{id}")
    public String deleteRepair(@PathVariable Long id, RedirectAttributes ra) {
        repairRequestRepository.deleteById(id);
        ra.addFlashAttribute("message", "记录已删除");
        return "redirect:/admin/repairs";
    }

    @PostMapping("/repairs/update/{id}")
    public String updateRepairStatus(@PathVariable Long id, @RequestParam String status, RedirectAttributes ra) {
        RepairRequest request = repairRequestRepository.findById(id).orElse(null);
        if (request != null) {
            request.setStatus(status);
            repairRequestRepository.save(request);
            ra.addFlashAttribute("message", "状态已更新");
        }
        return "redirect:/admin/repairs";
    }

    // --- Notice Management ---
    @GetMapping("/notices")
    public String listNotices(Model model) {
        model.addAttribute("notices", noticeRepository.findAll());
        return "admin/notices";
    }

    @PostMapping("/notices/add")
    public String addNotice(@ModelAttribute Notice notice) {
        noticeRepository.save(notice);
        return "redirect:/admin/notices";
    }

    @PostMapping("/notices/delete/{id}")
    public String deleteNotice(@PathVariable Long id) {
        noticeRepository.deleteById(id);
        return "redirect:/admin/notices";
    }

    // --- Complaint Management ---
    @GetMapping("/complaints")
    public String listComplaints(Model model) {
        model.addAttribute("complaints", complaintRepository.findAll());
        return "admin/complaints";
    }

    @PostMapping("/complaints/handle/{id}")
    public String handleComplaint(@PathVariable Long id, @RequestParam String feedback, @RequestParam String status, RedirectAttributes ra) {
        Complaint complaint = complaintRepository.findById(id).orElse(null);
        if (complaint != null) {
            complaint.setResultFeedback(feedback);
            complaint.setStatus(status);
            complaintRepository.save(complaint);
            ra.addFlashAttribute("message", "回复成功");
        }
        return "redirect:/admin/complaints";
    }

    @PostMapping("/complaints/delete/{id}")
    public String deleteComplaint(@PathVariable Long id, RedirectAttributes ra) {
        complaintRepository.deleteById(id);
        ra.addFlashAttribute("message", "记录已删除");
        return "redirect:/admin/complaints";
    }
}

package com.AS.Student_Attendance.controller;

import com.AS.Student_Attendance.entity.Teachers;
import com.AS.Student_Attendance.enumDto.ApprovalStatus;
import com.AS.Student_Attendance.repository.TeachersRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class AdminController {
    private final TeachersRepository teachersRepository;

    public AdminController(TeachersRepository teachersRepository) {
        this.teachersRepository = teachersRepository;
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        var pendingTeachers = teachersRepository.findByStatus(ApprovalStatus.PENDING);
        var approvedTeachers = teachersRepository.findByStatus(ApprovalStatus.APPROVED);
        var rejectedTeachers = teachersRepository.findByStatus(ApprovalStatus.REJECTED);
        model.addAttribute("pendingTeachers", pendingTeachers);
        model.addAttribute("approvedTeachers", approvedTeachers);
        model.addAttribute("rejectedTeachers", rejectedTeachers);
        model.addAttribute("teachers", approvedTeachers);
        return "admin_dashboard";
    }

    @PostMapping("/admin/approve")
    public String approveTeacher(@RequestParam Integer teacherId) {
        Teachers teacher = teachersRepository.findById(teacherId).orElse(null);
        if (teacher != null) {
            teacher.setStatus(ApprovalStatus.APPROVED);
            teachersRepository.save(teacher);
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/admin/reject")
    public String rejectTeacher(@RequestParam Integer teacherId) {
        Teachers teacher = teachersRepository.findById(teacherId).orElse(null);
        if (teacher != null) {
            teacher.setStatus(ApprovalStatus.REJECTED);
            teachersRepository.save(teacher);
        }
        return "redirect:/admin/dashboard";
    }
}


package com.AS.Student_Attendance.controller;

import com.AS.Student_Attendance.entity.Attendance;
import com.AS.Student_Attendance.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
public class AttendancePageController {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @GetMapping("/attendance/student")
    public String attendancePage() {
        return "attendance"; // Thymeleaf template name
    }

    @GetMapping("/student/attendance")
    public String studentAttendancePage(HttpSession session, Model model) {
        // Ensure the user is logged in as STUDENT
        String role = (String) session.getAttribute("role");
        Integer userId = (Integer) session.getAttribute("userId");
        if (role == null || userId == null || !"STUDENT".equals(role)) {
            return "redirect:/login";
        }

        List<Attendance> attendanceRecords = attendanceRepository.findByUser_UserId(userId);
        model.addAttribute("attendanceRecords", attendanceRecords);
        return "student_attendance"; // Thymeleaf template for student attendance
    }
    // Add other MVC methods as needed
}
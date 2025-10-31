package com.AS.Student_Attendance.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
class AttendancePageController {
    @GetMapping("/attendance/student")
    public String attendancePage() {
        return "attendance"; // Thymeleaf template name
    }

    @GetMapping("/student/attendance")
    public String studentAttendancePage() {
        return "student_attendance"; // New Thymeleaf template for student attendance
    }
    // Add other MVC methods as needed
}
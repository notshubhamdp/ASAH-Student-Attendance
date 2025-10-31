package com.AS.Student_Attendance.controller;

import com.AS.Student_Attendance.entity.Attendance;
import com.AS.Student_Attendance.repository.AttendanceRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.stereotype.Controller;
import com.AS.Student_Attendance.entity.Courses;
import com.AS.Student_Attendance.repository.CoursesRepository;
import com.AS.Student_Attendance.entity.User;
import com.AS.Student_Attendance.repository.UserRepository;
import com.AS.Student_Attendance.enumDto.AttendanceStatus;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;

@Controller
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CoursesRepository coursesRepository;

    @PostMapping("/attendance/mark")
    public String markAttendance(@RequestParam Integer userId,
                                @RequestParam Integer courseId,
                                @RequestParam String status,
                                HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        Integer teacherId = (Integer) session.getAttribute("userId");
        if (role == null || teacherId == null) {
            return "redirect:/login";
        }
        if (!role.equals("TEACHER")) {
            return "redirect:/student/profile";
        }
        System.out.println("Looking for courseId: '" + courseId + "'");
        System.out.println("[DEBUG] Received userId: " + userId + ", courseId: '" + courseId + "', status: '" + status + "'");
        User student = userRepository.findById(userId).orElse(null);
        Courses course = coursesRepository.findById(courseId).orElse(null);
        if (student == null || course == null) {
            String errorMsg = "Invalid student or course.";
            return "redirect:/teacher/dashboard?attendanceMsg=" + java.net.URLEncoder.encode(errorMsg, java.nio.charset.StandardCharsets.UTF_8) + "&attendanceStatus=ERROR";
        }
        Attendance attendance = new Attendance();
        attendance.setUser(student);
        attendance.setCourse(course);
        attendance.setAttendanceDate(LocalDate.now());
        try {
            attendance.setStatus(AttendanceStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            String errorMsg = "Invalid attendance status.";
            return "redirect:/teacher/dashboard?attendanceMsg=" + java.net.URLEncoder.encode(errorMsg, java.nio.charset.StandardCharsets.UTF_8) + "&attendanceStatus=ERROR";
        }
        attendance.setCreatedAt(new java.sql.Time(System.currentTimeMillis()));
        attendanceRepository.save(attendance);
        String studentName = student.getFirstName() + " " + student.getLastName();
        String attendanceMsg = "";
        String attendanceStatus = status.toUpperCase();
        switch (attendanceStatus) {
            case "PRESENT":
                attendanceMsg = "Present marked for student " + studentName;
                break;
            case "ABSENT":
                attendanceMsg = "Absent marked for student " + studentName;
                break;
            case "LATE":
                attendanceMsg = "Late marked for student " + studentName;
                break;
            default:
                attendanceMsg = "Attendance marked for student " + studentName;
        }
        return "redirect:/teacher/dashboard?attendanceMsg=" + java.net.URLEncoder.encode(attendanceMsg, java.nio.charset.StandardCharsets.UTF_8) + "&attendanceStatus=" + attendanceStatus;
    }

    @GetMapping("/attendance")
    public String viewStudentAttendance(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        Integer userId = (Integer) session.getAttribute("userId");
        if (role == null || userId == null) {
            return "redirect:/login";
        }
        if (!role.equals("STUDENT")) {
            return "redirect:/teacher/dashboard";
        }
        // Only return attendance for this student
        java.util.List<Attendance> records = attendanceRepository.findByUser_UserId(userId);
        model.addAttribute("records", records);
        return "attendance";
    }

    @GetMapping("/attendance/all")
    public String viewAllAttendance(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.equals("TEACHER")) {
            return "redirect:/login";
        }
        java.util.List<Attendance> records = attendanceRepository.findAll();
        model.addAttribute("records", records);
        return "attendance";
    }
}
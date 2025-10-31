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
        // LoginController stores teacher id under "teacherId" and students under "userId".
        Object teacherIdObj = session.getAttribute("teacherId");

        // Validate authentication and authorization
        if (role == null) {
            return "redirect:/login";
        }
        if (!role.equals("TEACHER")) {
            return "redirect:/student/profile";
        }
        if (teacherIdObj == null) {
            // teacher session not present - force login
            return "redirect:/login";
        }

        // Validate and load student
        User student = userRepository.findById(userId).orElse(null);
        if (student == null) {
            return "redirect:/teacher/dashboard?error=Student not found";
        }

        // Validate and load course
        Courses course = coursesRepository.findById(courseId).orElse(null);
        if (course == null) {
            return "redirect:/teacher/dashboard?error=Course not found";
        }

        // Check for duplicate attendance
        if (attendanceRepository.existsByUserUserIdAndCourseCourseIdAndAttendanceDate(
            userId.longValue(), courseId.longValue(), LocalDate.now())) {
            return "redirect:/teacher/dashboard?error=Attendance already marked for today";
        }

        // Create attendance record
        Attendance attendance = new Attendance();
        attendance.setUser(student);
        attendance.setCourse(course);
        attendance.setAttendanceDate(LocalDate.now());
        attendance.setCreatedAt(new java.sql.Time(System.currentTimeMillis()));
        
        // Parse and set status
        AttendanceStatus attendanceStatus;
        try {
            attendanceStatus = AttendanceStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return "redirect:/teacher/dashboard?error=Invalid attendance status";
        }
        attendance.setStatus(attendanceStatus);
        attendanceRepository.save(attendance);

        // Build friendly message and redirect
        String studentName = student.getFirstName() + " " + student.getLastName();
        String attendanceMsg;
        switch (attendanceStatus) {
            case PRESENT:
                attendanceMsg = "Present marked for student " + studentName;
                break;
            case ABSENT:
                attendanceMsg = "Absent marked for student " + studentName;
                break;
            case LATE:
                attendanceMsg = "Late marked for student " + studentName;
                break;
            default:
                attendanceMsg = "Attendance marked for student " + studentName;
        }
        return "redirect:/teacher/dashboard?attendanceMsg=" + java.net.URLEncoder.encode(attendanceMsg, java.nio.charset.StandardCharsets.UTF_8) + "&attendanceStatus=" + attendanceStatus.name();
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
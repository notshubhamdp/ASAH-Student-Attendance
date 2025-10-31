//package com.AS.Student_Attendance.controller;
//
//import com.AS.Student_Attendance.entity.User;
//import com.AS.Student_Attendance.entity.Courses;
//import com.AS.Student_Attendance.enumDto.ApprovalStatus;
//import com.AS.Student_Attendance.enumDto.Role;
//import com.AS.Student_Attendance.repository.AttendanceRepository;
//import com.AS.Student_Attendance.repository.UserRepository;
//import com.AS.Student_Attendance.repository.CoursesRepository;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.Collections;
//
//@Controller
//public class TeacherDashboardController {
//    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TeacherDashboardController.class);
//
//    private final UserRepository userRepository;
//    private final AttendanceRepository attendanceRepository;
//    private final CoursesRepository coursesRepository;
//
//    public TeacherDashboardController(UserRepository userRepository, AttendanceRepository attendanceRepository, CoursesRepository coursesRepository) {
//        this.userRepository = userRepository;
//        this.attendanceRepository = attendanceRepository;
//        this.coursesRepository = coursesRepository;
//    }
//
//    @GetMapping("/teacher/dashboard")
//    public String teacherDashboard(HttpSession session, Model model) {
//        String role = (String) session.getAttribute("role");
//        Integer userId = (Integer) session.getAttribute("userId");
//        String department = (String) session.getAttribute("department");
//        logger.info("Teacher dashboard accessed. Session role: {} userId: {} department: {}", role, userId, department);
//        if (role == null || userId == null) {
//            return "redirect:/login";
//        }
//        if (!role.equals("TEACHER")) {
//            return "redirect:/student/profile";
//        }
//    var allowedRoles = java.util.Collections.singletonList(Role.STUDENT);
//    java.util.List<User> pendingStudents = userRepository.findByRoleInAndStatus(allowedRoles, ApprovalStatus.PENDING);
//    logger.info("Pending students found: {}", pendingStudents.size());
//    for (User student : pendingStudents) {
//        logger.info("Pending student: username={}, department={}, status={}, role={}", student.getUsername(), student.getDepartment(), student.getStatus(), student.getRole());
//    }
//    java.util.List<User> approvedStudents = userRepository.findByRoleInAndStatus(allowedRoles, ApprovalStatus.APPROVED);
//    java.util.List<User> rejectedStudents = userRepository.findByRoleInAndStatus(allowedRoles, ApprovalStatus.REJECTED);
//    model.addAttribute("pendingStudents", pendingStudents);
//    model.addAttribute("approvedStudents", approvedStudents);
//    model.addAttribute("rejectedStudents", rejectedStudents);
//    model.addAttribute("students", approvedStudents);
//    // Fetch courses for the teacher's department
//    java.util.List<Courses> courses = department == null ? java.util.Collections.emptyList() : coursesRepository.findByBranch_BranchNameIgnoreCase(department);
//    model.addAttribute("courses", courses);
//    return "teacher_dashboard";
//    }
//
//    @PostMapping("/teacher/approve")
//    public String approveStudent(@RequestParam Integer userId, @RequestParam String rollNo, HttpSession session) {
//        User student = userRepository.findById(userId).orElse(null);
//        if (student != null) {
//            if (rollNo == null || rollNo.trim().isEmpty()) {
//                session.setAttribute("approveError", "Roll No. is required to approve student.");
//                return "redirect:/teacher/dashboard";
//            }
//            student.setStatus(ApprovalStatus.APPROVED);
//            student.setRollNo(rollNo);
//            if (student.getCreatedAt() == null) {
//                student.setCreatedAt(new java.sql.Time(new java.util.Date().getTime()));
//            }
//            userRepository.save(student);
//        }
//        session.removeAttribute("approveError");
//        return "redirect:/teacher/dashboard";
//    }
//
//    @PostMapping("/teacher/reject")
//    public String rejectStudent(@RequestParam Integer userId) {
//        User student = userRepository.findById(userId).orElse(null);
//        if (student != null) {
//            student.setStatus(ApprovalStatus.REJECTED);
//            if (student.getCreatedAt() == null) {
//                student.setCreatedAt(new java.sql.Time(new java.util.Date().getTime()));
//            }
//            userRepository.save(student);
//        }
//        return "redirect:/teacher/dashboard";
//    }
//}
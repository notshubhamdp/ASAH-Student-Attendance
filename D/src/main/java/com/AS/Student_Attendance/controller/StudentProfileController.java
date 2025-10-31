package com.AS.Student_Attendance.controller;

import com.AS.Student_Attendance.entity.User;
import com.AS.Student_Attendance.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentProfileController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/student/profile")
    public String getProfile(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        Integer userId = (Integer) session.getAttribute("userId");
        if (role == null || userId == null) {
            return "redirect:/login";
        }
        if (!role.equals("STUDENT")) {
            return "redirect:/teacher/dashboard";
        }
        User student = userRepository.findById(userId).orElse(null);
        if (student != null) {
            model.addAttribute("name", student.getFirstName() + " " + student.getLastName());
            model.addAttribute("rollNo", student.getRollNo());
            model.addAttribute("department", student.getDepartment());
            model.addAttribute("email", student.getEmail());
        }
        return "profile";
    }
}

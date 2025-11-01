package com.AS.Student_Attendance.controller;

import com.AS.Student_Attendance.entity.User;
import com.AS.Student_Attendance.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller("webProfileController")
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String getProfile(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        if (role == null) {
            return "redirect:/login";
        }
        User user = null;
        if ("STUDENT".equals(role)) {
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId == null) return "redirect:/login";
            user = userRepository.findById(userId).orElse(null);
        } else if ("TEACHER".equals(role)) {
            Integer teacherId = (Integer) session.getAttribute("teacherId");
            if (teacherId != null) {
                user = userRepository.findById(teacherId).orElse(null);
            } else {
                String email = (String) session.getAttribute("email");
                if (email != null) user = userRepository.findByEmail(email);
            }
        } else if ("ADMIN".equals(role)) {
            Integer adminId = (Integer) session.getAttribute("adminId");
            if (adminId != null) {
                user = userRepository.findById(adminId).orElse(null);
            } else {
                String email = (String) session.getAttribute("email");
                if (email != null) {
                    user = userRepository.findByEmail(email);
                } else {
                    String username = (String) session.getAttribute("username");
                    if (username != null) user = userRepository.findByUsername(username);
                }
            }
        } else {
            String username = (String) session.getAttribute("username");
            if (username != null) user = userRepository.findByUsername(username);
        }

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("department", user.getDepartment());
        model.addAttribute("role", user.getRole() != null ? user.getRole().name() : "");
        model.addAttribute("createdAt", user.getCreatedAt());
        model.addAttribute("rollNo", user.getRollNo());
        return "profile";
    }

    @PostMapping
    public String updateProfile(@ModelAttribute User updatedUser, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role == null) return "redirect:/login";
        User user = null;
        if ("STUDENT".equals(role)) {
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId == null) return "redirect:/login";
            user = userRepository.findById(userId).orElse(null);
        } else if ("TEACHER".equals(role)) {
            Integer teacherId = (Integer) session.getAttribute("teacherId");
            if (teacherId != null) user = userRepository.findById(teacherId).orElse(null);
            else {
                String email = (String) session.getAttribute("email");
                if (email != null) user = userRepository.findByEmail(email);
            }
        } else if ("ADMIN".equals(role)) {
            Integer adminId = (Integer) session.getAttribute("adminId");
            if (adminId != null) {
                user = userRepository.findById(adminId).orElse(null);
            } else {
                String email = (String) session.getAttribute("email");
                if (email != null) {
                    user = userRepository.findByEmail(email);
                } else {
                    String username = (String) session.getAttribute("username");
                    if (username != null) user = userRepository.findByUsername(username);
                }
            }
        } else {
            String username = (String) session.getAttribute("username");
            if (username != null) user = userRepository.findByUsername(username);
        }
        if (user == null) return "redirect:/login";
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());
        user.setDepartment(updatedUser.getDepartment());
        user.setPassword(updatedUser.getPassword());
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(new java.sql.Time(new java.util.Date().getTime()));
        }
        userRepository.save(user);
        return "redirect:/profile";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
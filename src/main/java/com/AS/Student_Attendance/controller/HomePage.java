package com.AS.Student_Attendance.controller;

import com.AS.Student_Attendance.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.AS.Student_Attendance.repository.UserRepository;
import com.AS.Student_Attendance.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomePage {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @PostMapping("/delete-account")
    public String deleteAccount(@AuthenticationPrincipal UserDetails principal, HttpServletRequest request) {
        if (principal != null) {
            String username = principal.getUsername();
            User user = userRepository.findByUsername(username);
            if (user != null && (user.getRole().name().equals("STUDENT") || user.getRole().name().equals("TEACHER"))) {
                userRepository.delete(user);
            }
        }
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }


    @GetMapping("/teachers")
    public String teachers() {
        return "teachers";
    }

    @GetMapping("/courses")
    public String courses() {
        return "courses";
    }



    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/attendance/view")
    public String attendance() {
        return "attendance";
    }

    @GetMapping("/profile/view")
    public String profile() {
        return "profile";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }


    @GetMapping("/teacher_dashboard")
    public String teacherDashboard() {
        return "teacher_dashboard";
    }

    @GetMapping("/student_dashboard")


   public String myProfile(Model model, @SessionAttribute(name = "username", required = false) String username) {

        if (username != null) {
            User user = userService.getUserByUsername(username);
            model.addAttribute("user", user);

            String rollNo = "";
            // If roll number is stored in User entity, fetch it. Otherwise, set as needed.
            // Example: If you have getRollNo(), use student.getRollNo()
            // For now, set as placeholder
            if (user != null) {
                try {
                    java.lang.reflect.Method method = user.getClass().getMethod("getRollNo");
                    rollNo = (String) method.invoke(user);
                } catch (Exception e) {
                    rollNo = "Assigned by Teacher";
                }
            } else {
                rollNo = "Assigned by Teacher";
            }
            model.addAttribute("rollNo", rollNo);
        }
        return "student_dashboard";
    }
    
}
    

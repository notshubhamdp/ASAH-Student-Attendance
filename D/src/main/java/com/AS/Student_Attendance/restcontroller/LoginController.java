package com.AS.Student_Attendance.restcontroller;

import com.AS.Student_Attendance.entity.User;
import com.AS.Student_Attendance.repository.UserRepository;
import com.AS.Student_Attendance.enumDto.ApprovalStatus;
import com.AS.Student_Attendance.repository.TeachersRepository;
import com.AS.Student_Attendance.entity.Teachers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
    private TeachersRepository teachersRepository;

	@PostMapping
	public Map<String, Object> login(@RequestBody Map<String, String> payload, HttpSession session) {
		String role = payload.get("role");
		String department = payload.get("department");
		Map<String, Object> response = new HashMap<>();


		if ("STUDENT".equalsIgnoreCase(role)) {
			String username = payload.get("username");
			String password = payload.get("password");
			User user = userRepository.findByUsername(username);
			// Debug logging for troubleshooting
			System.out.println("[DEBUG] Login attempt for student: username=" + username + ", department(from form)=" + department);
			if (user != null) {
				System.out.println("[DEBUG] Found user: username=" + user.getUsername() + ", department(from db)=" + user.getDepartment() + ", role=" + user.getRole().name());
			} else {
				System.out.println("[DEBUG] No user found for username: " + username);
			}
			if (user != null && user.getPassword().equals(password) && user.getRole().name().equals("STUDENT") && user.getDepartment() != null && user.getDepartment().equalsIgnoreCase(department)) {
				if (user.getStatus() == ApprovalStatus.APPROVED) {
					response.put("success", true);
					response.put("role", "STUDENT");
					response.put("message", "Login successful");
					session.setAttribute("username", user.getUsername());
					session.setAttribute("role", "STUDENT");
					session.setAttribute("userId", user.getUserId());
				} else if (user.getStatus() == ApprovalStatus.PENDING) {
					response.put("success", false);
					response.put("message", "Your registration is pending teacher approval.");
				} else if (user.getStatus() == ApprovalStatus.REJECTED) {
					response.put("success", false);
					response.put("message", "Your registration was rejected. Please contact your teacher.");
				} else {
					response.put("success", false);
					response.put("message", "Invalid account status.");
				}
			} else {
				response.put("success", false);
				response.put("message", "Invalid student credentials or department");
			}
			return response;
		}

		if ("TEACHER".equalsIgnoreCase(role)) {
            String email = payload.get("email");
            String password = payload.get("passwordTeacher");
            User user = userRepository.findByEmail(email);
            if (user != null && user.getEmail().equals(email) && user.getDepartment() != null && user.getDepartment().equalsIgnoreCase(department)) {
                if (user.getStatus() == ApprovalStatus.APPROVED) {
                    // You may want to check password here if stored in Teachers entity
                    response.put("success", true);
                    response.put("role", "TEACHER");
                    response.put("message", "Login successful");
                    session.setAttribute("email", user.getEmail());
                    session.setAttribute("role", "TEACHER");
                    session.setAttribute("teacherId", user.getUserId());
                    session.setAttribute("department", user.getDepartment());
                } else if (user.getStatus() == ApprovalStatus.PENDING) {
                    response.put("success", false);
                    response.put("message", "Your registration is pending admin approval.");
                } else if (user.getStatus() == ApprovalStatus.REJECTED) {
                    response.put("success", false);
                    response.put("message", "Your registration was rejected. Please contact the admin.");
                } else {
                    response.put("success", false);
                    response.put("message", "Invalid account status.");
                }
            } else {
                response.put("success", false);
                response.put("message", "Invalid teacher credentials or department");
            }
            return response;
        }

		if ("ADMIN".equalsIgnoreCase(role) || "admin".equalsIgnoreCase(role)) {
            String username = payload.get("usernameAdmin");
            String password = payload.get("passwordAdmin");
            if ("admin".equalsIgnoreCase(username) && "admin".equals(password)) {
                response.put("success", true);
                response.put("role", "ADMIN");
                response.put("message", "Admin login successful");
                session.setAttribute("username", "admin");
                session.setAttribute("role", "ADMIN");
            } else {
                response.put("success", false);
                response.put("message", "Invalid admin credentials");
            }
            return response;
        }

		response.put("success", false);
		response.put("message", "Invalid role selected");
		return response;
	}
}

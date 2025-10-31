package com.AS.Student_Attendance.restcontroller;

import com.AS.Student_Attendance.entity.User;
import com.AS.Student_Attendance.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student/dashboard")
public class StudentDashboardController {
	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public String getStudentDashboard(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
		if (username == null) {
			return "redirect:/login";
		}
		User user = userRepository.findByUsername(username);
		if (user == null) {
			return "redirect:/login";
		}
		model.addAttribute("studentName", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("department", user.getDepartment());
		model.addAttribute("email", user.getEmail());
		model.addAttribute("rollNo", user.getRollNo());
		return "student_dashboard";
	}
}

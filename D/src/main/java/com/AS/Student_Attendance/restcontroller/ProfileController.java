package com.AS.Student_Attendance.restcontroller;

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

@Controller
@RequestMapping("/profile")
public class ProfileController {
	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public String getProfile(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
		if (username == null) {
			return "redirect:/login";
		}
		User user = userRepository.findByUsername(username);
		if (user == null) {
			return "redirect:/login";
		}
		model.addAttribute("firstName", user.getFirstName());
		model.addAttribute("lastName", user.getLastName());
		model.addAttribute("email", user.getEmail());
		model.addAttribute("phone", user.getPhone());
		model.addAttribute("department", user.getDepartment());
		model.addAttribute("role", user.getRole().name());
		model.addAttribute("createdAt", user.getCreatedAt());
		model.addAttribute("rollNo", user.getRollNo());
		return "profile";
	}

	@PostMapping
	public String updateProfile(@ModelAttribute User updatedUser, HttpSession session) {
		String username = (String) session.getAttribute("username");
		if (username == null) {
			return "redirect:/login";
		}
		User user = userRepository.findByUsername(username);
		if (user == null) {
			return "redirect:/login";
		}
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

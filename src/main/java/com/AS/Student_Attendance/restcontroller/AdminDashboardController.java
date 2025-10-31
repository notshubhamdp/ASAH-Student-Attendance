package com.AS.Student_Attendance.restcontroller;
import com.AS.Student_Attendance.entity.Teachers;
import org.springframework.web.bind.annotation.PathVariable;

import com.AS.Student_Attendance.repository.StudentsRepository;
import com.AS.Student_Attendance.repository.AttendanceRepository;
import com.AS.Student_Attendance.repository.TeachersRepository;
import com.AS.Student_Attendance.enumDto.ApprovalStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminDashboardController {
	@Autowired
	private StudentsRepository studentsRepository;
	@Autowired
	private AttendanceRepository attendanceRepository;
	@Autowired
	private TeachersRepository teachersRepository;

	@GetMapping("/admin_dashboard")
	public String adminDashboard(Model model) {
		model.addAttribute("pendingTeachers", teachersRepository.findByStatus(ApprovalStatus.PENDING));
		return "admin_dashboard";
	}

	@GetMapping("/students_admin")
	public String studentsPage(Model model) {
		model.addAttribute("students", studentsRepository.findAll());
		return "students";
	}

	@GetMapping("/admin/reports")
	public String reportsPage(Model model) {
		model.addAttribute("attendanceRecords", attendanceRepository.findAll());
		return "reports";
	}

	@PostMapping("/admin/login")
	public String adminLogin(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
		if ("admin".equalsIgnoreCase(username) && "admin".equals(password)) {
			return "redirect:/admin_dashboard";
		} else {
			redirectAttributes.addFlashAttribute("errorMsg", "Invalid admin credentials!");
			return "redirect:/login";
		}
	}

	@PostMapping("/admin/reject-teacher/{id}")
	public String rejectTeacher(@PathVariable Integer id) {
		Teachers teacher = teachersRepository.findById(id).orElse(null);
		if (teacher != null) {
			teacher.setStatus(ApprovalStatus.REJECTED);
			teachersRepository.save(teacher);
		}
		return "redirect:/admin_dashboard";
	}
}

package com.AS.Student_Attendance.restcontroller;

import com.AS.Student_Attendance.entity.Attendance;
import com.AS.Student_Attendance.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ReportsController {

	private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);
	@Autowired
	private AttendanceRepository attendanceRepository;

	@GetMapping("/reports")
	public String getAttendanceReports(
		@org.springframework.web.bind.annotation.RequestParam(value = "student", required = false) Integer studentId,
		@org.springframework.web.bind.annotation.RequestParam(value = "course", required = false) Long courseId,
		@org.springframework.web.bind.annotation.RequestParam(value = "date", required = false) java.time.LocalDate date,
		Model model) {
		List<Attendance> attendanceRecords = attendanceRepository.findAll();
		// Always show all records, but allow filtering if needed
		if (studentId != null) {
			attendanceRecords = attendanceRecords.stream()
				.filter(a -> a.getUser().getUserId().equals(studentId))
				.toList();
		}
		if (courseId != null) {
			attendanceRecords = attendanceRecords.stream()
				   .filter(a -> a.getCourse().getCourseId().equals(courseId))
				.toList();
		}
		if (date != null) {
			attendanceRecords = attendanceRecords.stream()
				.filter(a -> a.getAttendanceDate().equals(date))
				.toList();
		}
		model.addAttribute("attendanceRecords", attendanceRecords);
		return "reports";
	}
}

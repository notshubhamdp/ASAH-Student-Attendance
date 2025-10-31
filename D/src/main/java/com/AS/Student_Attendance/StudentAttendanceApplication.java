package com.AS.Student_Attendance;

import com.AS.Student_Attendance.repository.*;

import jakarta.annotation.PostConstruct;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.AS.Student_Attendance.entity.Attendance;
// ...existing code...
import com.AS.Student_Attendance.entity.User;
import com.AS.Student_Attendance.entity.Courses;
import com.AS.Student_Attendance.enumDto.Role;
import com.AS.Student_Attendance.enumDto.ApprovalStatus;

@SpringBootApplication
public class StudentAttendanceApplication {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CoursesRepository coursesRepository;
	@Autowired
	private AttendanceRepository attendanceRepository;
	
	public static void main(String[] args) { SpringApplication.run(StudentAttendanceApplication.class, args); }
	
	@PostConstruct
	public void init() {
	



	// Create and save actual courses



		System.out.println("Student Attendance has Started");
	}
}

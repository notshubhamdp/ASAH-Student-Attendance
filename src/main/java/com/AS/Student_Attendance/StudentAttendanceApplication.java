package com.AS.Student_Attendance;

import com.AS.Student_Attendance.repository.*;

import jakarta.annotation.PostConstruct;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.AS.Student_Attendance.entity.Courses;
import com.AS.Student_Attendance.entity.Branch;
import com.AS.Student_Attendance.repository.BranchRepository;

@SpringBootApplication
public class StudentAttendanceApplication {

	@Autowired
	private CoursesRepository coursesRepository;
	@Autowired
	private BranchRepository branchRepository;

	public static void main(String[] args) { SpringApplication.run(StudentAttendanceApplication.class, args); }
	
	@PostConstruct
	public void init() {
		// Seed a default branch and courses if they do not exist
		String defaultBranchName = "General";
		Branch branch = branchRepository.findByBranchNameIgnoreCase(defaultBranchName);
		if (branch == null) {
			branch = new Branch();
			branch.setBranchName(defaultBranchName);
			branch = branchRepository.save(branch);
		}

		List<String> defaultCourses = Arrays.asList("OSY", "SEN", "DAN", "ENDS");
		for (String courseName : defaultCourses) {
			Courses existing = coursesRepository.findByCourseName(courseName);
			if (existing == null) {
				Courses c = new Courses();
				c.setCourseCode(courseName + "-101");
				c.setCourseName(courseName);
				c.setCredits(3);
				c.setBranch(branch);
				coursesRepository.save(c);
			}
		}

		System.out.println("Student Attendance has Started");
	}
}

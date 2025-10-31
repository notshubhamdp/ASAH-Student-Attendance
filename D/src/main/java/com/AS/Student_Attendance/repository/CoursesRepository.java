package com.AS.Student_Attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AS.Student_Attendance.entity.Courses;

import java.util.List;

public interface CoursesRepository extends JpaRepository<Courses, Integer> {
    Courses findByCourseCode(String courseCode);
    Courses findByCourseName(String courseName);
    List<Courses> findByCourseNameIgnoreCase(String courseName);
    List<Courses> findByBranch_BranchNameIgnoreCase(String branchName);
}

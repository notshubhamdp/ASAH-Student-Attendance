package com.AS.Student_Attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AS.Student_Attendance.entity.Students;

public interface StudentsRepository extends JpaRepository<Students, Integer> {
	boolean existsByFirstNameAndLastName(String firstName, String lastName);
}

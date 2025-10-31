package com.AS.Student_Attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.AS.Student_Attendance.entity.Teachers;
import com.AS.Student_Attendance.enumDto.ApprovalStatus;

import jakarta.persistence.criteria.CriteriaBuilder.In;

import java.util.List;

public interface TeachersRepository extends JpaRepository<Teachers, Integer> {
    Teachers findByEmail(String email);
    Teachers findByEmailAndStatus(String email, ApprovalStatus status);
    List<Teachers> findByStatus(ApprovalStatus status);
}

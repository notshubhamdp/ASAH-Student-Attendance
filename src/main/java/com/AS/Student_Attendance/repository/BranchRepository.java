package com.AS.Student_Attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AS.Student_Attendance.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
    Branch findByBranchNameIgnoreCase(String branchName);
}

package com.AS.Student_Attendance.repository;

import com.AS.Student_Attendance.entity.Teachers;
import com.AS.Student_Attendance.enumDto.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
// Make sure the User class exists at the specified package path.
// If the package is different, update the import accordingly.
// For example, if the correct package is com.AS.Student_Attendance.entity, use:
import com.AS.Student_Attendance.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	boolean existsByUsername(String username);
    User findByEmail(String email);
	boolean existsByEmail(String email);
	User findByUsername(String username);
	// Only fetch students and teachers, ignore admin
	List<User> findByRoleInAndStatus(List<com.AS.Student_Attendance.enumDto.Role> roles, com.AS.Student_Attendance.enumDto.ApprovalStatus status);
	List<User> findByRoleInAndStatusAndDepartment(List<com.AS.Student_Attendance.enumDto.Role> roles, com.AS.Student_Attendance.enumDto.ApprovalStatus status, String department);
    List<User> findByStatus(ApprovalStatus status);
	// New methods to fetch users by multiple roles (ignoring ADMIN)
}

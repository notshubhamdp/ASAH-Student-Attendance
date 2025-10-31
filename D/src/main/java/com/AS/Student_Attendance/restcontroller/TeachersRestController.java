package com.AS.Student_Attendance.restcontroller;

import com.AS.Student_Attendance.entity.Teachers;
import com.AS.Student_Attendance.entity.User;
import com.AS.Student_Attendance.enumDto.ApprovalStatus;
import com.AS.Student_Attendance.repository.TeachersRepository;
import com.AS.Student_Attendance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeachersRestController {
    @Autowired
    private TeachersRepository teachersRepository;

    @GetMapping
    public List<Teachers> getAllTeachers() {
        return teachersRepository.findAll();
    }

@Autowired
private UserRepository userRepository;

// Endpoint for teachers to view pending student requests
@GetMapping("/pending-students")
public List<User> getPendingStudents() {
    return userRepository.findByRoleInAndStatus(
        java.util.Collections.singletonList(com.AS.Student_Attendance.enumDto.Role.STUDENT),
        com.AS.Student_Attendance.enumDto.ApprovalStatus.PENDING
    );
}

@GetMapping("/{id}")
public Teachers getTeacherById(@PathVariable Integer id) {
    return teachersRepository.findById(id).orElse(null);
}

    @PutMapping("/{id}")
    public Teachers updateTeacher(@PathVariable Integer id, @RequestBody Teachers teacher) {
        teacher.setTeacherId(id);
        return teachersRepository.save(teacher);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Integer id) {
        teachersRepository.deleteById(id);
    }

    @PutMapping("/{id}/approve")
    public Teachers approveTeacher(@PathVariable Integer id) {
        Teachers teacher = teachersRepository.findById(id).orElse(null);
        if (teacher != null) {
            teacher.setStatus(com.AS.Student_Attendance.enumDto.ApprovalStatus.APPROVED);
            return teachersRepository.save(teacher);
        }
        return null;
    }

    @PutMapping("/{id}/reject")
    public Teachers rejectTeacher(@PathVariable Integer id) {
        Teachers teacher = teachersRepository.findById(id).orElse(null);
        if (teacher != null) {
            teacher.setStatus(com.AS.Student_Attendance.enumDto.ApprovalStatus.REJECTED);
            return teachersRepository.save(teacher);
        }
        return null;
    }

    @GetMapping("/pending")
    public List<Teachers> getPendingTeachers() {
        return teachersRepository.findByStatus(com.AS.Student_Attendance.enumDto.ApprovalStatus.PENDING);
    }
        @GetMapping("/approved")
        public List<Teachers> getApprovedTeachers() {
            return teachersRepository.findByStatus(com.AS.Student_Attendance.enumDto.ApprovalStatus.APPROVED);
        }
    @GetMapping("/approved-students")
    public List<User> getAprovedStudents() {
        return userRepository.findByRoleInAndStatus(
                java.util.Collections.singletonList(com.AS.Student_Attendance.enumDto.Role.STUDENT),
                ApprovalStatus.APPROVED
        );
    }
        @GetMapping("/rejected-students")
        public List<User> getrejectedStudents() {
            return userRepository.findByRoleInAndStatus(
                    java.util.Collections.singletonList(com.AS.Student_Attendance.enumDto.Role.STUDENT),
                    ApprovalStatus.REJECTED
            );

    }
}

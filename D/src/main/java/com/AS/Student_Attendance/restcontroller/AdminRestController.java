package com.AS.Student_Attendance.restcontroller;

import com.AS.Student_Attendance.entity.Teachers;
import com.AS.Student_Attendance.enumDto.ApprovalStatus;
import com.AS.Student_Attendance.repository.TeachersRepository;
import com.AS.Student_Attendance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminRestController {
    @Autowired
    private TeachersRepository teachersRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/approve-teacher/{id}")
    public ResponseEntity<String> approveTeacher(@PathVariable Integer id) {
        Teachers teacher = teachersRepository.findById(id).orElse(null);
        if (teacher == null) {
            return ResponseEntity.badRequest().body("Teacher not found");
        }
        teacher.setStatus(ApprovalStatus.APPROVED);
        teachersRepository.save(teacher);
        // Also update the corresponding User entity
        com.AS.Student_Attendance.entity.User user = userRepository.findByEmail(teacher.getEmail());
        if (user != null) {
            user.setStatus(ApprovalStatus.APPROVED);
            userRepository.save(user);
        }
        return ResponseEntity.ok("Teacher approved successfully");
    }

    @GetMapping("/pending-teachers")
    public ResponseEntity<?> getPendingTeachers() {
        var pendingTeachers = teachersRepository.findByStatus(ApprovalStatus.PENDING);
        return ResponseEntity.ok(pendingTeachers);
    }
}


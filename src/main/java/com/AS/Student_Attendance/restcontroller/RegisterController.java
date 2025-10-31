package com.AS.Student_Attendance.restcontroller;

import com.AS.Student_Attendance.entity.User;
import com.AS.Student_Attendance.repository.UserRepository;
import com.AS.Student_Attendance.repository.TeachersRepository;
import com.AS.Student_Attendance.enumDto.ApprovalStatus;
import com.AS.Student_Attendance.mapper.TeacherMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeachersRepository teachersRepository;
    @Autowired
    private TeacherMapper teacherMapper;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        Map<String, String> response = new HashMap<>();
        logger.info("Received registration request for username: {}", user.getUsername());
        String role = user.getRole().name();
        if (!role.equals("STUDENT") && !role.equals("TEACHER")) {
            response.put("message", "Only STUDENT and TEACHER roles are allowed for registration.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> {
                errorMsg.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            });
            response.put("message", errorMsg.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else if (userRepository.existsByUsername(user.getUsername())) {
            logger.warn("Username already exists: {}", user.getUsername());
            response.put("message", "Username already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } else if (userRepository.existsByEmail(user.getEmail())) {
            logger.warn("Email already exists: {}", user.getEmail());
            response.put("message", "Email already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } else {
                try {
                    // Set createdAt if not provided
                    if (user.getCreatedAt() == null) {
                        user.setCreatedAt(new java.sql.Time(new java.util.Date().getTime()));
                    }
                    // Only students require approval; teacher is auto-approved
                    if (role.equals("STUDENT")) {
                        user.setStatus(ApprovalStatus.PENDING);
                        user.setRollNo(null);
                        userRepository.save(user);
                        logger.info("Student registered successfully: {}", user.getUsername());
                        response.put("message", "Student registered successfully. Awaiting teacher approval.");
                        return ResponseEntity.ok(response);
                    } else if (role.equals("TEACHER")) {
                        // Check if teacher already exists in Teachers table
                        if (teachersRepository.findByEmail(user.getEmail()) != null) {
                            logger.warn("Teacher with email already exists in Teachers table: {}", user.getEmail());
                            response.put("message", "Teacher with this email already exists. Please use a different email.");
                            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
                        }
                        com.AS.Student_Attendance.entity.Teachers teacher = new com.AS.Student_Attendance.entity.Teachers();
                        teacher.setName(user.getFirstName() + " " + user.getLastName());
                        teacher.setEmail(user.getEmail());
                        teacher.setPhone(user.getPhone());
                        teacher.setDepartment(user.getDepartment());
                        teacher.setCreatedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
                        teacher.setStatus(ApprovalStatus.PENDING);
                        teachersRepository.save(teacher);
                        user.setStatus(ApprovalStatus.PENDING);
                        user.setRollNo(null);
                        userRepository.save(user);
                        logger.info("Teacher registered successfully: {}", user.getUsername());
                        response.put("message", "Teacher registered successfully. Awaiting admin approval.");
                        return ResponseEntity.ok(response);
                    }
                } catch (org.springframework.dao.DataIntegrityViolationException ex) {
                    logger.error("Duplicate entry error during registration: {}", ex.getMessage());
                    response.put("message", "Email or username already exists. Please use a different one.");
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
                } catch (Exception ex) {
                    logger.error("Unexpected error during registration: {}", ex.getMessage());
                    response.put("message", "An unexpected error occurred. Please try again.");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
        }
    // unreachable code removed
        // If the role is neither STUDENT nor TEACHER, or some unexpected path occurs
        response.put("message", "Invalid registration request.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}

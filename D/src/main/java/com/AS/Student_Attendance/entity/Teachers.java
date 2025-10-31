package com.AS.Student_Attendance.entity;

import com.AS.Student_Attendance.enumDto.ApprovalStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.sql.Timestamp;

@Entity
@Table(name = "teachers")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Teachers {
   @Id
   @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
   private Integer teacherId;

   @Column(name = "name", nullable = false)
   private String name;

   @Column(name = "email", nullable = false, unique = true)
   private String email;

   @Column(name = "phone")
   private String phone;

   @Column(name = "department")
   private String department;

   @Column(name = "created_at", nullable = false)
   private Timestamp createdAt;

   @Column(name = "status")
   @Enumerated(EnumType.STRING)
   private ApprovalStatus status;

   // Getters and Setters
}

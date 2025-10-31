package com.AS.Student_Attendance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Students {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String rollNumber;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch; // Each student belongs to one branch

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendanceRecords; // Student’s attendance records

    @Column(nullable = false, unique = true)
    private String email;

}

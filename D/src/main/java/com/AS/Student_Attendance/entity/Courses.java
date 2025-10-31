package com.AS.Student_Attendance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId; // matches what @JoinColumn references
    // matches DB column "course_id"

    private String courseCode;
    private String courseName;
    private Integer credits;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;
}

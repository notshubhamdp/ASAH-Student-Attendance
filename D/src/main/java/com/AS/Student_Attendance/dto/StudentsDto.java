package com.AS.Student_Attendance.dto;

// ...existing code...
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class StudentsDto {
    private Integer studentId;
    private String rollNo;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String department;
    private Integer year;
    private Timestamp createdAt;

}

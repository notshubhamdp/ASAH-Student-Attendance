package com.AS.Student_Attendance.dto;

// ...existing code...
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class TeachersDto {
    private Integer teacherId;
    private String name;
    private String email;
    private String phone;
    private String department;
    private Timestamp createdAt;
}

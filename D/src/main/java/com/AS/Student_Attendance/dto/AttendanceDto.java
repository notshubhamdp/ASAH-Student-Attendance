package com.AS.Student_Attendance.dto;

import com.AS.Student_Attendance.entity.Courses;
import com.AS.Student_Attendance.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttendanceDto {
    private Long attendanceId;
    private User user;
    private Courses classEntity;
    private LocalDate attendanceDate;
    private Boolean present;
    private Time createdAt;
}

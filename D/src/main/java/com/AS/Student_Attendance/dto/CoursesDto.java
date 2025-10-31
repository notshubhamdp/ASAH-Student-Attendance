package com.AS.Student_Attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CoursesDto {
    private Integer courseId;
    private String courseName;
    private String courseCode;
    private Integer credits;
}

package com.AS.Student_Attendance.mapper;

import org.mapstruct.Mapper;

import com.AS.Student_Attendance.dto.StudentsDto;
import com.AS.Student_Attendance.entity.Students;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    Students toStudentEntity(StudentsDto studentsDto);
    StudentsDto toStudentsDto(Students students);
}

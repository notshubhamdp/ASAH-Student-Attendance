package com.AS.Student_Attendance.mapper;

import org.mapstruct.Mapper;
import com.AS.Student_Attendance.dto.TeachersDto;
import com.AS.Student_Attendance.entity.Teachers;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    Teachers toTeacherEntity(TeachersDto teachersDto);
    TeachersDto toTeachersDto(Teachers teachers);
}

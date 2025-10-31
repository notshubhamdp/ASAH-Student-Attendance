package com.AS.Student_Attendance.mapper;

import org.mapstruct.Mapper;
import com.AS.Student_Attendance.dto.CoursesDto;
import com.AS.Student_Attendance.entity.Courses; // Add this import

@Mapper(componentModel = "spring")
public interface CourseMapper {
    Courses toCourseEntity(CoursesDto coursesDto);
    CoursesDto toCoursesDto(Courses courses);
}

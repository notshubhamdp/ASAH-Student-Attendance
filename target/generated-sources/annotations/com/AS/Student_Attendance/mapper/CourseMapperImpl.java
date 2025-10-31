package com.AS.Student_Attendance.mapper;

import com.AS.Student_Attendance.dto.CoursesDto;
import com.AS.Student_Attendance.entity.Courses;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-31T21:52:05+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Override
    public Courses toCourseEntity(CoursesDto coursesDto) {
        if ( coursesDto == null ) {
            return null;
        }

        Courses courses = new Courses();

        courses.setCourseId( coursesDto.getCourseId() );
        courses.setCourseCode( coursesDto.getCourseCode() );
        courses.setCourseName( coursesDto.getCourseName() );
        courses.setCredits( coursesDto.getCredits() );

        return courses;
    }

    @Override
    public CoursesDto toCoursesDto(Courses courses) {
        if ( courses == null ) {
            return null;
        }

        CoursesDto coursesDto = new CoursesDto();

        coursesDto.setCourseId( courses.getCourseId() );
        coursesDto.setCourseName( courses.getCourseName() );
        coursesDto.setCourseCode( courses.getCourseCode() );
        coursesDto.setCredits( courses.getCredits() );

        return coursesDto;
    }
}

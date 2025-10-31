package com.AS.Student_Attendance.mapper;

import com.AS.Student_Attendance.dto.CoursesDto;
import com.AS.Student_Attendance.entity.Courses;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-31T22:30:56+0530",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Override
    public Courses toCourseEntity(CoursesDto coursesDto) {
        if ( coursesDto == null ) {
            return null;
        }

        Courses courses = new Courses();

        courses.setCourseCode( coursesDto.getCourseCode() );
        courses.setCourseId( coursesDto.getCourseId() );
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

        coursesDto.setCourseCode( courses.getCourseCode() );
        coursesDto.setCourseId( courses.getCourseId() );
        coursesDto.setCourseName( courses.getCourseName() );
        coursesDto.setCredits( courses.getCredits() );

        return coursesDto;
    }
}

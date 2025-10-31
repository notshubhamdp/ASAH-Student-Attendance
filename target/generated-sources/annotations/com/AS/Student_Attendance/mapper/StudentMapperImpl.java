package com.AS.Student_Attendance.mapper;

import com.AS.Student_Attendance.dto.StudentsDto;
import com.AS.Student_Attendance.entity.Students;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-31T21:52:05+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public Students toStudentEntity(StudentsDto studentsDto) {
        if ( studentsDto == null ) {
            return null;
        }

        Students students = new Students();

        students.setFirstName( studentsDto.getFirstName() );
        students.setLastName( studentsDto.getLastName() );
        students.setEmail( studentsDto.getEmail() );

        return students;
    }

    @Override
    public StudentsDto toStudentsDto(Students students) {
        if ( students == null ) {
            return null;
        }

        StudentsDto studentsDto = new StudentsDto();

        studentsDto.setFirstName( students.getFirstName() );
        studentsDto.setLastName( students.getLastName() );
        studentsDto.setEmail( students.getEmail() );

        return studentsDto;
    }
}

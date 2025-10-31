package com.AS.Student_Attendance.mapper;

import com.AS.Student_Attendance.dto.StudentsDto;
import com.AS.Student_Attendance.entity.Students;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-31T22:30:55+0530",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public Students toStudentEntity(StudentsDto studentsDto) {
        if ( studentsDto == null ) {
            return null;
        }

        Students students = new Students();

        students.setEmail( studentsDto.getEmail() );
        students.setFirstName( studentsDto.getFirstName() );
        students.setLastName( studentsDto.getLastName() );

        return students;
    }

    @Override
    public StudentsDto toStudentsDto(Students students) {
        if ( students == null ) {
            return null;
        }

        StudentsDto studentsDto = new StudentsDto();

        studentsDto.setEmail( students.getEmail() );
        studentsDto.setFirstName( students.getFirstName() );
        studentsDto.setLastName( students.getLastName() );

        return studentsDto;
    }
}

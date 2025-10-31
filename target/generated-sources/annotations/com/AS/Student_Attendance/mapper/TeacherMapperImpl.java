package com.AS.Student_Attendance.mapper;

import com.AS.Student_Attendance.dto.TeachersDto;
import com.AS.Student_Attendance.entity.Teachers;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-31T21:52:06+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class TeacherMapperImpl implements TeacherMapper {

    @Override
    public Teachers toTeacherEntity(TeachersDto teachersDto) {
        if ( teachersDto == null ) {
            return null;
        }

        Teachers teachers = new Teachers();

        teachers.setTeacherId( teachersDto.getTeacherId() );
        teachers.setName( teachersDto.getName() );
        teachers.setEmail( teachersDto.getEmail() );
        teachers.setPhone( teachersDto.getPhone() );
        teachers.setDepartment( teachersDto.getDepartment() );
        teachers.setCreatedAt( teachersDto.getCreatedAt() );

        return teachers;
    }

    @Override
    public TeachersDto toTeachersDto(Teachers teachers) {
        if ( teachers == null ) {
            return null;
        }

        TeachersDto teachersDto = new TeachersDto();

        teachersDto.setTeacherId( teachers.getTeacherId() );
        teachersDto.setName( teachers.getName() );
        teachersDto.setEmail( teachers.getEmail() );
        teachersDto.setPhone( teachers.getPhone() );
        teachersDto.setDepartment( teachers.getDepartment() );
        teachersDto.setCreatedAt( teachers.getCreatedAt() );

        return teachersDto;
    }
}

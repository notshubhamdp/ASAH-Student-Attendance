package com.AS.Student_Attendance.mapper;

import com.AS.Student_Attendance.dto.TeachersDto;
import com.AS.Student_Attendance.entity.Teachers;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-01T08:07:08+0530",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class TeacherMapperImpl implements TeacherMapper {

    @Override
    public Teachers toTeacherEntity(TeachersDto teachersDto) {
        if ( teachersDto == null ) {
            return null;
        }

        Teachers teachers = new Teachers();

        teachers.setCreatedAt( teachersDto.getCreatedAt() );
        teachers.setDepartment( teachersDto.getDepartment() );
        teachers.setEmail( teachersDto.getEmail() );
        teachers.setName( teachersDto.getName() );
        teachers.setPhone( teachersDto.getPhone() );
        teachers.setTeacherId( teachersDto.getTeacherId() );

        return teachers;
    }

    @Override
    public TeachersDto toTeachersDto(Teachers teachers) {
        if ( teachers == null ) {
            return null;
        }

        TeachersDto teachersDto = new TeachersDto();

        teachersDto.setCreatedAt( teachers.getCreatedAt() );
        teachersDto.setDepartment( teachers.getDepartment() );
        teachersDto.setEmail( teachers.getEmail() );
        teachersDto.setName( teachers.getName() );
        teachersDto.setPhone( teachers.getPhone() );
        teachersDto.setTeacherId( teachers.getTeacherId() );

        return teachersDto;
    }
}

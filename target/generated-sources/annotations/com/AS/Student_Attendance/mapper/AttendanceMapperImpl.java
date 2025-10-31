package com.AS.Student_Attendance.mapper;

import com.AS.Student_Attendance.dto.AttendanceDto;
import com.AS.Student_Attendance.entity.Attendance;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-31T21:52:05+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class AttendanceMapperImpl implements AttendanceMapper {

    @Override
    public Attendance toAttendanceEntity(AttendanceDto attendanceDto) {
        if ( attendanceDto == null ) {
            return null;
        }

        Attendance attendance = new Attendance();

        attendance.setAttendanceId( attendanceDto.getAttendanceId() );
        attendance.setUser( attendanceDto.getUser() );
        attendance.setAttendanceDate( attendanceDto.getAttendanceDate() );
        attendance.setCreatedAt( attendanceDto.getCreatedAt() );

        return attendance;
    }

    @Override
    public AttendanceDto toAttendanceDto(Attendance attendance) {
        if ( attendance == null ) {
            return null;
        }

        AttendanceDto attendanceDto = new AttendanceDto();

        attendanceDto.setAttendanceId( attendance.getAttendanceId() );
        attendanceDto.setUser( attendance.getUser() );
        attendanceDto.setAttendanceDate( attendance.getAttendanceDate() );
        attendanceDto.setCreatedAt( attendance.getCreatedAt() );

        return attendanceDto;
    }
}

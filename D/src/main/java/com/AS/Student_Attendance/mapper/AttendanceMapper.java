package com.AS.Student_Attendance.mapper;

import org.mapstruct.Mapper;
import com.AS.Student_Attendance.dto.AttendanceDto;
import com.AS.Student_Attendance.entity.Attendance;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {
    Attendance toAttendanceEntity(AttendanceDto attendanceDto);
    AttendanceDto toAttendanceDto(Attendance attendance);
}

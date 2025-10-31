package com.AS.Student_Attendance.mapper;

import org.mapstruct.Mapper;

import com.AS.Student_Attendance.dto.UserDto;
import com.AS.Student_Attendance.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
   User toUserEntity(UserDto userDto);
   UserDto toUserDto(User user);
}

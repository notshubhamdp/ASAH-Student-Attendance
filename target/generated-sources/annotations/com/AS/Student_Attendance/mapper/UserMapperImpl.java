package com.AS.Student_Attendance.mapper;

import com.AS.Student_Attendance.dto.UserDto;
import com.AS.Student_Attendance.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-31T21:52:05+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUserEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setUserId( userDto.getUserId() );
        user.setUsername( userDto.getUsername() );
        user.setFirstName( userDto.getFirstName() );
        user.setLastName( userDto.getLastName() );
        user.setEmail( userDto.getEmail() );
        user.setPhone( userDto.getPhone() );
        user.setDepartment( userDto.getDepartment() );
        user.setRole( userDto.getRole() );
        user.setPassword( userDto.getPassword() );
        user.setCreatedAt( userDto.getCreatedAt() );

        return user;
    }

    @Override
    public UserDto toUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setUserId( user.getUserId() );
        userDto.setUsername( user.getUsername() );
        userDto.setFirstName( user.getFirstName() );
        userDto.setLastName( user.getLastName() );
        userDto.setEmail( user.getEmail() );
        userDto.setPhone( user.getPhone() );
        userDto.setDepartment( user.getDepartment() );
        userDto.setRole( user.getRole() );
        userDto.setPassword( user.getPassword() );
        userDto.setCreatedAt( user.getCreatedAt() );

        return userDto;
    }
}

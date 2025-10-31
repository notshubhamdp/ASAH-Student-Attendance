package com.AS.Student_Attendance.mapper;

import com.AS.Student_Attendance.dto.UserDto;
import com.AS.Student_Attendance.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-31T22:30:57+0530",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUserEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setCreatedAt( userDto.getCreatedAt() );
        user.setDepartment( userDto.getDepartment() );
        user.setEmail( userDto.getEmail() );
        user.setFirstName( userDto.getFirstName() );
        user.setLastName( userDto.getLastName() );
        user.setPassword( userDto.getPassword() );
        user.setPhone( userDto.getPhone() );
        user.setRole( userDto.getRole() );
        user.setUserId( userDto.getUserId() );
        user.setUsername( userDto.getUsername() );

        return user;
    }

    @Override
    public UserDto toUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setCreatedAt( user.getCreatedAt() );
        userDto.setDepartment( user.getDepartment() );
        userDto.setEmail( user.getEmail() );
        userDto.setFirstName( user.getFirstName() );
        userDto.setLastName( user.getLastName() );
        userDto.setPassword( user.getPassword() );
        userDto.setPhone( user.getPhone() );
        userDto.setRole( user.getRole() );
        userDto.setUserId( user.getUserId() );
        userDto.setUsername( user.getUsername() );

        return userDto;
    }
}

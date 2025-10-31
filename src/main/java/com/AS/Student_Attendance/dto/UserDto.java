package com.AS.Student_Attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Time;
import com.AS.Student_Attendance.enumDto.Role;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserDto {
    private Integer userId;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;
    private String department;

    private Role role;

    private String password;

    private Time createdAt;
}

package org.sparta.personalproject.dto;

import lombok.Getter;
import lombok.Setter;
import org.sparta.personalproject.entity.UserRoleEnum;
import org.sparta.personalproject.entity.User;

@Getter
@Setter
public class UserResponseDto {

    private Long id;

    private String username;

    private String password;

    private UserRoleEnum role;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
    }
}

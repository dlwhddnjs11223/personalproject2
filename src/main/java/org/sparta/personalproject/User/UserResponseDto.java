package org.sparta.personalproject.User;

import lombok.Getter;
import lombok.Setter;

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

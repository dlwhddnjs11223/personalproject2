package org.sparta.personalproject.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    private String username;
    private String role;
    private String password;
}

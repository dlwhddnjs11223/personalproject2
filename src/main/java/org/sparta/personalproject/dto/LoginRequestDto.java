package org.sparta.personalproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    private String username;
    private String role;
    private String password;
}

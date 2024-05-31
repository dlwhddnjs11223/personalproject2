package org.sparta.personalproject.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReqeustDto {

    private String nickname;

    @Size(min=4, max=10, message = "최소 4자, 최대 10자로 입력해주세요.")
    @Pattern(regexp= "^[a-z0-9]*$", message = "0부터 9까지의 숫자나 소문자 알파벳만 입력 가능합니다.")
    private String username;

    @Size(min=8, max=15)
    private String password;



}

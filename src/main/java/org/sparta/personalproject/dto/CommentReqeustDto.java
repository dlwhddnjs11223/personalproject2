package org.sparta.personalproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentReqeustDto {
    @NotBlank(message = "내용이 입력되지 않았습니다.")
    private String content;



}

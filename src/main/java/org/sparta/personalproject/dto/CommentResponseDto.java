package org.sparta.personalproject.dto;

import lombok.Getter;
import lombok.Setter;
import org.sparta.personalproject.entity.Comment;
import org.sparta.personalproject.entity.User;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {
    private Long id;
    private String content;
    private User user;
    private LocalDateTime createdAt;


    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.user = comment.getUser();
        this.createdAt = comment.getCreatedAt();

    }
}

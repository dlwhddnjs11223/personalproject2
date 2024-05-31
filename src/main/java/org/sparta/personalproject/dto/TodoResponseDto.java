package org.sparta.personalproject.dto;

import lombok.Getter;
import lombok.Setter;
import org.sparta.personalproject.entity.Todo;

import java.time.LocalDateTime;


@Setter
@Getter
public class TodoResponseDto {
    private long id;
    private String title;
    private String content;
    private String person;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.person = todo.getPerson();
        this.createdAt = todo.getCreatedAt();
        this.modifiedAt = todo.getModifiedAt();
    }

}



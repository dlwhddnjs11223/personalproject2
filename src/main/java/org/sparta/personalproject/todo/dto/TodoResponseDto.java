package org.sparta.personalproject.todo.dto;

import lombok.Getter;
import lombok.Setter;
import org.sparta.personalproject.todo.entity.Todo;

import java.time.LocalDateTime;


@Setter
@Getter
public class TodoResponseDto {
    long id;
    String title;
    String content;
    String person;
    long date;
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



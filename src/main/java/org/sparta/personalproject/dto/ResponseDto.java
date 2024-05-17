package org.sparta.personalproject.dto;

import lombok.Getter;
import lombok.Setter;
import org.sparta.personalproject.entity.Schedule;

import java.time.LocalDateTime;


@Setter
@Getter
public class ResponseDto {
    long id;
    String title;
    String content;
    String person;
    long date;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.person = schedule.getPerson();
        this.date = schedule.getDate();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
    }

}



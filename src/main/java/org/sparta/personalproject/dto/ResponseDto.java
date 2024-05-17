package org.sparta.personalproject.dto;

import lombok.Getter;
import lombok.Setter;
import org.sparta.personalproject.entity.Schedule;


@Setter
@Getter
public class ResponseDto {
    long id;
    String title;
    String content;
    String person;
    long date;

    public ResponseDto(Schedule schedule) {
        this.id=schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.person = schedule.getPerson();
        this.date = schedule.getDate();
    }

    public ResponseDto(Long id, String title, String content, String person, Long date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.person = person;
        this.date = date;
    }
}



package org.sparta.personalproject.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

}

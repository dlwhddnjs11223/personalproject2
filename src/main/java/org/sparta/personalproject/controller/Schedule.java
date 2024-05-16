package org.sparta.personalproject.controller;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Schedule {
    private String title;
    private String content;
    private String person;
    private long pw;
    private long date;
    private long id;

    public Schedule(RequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.person = requestDto.getPerson();
        this.pw = requestDto.getPw();
        this.date = requestDto.getDate();
    }



    public void update(RequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.person = requestDto.getPerson();
        this.date = requestDto.getDate();

    }
}

package org.sparta.personalproject.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestDto {
    String title;
    String content;
    String person;
    long pw;
    long date;


    public RequestDto(Schedule schedule) {
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.person = schedule.getPerson();
        this.pw = schedule.getPw();
        this.date = schedule.getDate();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPerson() {
        return person;
    }

    public long getPw() {
        return pw;
    }

    public long getDate() {
        return date;
    }
}

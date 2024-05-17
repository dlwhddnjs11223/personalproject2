package org.sparta.personalproject.service;


import org.sparta.personalproject.Repository.Repository;
import org.sparta.personalproject.controller.Pw;
import org.sparta.personalproject.dto.RequestDto;
import org.sparta.personalproject.dto.ResponseDto;
import org.sparta.personalproject.entity.Schedule;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class Service {


    private final Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public ResponseDto createSchedule(RequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);
        repository.save(schedule);
        return new ResponseDto(schedule);
    }


    public Optional<Schedule> getSchedule(long id) {
        Optional<Schedule> schedule = repository.findById(id);
        return schedule;
    }

    public List<ResponseDto> getSchedulelist() {
        return  repository.findAll().stream().sorted(Comparator.comparing(Schedule::getDate)).map(ResponseDto::new).toList();


    }

    @Transactional
    public ResponseDto updateSchedule(long id, RequestDto requestDto) {
        Schedule schedule = findSchedule(id);

        if (schedule.getPw() == requestDto.getPw()) {
            schedule.update(requestDto);

        } else {
            throw new IllegalArgumentException("비번 안맞음.");
        }

        return new ResponseDto(schedule);
    }

    public ResponseDto deleteSchedule(long id, Pw pw) {
        Schedule schedule = findSchedule(id);
        if (schedule.getPw() == pw.getPw()) {
            repository.delete(schedule);
            return new ResponseDto(schedule);
        } else {
            throw new IllegalArgumentException("비번 안맞음.");
        }
    }

    private Schedule findSchedule(long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("없음"));
    }
}
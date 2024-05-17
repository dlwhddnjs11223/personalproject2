package org.sparta.personalproject.service;


import org.sparta.personalproject.Repository.scheduleRepository;
import org.sparta.personalproject.controller.Pw;
import org.sparta.personalproject.dto.RequestDto;
import org.sparta.personalproject.dto.ResponseDto;
import org.sparta.personalproject.entity.Schedule;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@org.springframework.stereotype.Service
public class scheduleService {


    private final scheduleRepository scheduleRepository;

    public scheduleService(scheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ResponseDto createSchedule(RequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);
        scheduleRepository.save(schedule);
        return new ResponseDto(schedule);
    }

    public Schedule getSchedule(long id) {
        Schedule schedule = findSchedule(id);
        return schedule;
    }

    public List<ResponseDto> getSchedulelist() {
        return  scheduleRepository.findAll().stream().sorted(Comparator.comparing(Schedule::getCreatedAt)).map(ResponseDto::new).toList();
    }

    public List<ResponseDto> findScheduleByContent(String content) {
        return  scheduleRepository.findAllByContentContains(content);
    }

    @Transactional
    public ResponseDto updateSchedule(Long id, RequestDto requestDto) {
        Schedule schedule = findSchedule(id);

        if (schedule.getPw() == requestDto.getPw()) {
            schedule.update(requestDto);

        } else {
            throw new IllegalArgumentException("비번 안맞음.");
        }

        return new ResponseDto(schedule);
    }

    public ResponseDto deleteSchedule(Long id, Pw pw) {
        Schedule schedule = findSchedule(id);
        if (schedule.getPw() == pw.getPw()) {
            scheduleRepository.delete(schedule);
            return new ResponseDto(schedule);
        } else {
            throw new IllegalArgumentException("비번 안맞음.");
        }
    }

    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없음"));
    }
}
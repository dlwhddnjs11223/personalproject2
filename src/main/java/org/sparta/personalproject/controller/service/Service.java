package org.sparta.personalproject.controller.service;

import org.sparta.personalproject.Repository.Repository;
import org.sparta.personalproject.controller.Pw;
import org.sparta.personalproject.controller.entity.Schedule;
import org.sparta.personalproject.dto.RequestDto;
import org.sparta.personalproject.dto.ResponseDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;
import java.util.List;

public class Service {

    private final Repository repository;

    public Service(JdbcTemplate jdbcTemplate) throws SQLException {
        this.repository = new Repository(jdbcTemplate);
    }

    public ResponseDto createSchedule(RequestDto requestDto) {

        Schedule schedule = new Schedule(requestDto);

        return repository.addSchedule(schedule);


    }

    public ResponseDto getSchedule(long id) {

        Schedule schedule = repository.findSchedule(id);
        ResponseDto responseDto = new ResponseDto(schedule);
        return responseDto;
    }

    public List<ResponseDto> getSchedulelist() {

        return repository.getSchedulelist();

    }

    public ResponseDto updateSchedule(@PathVariable long id, @RequestBody RequestDto requestDto) {


        Schedule schedule = repository.findSchedule(id);
        if (schedule != null && schedule.getPw() == requestDto.getPw()) {
            return repository.updateSchedule(id, requestDto);
        } else {
            throw new IllegalArgumentException("해당 ID가 존재하지 않거나 비밀번호가 일치하지 않습니다.");
        }
    }

    public ResponseDto deleteSchedule(long id, Pw pw) {
        // 해당 schedule 가져오기

        Schedule schedule = repository.findSchedule(id);
        if (schedule != null && schedule.getPw() == pw.getPw()) {
            return repository.deleteSchedule(id);
        } else {
            throw new IllegalArgumentException("해당 ID가 존재하지 않거나 비밀번호가 일치하지 않습니다.");
        }
    }
}
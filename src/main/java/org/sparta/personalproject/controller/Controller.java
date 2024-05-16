package org.sparta.personalproject.controller;

import org.sparta.personalproject.controller.service.Service;
import org.sparta.personalproject.dto.RequestDto;
import org.sparta.personalproject.dto.ResponseDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("/api")
public class Controller {

    private final JdbcTemplate jdbcTemplate;


    public Controller(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
    1단계
     */
    @PostMapping("/schedules")
    public ResponseDto createSchedule(@RequestBody RequestDto requestDto) throws SQLException {
        Service service = new Service(jdbcTemplate);
        return service.createSchedule(requestDto);
    }

    /*
    2단계
     */
    @GetMapping("/schedules/{id}")
    public ResponseDto getSchedule(@PathVariable long id) throws SQLException {
        Service service = new Service(jdbcTemplate);
        return service.getSchedule(id);
    }

    /*
    3단계X
     */
    @GetMapping("/schedules")
    public List<ResponseDto> getSchedulelist() throws SQLException {
        Service service = new Service(jdbcTemplate);
        return service.getSchedulelist();
    }


//    /*
//    4단계
//     */
    @PutMapping("/schedules/{id}")
    public ResponseDto updateSchedule(@PathVariable long id, @RequestBody RequestDto requestDto) throws SQLException {
        Service service = new Service(jdbcTemplate);
        return service.updateSchedule(id, requestDto);
    }

    //
//    /*
//    5단계
//     */
    @DeleteMapping("/schedules/{id}")
    public ResponseDto deleteSchedule(@PathVariable long id, @RequestBody Pw pw) throws SQLException {
        Service service = new Service(jdbcTemplate);
        return service.deleteSchedule(id, pw);
    }
}

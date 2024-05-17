package org.sparta.personalproject.controller;

import org.sparta.personalproject.Repository.Repository;
import org.sparta.personalproject.entity.Schedule;
import org.sparta.personalproject.service.Service;
import org.sparta.personalproject.dto.RequestDto;
import org.sparta.personalproject.dto.ResponseDto;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class Controller {

    private final Service service;


    public Controller(Service service)  {
       this.service = service;

    }

    /*
    1단계
     */
    @PostMapping("/schedules")
    public ResponseDto createSchedule(@RequestBody RequestDto requestDto) {

        return service.createSchedule(requestDto);
    }

    /*
    2단계
     */
    @GetMapping("/schedules/{id}")
    public Optional<Schedule> getSchedule(@PathVariable long id)  {

        return service.getSchedule(id);
    }

    /*
    3단계X
     */
    @GetMapping("/schedules")
    public List<ResponseDto> getSchedulelist()  {

        return service.getSchedulelist();
    }


//    /*
//    4단계
//     */
    @PutMapping("/schedules/{id}")
    public ResponseDto updateSchedule(@PathVariable long id, @RequestBody RequestDto requestDto)  {

        return service.updateSchedule(id, requestDto);
    }

    //
//    /*
//    5단계
//     */
    @DeleteMapping("/schedules/{id}")
    public ResponseDto deleteSchedule(@PathVariable long id, @RequestBody Pw pw)  {

        return service.deleteSchedule(id, pw);
    }
}

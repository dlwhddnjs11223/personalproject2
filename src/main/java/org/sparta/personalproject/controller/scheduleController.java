package org.sparta.personalproject.controller;

import org.sparta.personalproject.dto.RequestDto;
import org.sparta.personalproject.dto.ResponseDto;
import org.sparta.personalproject.entity.Schedule;
import org.sparta.personalproject.service.scheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class scheduleController {

    private final scheduleService service;


    public scheduleController(scheduleService service)  {
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
    public Schedule getSchedule(@PathVariable long id)  {

        return service.getSchedule(id);
    }

    /*
    3단계X
     */
    @GetMapping("/schedules")
    public List<ResponseDto> getSchedulelist()  {

        return service.getSchedulelist();
    }

    @GetMapping("/schedules/param")
    public List<ResponseDto> findScheduleByContent(String content)  {

        return service.findScheduleByContent(content);
    }


//    /*
//    4단계
//     */
    @PutMapping("/schedules/param")
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

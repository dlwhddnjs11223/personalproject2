package org.sparta.personalproject.controller;

import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api")
public class Controller {

    private final Map<Long, Schedule>  scheduleList = new HashMap<>();
    long idNum=0;
    /*
    1단계
     */
    @PostMapping("/schedules")
    public ResponseDto createSchedule (@RequestBody RequestDto requestDto){

    // requestDto를 entity로 변환
    Schedule schedule = new Schedule(requestDto);
        schedule.setId(idNum);
    // entity를 데이터베이스에 저장
    scheduleList.put(idNum, schedule);
        idNum++;
    // entity를 responseDto로 변환
        return new ResponseDto(schedule);
    }

    /*
    2단계
     */
    @GetMapping("/schedules/{id}")
    public ResponseDto getSchedule (@PathVariable long id)  {

        Schedule schedule = findSchedule(id);
            ResponseDto responseDto = new ResponseDto(schedule);
            return responseDto;
       }
    /*
    3단계
     */
    @GetMapping("/schedules")
    public List<ResponseDto> getSchedulelist (){
    List<ResponseDto> responseDtoList = scheduleList.values()
            .stream()
            .map(ResponseDto::new)
            .toList();
    return responseDtoList;
    }
    /*
    4단계
     */
    @PutMapping("/schedules/{id}")
    public ResponseDto updateSchedule (@PathVariable long id, @RequestBody RequestDto requestDto)  {
        // 해당 schedule 가져오기
        Schedule schedule = findSchedule(id);

        schedule.update(requestDto);
        return new ResponseDto(schedule);
    }

    /*
    5단계
     */
    @DeleteMapping("/schedules/{id}")
    public ResponseDto deleteSchedule (@PathVariable long id)  {
        // 해당 schedule 가져오기
        Schedule schedule = findSchedule(id);

        scheduleList.remove(schedule.getId());
        return new ResponseDto(schedule);
    }

    /*
    ID값으로 entity 찾아오는 메서드
     */
    public Schedule findSchedule (long id)  {
       if(scheduleList.containsKey(id)){
           Schedule schedule = scheduleList.get(id);

           return schedule;
      } else{
           throw new IllegalArgumentException("선택한 일정이 존재하지 않습니다.");
       }

    }
}

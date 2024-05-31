package org.sparta.personalproject.controller;

import org.sparta.personalproject.security.entity.UserDetailsImpl;
import org.sparta.personalproject.dto.Pw;
import org.sparta.personalproject.dto.TodoRequestDto;
import org.sparta.personalproject.dto.TodoResponseDto;
import org.sparta.personalproject.service.TodoService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")

public class TodoController {

    private final TodoService service;


    public TodoController(TodoService service)  {
       this.service = service;

    }

    /*
    1단계
     */
    @PostMapping("/todo")
    public TodoResponseDto createSchedule(@RequestBody TodoRequestDto todoRequestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return service.createTodo(todoRequestDto, userDetails);
    }

    /*
    2단계
     */
    @GetMapping("/todo/{id}")
    public TodoResponseDto getSchedule(@PathVariable long id)  {

        return service.getTodo(id);
    }

    /*
    3단계X
     */
    @GetMapping("/todo")
    public List<TodoResponseDto> getSchedulelist()  {

        return service.getToodoList();
    }


//    /*
//    4단계
//     */

    @PutMapping("/todo/{id}")
    public TodoResponseDto updateSchedule(@PathVariable long id,
                                          @RequestBody TodoRequestDto todoRequestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails)  {

        return service.updateTodo(id, todoRequestDto, userDetails);
    }

    //
//    /*
//    5단계
//     */
    @DeleteMapping("/todo/{id}")
    public String deleteSchedule(@PathVariable long id,
                                          @RequestBody Pw pw,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails)  {

        return "삭제 완료";
    }
}

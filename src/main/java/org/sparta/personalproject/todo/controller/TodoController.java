package org.sparta.personalproject.todo.controller;

import org.sparta.personalproject.security.entity.UserDetailsImpl;
import org.sparta.personalproject.todo.dto.Pw;
import org.sparta.personalproject.todo.dto.TodoRequestDto;
import org.sparta.personalproject.todo.dto.TodoResponseDto;
import org.sparta.personalproject.todo.entity.Todo;
import org.sparta.personalproject.todo.service.TodoService;
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
    @PostMapping("/schedules")
    public TodoResponseDto createSchedule(@RequestBody TodoRequestDto todoRequestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return service.createTodo(todoRequestDto, userDetails);
    }

    /*
    2단계
     */
    @GetMapping("/schedules/{id}")
    public Todo getSchedule(@PathVariable long id)  {

        return service.getTodo(id);
    }

    /*
    3단계X
     */
    @GetMapping("/schedules")
    public List<TodoResponseDto> getSchedulelist()  {

        return service.getToodoList();
    }


//    /*
//    4단계
//     */

    @PutMapping("/schedules/{id}")
    public TodoResponseDto updateSchedule(@PathVariable long id,
                                          @RequestBody TodoRequestDto todoRequestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails)  {

        return service.updateTodo(id, todoRequestDto, userDetails);
    }

    //
//    /*
//    5단계
//     */
    @DeleteMapping("/schedules/{id}")
    public TodoResponseDto deleteSchedule(@PathVariable long id,
                                          @RequestBody Pw pw,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails)  {

        return service.deleteTodo(id, pw, userDetails);
    }
}

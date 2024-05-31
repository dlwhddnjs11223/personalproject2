package org.sparta.personalproject.service;


import org.sparta.personalproject.security.entity.UserDetailsImpl;
import org.sparta.personalproject.repository.TodoRepository;
import org.sparta.personalproject.dto.Pw;
import org.sparta.personalproject.dto.TodoRequestDto;
import org.sparta.personalproject.dto.TodoResponseDto;
import org.sparta.personalproject.entity.Todo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public TodoResponseDto createTodo(TodoRequestDto todoRequestDto, UserDetailsImpl userDetails) {
        Todo todo = new Todo(todoRequestDto, userDetails);
        todoRepository.save(todo);
        return new TodoResponseDto(todo);
    }

    public TodoResponseDto getTodo(long id) {
        Todo todo = findTodo(id);
        return new TodoResponseDto(todo);
    }

    public List<TodoResponseDto> getToodoList() {
        return todoRepository.findAll().stream().sorted(Comparator.comparing(Todo::getCreatedAt)).map(TodoResponseDto::new).toList();
    }

    @Transactional
    public TodoResponseDto updateTodo(Long id,
                                      TodoRequestDto todoRequestDto,
                                      UserDetailsImpl userDetails) {
        Todo todo = findTodo(id);

        checkPwUser(todo, todoRequestDto.getPw(), userDetails);

        todo.update(todoRequestDto);
        return new TodoResponseDto(todo);
    }

    public void deleteTodo(Long id, Pw pw, UserDetailsImpl userDetails) {
        Todo todo = findTodo(id);
        checkPwUser(todo, pw.getPw(), userDetails);
        todoRepository.delete(todo);

    }

    public Todo findTodo(Long id) {
        return todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없음"));
    }

    public void checkPwUser(Todo todo, String reqeustPw, UserDetailsImpl userDetails){
        if (todo.getPw() != reqeustPw) {
            throw new IllegalArgumentException("update, delete : 비밀번호가 일치하지 않습니다.");
        }
        if (todo.getUser() != userDetails.getUser()) {
            throw new IllegalArgumentException("update, delete : 사용자가 일치하지 않습니다.");
        }

    }
}
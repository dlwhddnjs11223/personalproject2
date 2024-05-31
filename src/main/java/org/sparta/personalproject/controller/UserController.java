package org.sparta.personalproject.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sparta.personalproject.dto.LoginRequestDto;
import org.sparta.personalproject.dto.UserReqeustDto;
import org.sparta.personalproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid UserReqeustDto userReqeustDto, HttpServletResponse response) {
        userService.signup(userReqeustDto);
       ResponseEntity<String> restr = new ResponseEntity<>("가입 완료",HttpStatus.OK);
        return restr;
    }

    @PostMapping("/login")
    public  ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto,response);
        return  new ResponseEntity<> ("로그인 및 header에 토큰 추가 완료",HttpStatus.OK);

    }

    @DeleteMapping("/delete/{userId}")
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }
}

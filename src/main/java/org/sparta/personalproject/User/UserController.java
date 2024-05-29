package org.sparta.personalproject.User;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid UserReqeustDto userReqeustDto) {
        userService.signup(userReqeustDto);
        return new ResponseEntity<> ("가입 완료",HttpStatus.OK);
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

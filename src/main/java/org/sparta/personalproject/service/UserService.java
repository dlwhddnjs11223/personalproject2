package org.sparta.personalproject.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sparta.personalproject.entity.UserRoleEnum;
import org.sparta.personalproject.dto.LoginRequestDto;
import org.sparta.personalproject.dto.UserReqeustDto;
import org.sparta.personalproject.dto.UserResponseDto;
import org.sparta.personalproject.entity.User;
import org.sparta.personalproject.repository.UserRepository;
import org.sparta.personalproject.security.jwt.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    public UserResponseDto signup(UserReqeustDto userReqeustDto) {

        String nickname = userReqeustDto.getUsername();
        String userName = passwordEncoder.encode(userReqeustDto.getPassword());
        UserRoleEnum role = UserRoleEnum.USER;
        //
        String password = userReqeustDto.getPassword();


        Optional<User> checkUsername = userRepository.findByUsername(userName);
        if(checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 username입니다.");
        }


        User user = new User(nickname,userName, password, role);
        userRepository.save(user);
        return new UserResponseDto(user);
    }



    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new IllegalArgumentException("회원을 찾을 수 없습니다."));
//
        if (passwordEncoder.matches(password, user.getPassword()) ) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

        }

        String accessToken = jwtUtil.createAccessToken(user.getUsername(), user.getRole());
        jwtUtil.addAccessJwtToHeader(accessToken, response);


        String refreshToken = refreshTokenService.createRefreshToken(user);
        jwtUtil.addRefreshJwtToHeader(refreshToken, response);


        }



    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(()->new IllegalArgumentException("중복된 username 입니다."));
    }

    public void delete(Long userId) {
        userRepository.delete(userRepository.findById(userId));
    }
}

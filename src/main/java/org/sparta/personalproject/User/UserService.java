package org.sparta.personalproject.User;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sparta.personalproject.refreshtoken.RefreshToken;
import org.sparta.personalproject.refreshtoken.RefreshTokenService;
import org.sparta.personalproject.security.jwt.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;


    public UserResponseDto signup(UserReqeustDto userReqeustDto) {

        String nickname = userReqeustDto.getUsername();
        String userName = userReqeustDto.getUsername();
        UserRoleEnum role = UserRoleEnum.USER;
        String password = passwordEncoder.encode(userReqeustDto.getPassword());


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

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

        }

        String accessToken = jwtUtil.createAccessToken(user.getUsername(), user.getRole());
        jwtUtil.addJwtToHeader(accessToken, response);


        String refreshtoken = refreshTokenService.createRefreshToken(user);
        jwtUtil.addJwtToCookie(refreshtoken, response);


        }



    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(()->new IllegalArgumentException("중복된 username 입니다."));
    }

    public void delete(Long userId) {
        userRepository.delete(userRepository.findById(userId));
    }
}

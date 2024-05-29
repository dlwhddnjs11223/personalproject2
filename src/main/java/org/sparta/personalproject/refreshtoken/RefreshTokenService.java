package org.sparta.personalproject.refreshtoken;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sparta.personalproject.User.User;
import org.sparta.personalproject.security.jwt.JwtUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public String createRefreshToken(User user) {

        String token = jwtUtil.createRefreshToken();
        RefreshToken refreshToken = new RefreshToken(token, user );
        refreshTokenRepository.save(refreshToken);
        return token;

    }

    public RefreshToken findByUser(User user) {
        return refreshTokenRepository.findByUser(user);

    }

    public void checkRefreshToken () {
        User user =
    }
}

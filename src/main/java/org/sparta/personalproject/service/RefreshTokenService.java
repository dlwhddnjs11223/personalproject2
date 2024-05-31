package org.sparta.personalproject.service;

import lombok.RequiredArgsConstructor;
import org.sparta.personalproject.entity.User;
import org.sparta.personalproject.entity.RefreshToken;
import org.sparta.personalproject.repository.RefreshTokenRepository;
import org.sparta.personalproject.security.jwt.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    @Transactional
    public String createRefreshToken(User user) {

        String token = jwtUtil.createRefreshToken();
        String tokenValue = jwtUtil.substringRefreshToken(token);
        RefreshToken refreshToken = findByUser(user);
        refreshToken.setRefreshToken(tokenValue);
        refreshToken.setUser(user);
        refreshTokenRepository.save(refreshToken);
        return token;

    }

    private RefreshToken findByUser(User user) {
    return refreshTokenRepository.findByUser(user).orElse(new RefreshToken());

    }

    @Transactional
    public String updateRefreshToken(RefreshToken refreshToken) {

        RefreshToken refreshToken1 = findByRefreshToken(refreshToken.getRefreshToken());
        String token = jwtUtil.createRefreshToken();
        String tokenValue = jwtUtil.substringRefreshToken(token);
        refreshToken1.setRefreshToken(tokenValue);

        return token;

    }


    public RefreshToken findByRefreshToken(String tokenValue) {
        return refreshTokenRepository.findByRefreshToken(tokenValue)
                .orElseThrow(() -> new IllegalArgumentException("리프레쉬 토큰을 찾을 수 없습니다."));

    }




}

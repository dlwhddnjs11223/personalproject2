package org.sparta.personalproject.security.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.personalproject.entity.User;
import org.sparta.personalproject.entity.UserRoleEnum;
import org.sparta.personalproject.entity.RefreshToken;
import org.sparta.personalproject.service.RefreshTokenService;
import org.sparta.personalproject.security.entity.UserDetailsServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain filterChain) throws ServletException, IOException {


        //req에서 jwt를 찾고
        String accesstokenValue = jwtUtil.getAccessTokenFromHeader(req);


        // 찾은 jwt를 substring
        if (StringUtils.hasText(accesstokenValue)) {
            // JWT 토큰 substring
            accesstokenValue = jwtUtil.substringAccessToken(accesstokenValue);

            if (!jwtUtil.validateAccessToken(accesstokenValue)) { // 액세스 토큰이 유효하지 않으면

                log.error("액세스 토큰 만료");

                //
                String RefreshTokenValue = jwtUtil.getRefreshTokenFromHeader(req);


                if (StringUtils.hasText(RefreshTokenValue)) {
                    // JWT 토큰 substring

                    RefreshTokenValue = jwtUtil.substringRefreshToken(RefreshTokenValue);
                    if (!jwtUtil.validateRefreshToken(RefreshTokenValue)) {
                        throw new IllegalArgumentException("리프레쉬 토큰이 만료됐음. 다시 로그인");
                    }

                    RefreshToken refreshToken = refreshTokenService.findByRefreshToken(RefreshTokenValue);
                    log.info("DB 기존 리프레쉬 토큰"+refreshToken.getRefreshToken());
                    User user = refreshToken.getUser();
                    String username = user.getUsername();
                    UserRoleEnum Role = user.getRole();

                    accesstokenValue = jwtUtil.createAccessToken(username, Role);
                    RefreshTokenValue = refreshTokenService.updateRefreshToken(refreshToken);
                    log.info("DB 새로운 리프레쉬 토큰"+refreshToken.getRefreshToken());

                    jwtUtil.addAccessJwtToHeader(accesstokenValue, res);
                    jwtUtil.addRefreshJwtToHeader(RefreshTokenValue, res);


                }
            }
            String newAccesstokenValue = jwtUtil.substringAccessToken(accesstokenValue);
            log.info("새로운 액세스 토큰:"+ newAccesstokenValue );
            Claims info = jwtUtil.getUserInfoFromToken(newAccesstokenValue);

            try {
                setAuthentication(info.getSubject());
                // info에 담긴 subjedct(name)로 인증객체 생성 및 인증처리
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }
        filterChain.doFilter(req, res);
    }


    //  인증 처리 = security context holder 생성해주는 것
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }


    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
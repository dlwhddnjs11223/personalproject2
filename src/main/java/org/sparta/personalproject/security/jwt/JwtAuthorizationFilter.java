package org.sparta.personalproject.security.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsServiceImpl userDetailsService;



    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {


        //req에서 jwt를 찾고
        String tokenValue = jwtUtil.getTokenFromHeader(req);


        // 찾은 jwt를 substring
        if (StringUtils.hasText(tokenValue)) {
            // JWT 토큰 substring
            tokenValue = jwtUtil.substringToken(tokenValue);
            log.info(tokenValue);

            if (!jwtUtil.validateAccessToken(tokenValue)) {
                log.error("Token Error");
//                if(checkRefreshToken(req, res);
                return;
            }
            // jwt에서 사용자 정보를 읽어오고
            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

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

    public void checkRefreshToken(HttpServletRequest req, Authentication authResult)  {
        String tokenValue = jwtUtil.getTokenFromCookie(req);
        String refreshToken =

                String accessToken = jwtUtil.createAccessToken(username, role);
        String refreshToken = jwtUtil.createRefreshToken();

        jwtUtil.addJwtToHeader(accessToken, response);
        jwtUtil.addJwtToCookie(refreshToken, response);

    }


    // 인증 처리 = security context holder 생성해주는 것
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
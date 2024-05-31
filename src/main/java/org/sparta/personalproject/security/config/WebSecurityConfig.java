package org.sparta.personalproject.security.config;

import org.sparta.personalproject.service.RefreshTokenService;
import org.sparta.personalproject.security.entity.UserDetailsServiceImpl;
import org.sparta.personalproject.security.jwt.JwtAuthenticationFilter;
import org.sparta.personalproject.security.jwt.JwtAuthorizationFilter;
import org.sparta.personalproject.security.jwt.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationConfiguration authenticationConfiguration;

    public WebSecurityConfig(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService, AuthenticationConfiguration authenticationConfiguration, RefreshTokenService refreshTokenService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.authenticationConfiguration = authenticationConfiguration;
        this.refreshTokenService = refreshTokenService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
//        filter.setRequiresAuthenticationRequestMatcher(new NegatedRequestMatcher(new AntPathRequestMatcher("/api/user/**"))); // TIL 작성
        return filter;

    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, refreshTokenService, userDetailsService );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers("/api/user/**").permitAll() // '/api/user/'로 시작하는 요청 모두 접근 허가
                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리
        );



//         필터 관리
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class); // 인가
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // 인증

        // 접근 불가 페이지
        http.exceptionHandling((exceptionhandling) ->
                exceptionhandling
                        .accessDeniedPage("/forbidden.html")
        );

        return http.build();
    }
}
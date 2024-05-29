package org.sparta.personalproject.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparta.personalproject.User.UserRoleEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j(topic = "JwtUtil")
public class JwtUtil {

    // JWT 데이터


    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // Header KEY 값
    public static final String AUTHORIZATION_COOKIE = "Refresh";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    // 토큰 만료시간
    private final long ACCESSTOKEN_TIME = 60 * 60 * 1000L; // 60분

    // 토큰 만료시간
    private final long REFRESHTOKEN_TIME = 60 * 60 * 10000L; // 600분

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 로그 설정
    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    @PostConstruct // 딱 한번만 받아오면 되는 값을 사용할때마다 요청을 새로 호출하는 실수를 방지하기 위해 사용
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }
    // JWT 생성

    public String createAccessToken(String username, UserRoleEnum role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) // 사용자 식별자값(ID)
                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                        .setExpiration(new Date(date.getTime() + ACCESSTOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

    public String createRefreshToken() {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setExpiration(new Date(date.getTime() + REFRESHTOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }


    public void addJwtToHeader(String token, HttpServletResponse res) {
        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행


            res.setHeader(AUTHORIZATION_HEADER, token);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
    }


    public void addJwtToCookie(String token, HttpServletResponse res) {
        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

            Cookie cookie = new Cookie(AUTHORIZATION_COOKIE, token); // Name-Value
            cookie.setPath("/");

            // Response 객체에 Cookie 추가
            res.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
    }

    // Cookie에 들어있던 JWT 토큰을 Substring

    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        logger.error("Not Found Token");
        throw new NullPointerException("토큰이 유효하지 않습니다.");
    }

    // JWT 검증

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException | ExpiredJwtException |
                 UnsupportedJwtException | IllegalArgumentException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        }
        return false;

    }
    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException | ExpiredJwtException |
                 UnsupportedJwtException | IllegalArgumentException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        }
        return false;
    }
        // JWT에서 사용자 정보 가져오기

        public Claims getUserInfoFromToken (String token){
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        }

        // HttpServletRequest 에서 Cookie Value : JWT 가져오기
        public String getTokenFromHeader (HttpServletRequest req){

            String header = req.getHeader("Authorization");

            System.out.println(header);
            if (StringUtils.isEmpty(header)) {

                return null;
            }
            try {
                return URLDecoder.decode(header, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }

        public String getTokenFromCookie (HttpServletRequest req){

            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(AUTHORIZATION_COOKIE)) {
                        try {
                            return URLDecoder.decode(cookie.getValue(), "UTF-8"); // Encode 되어 넘어간 Value 다시 Decode
                        } catch (UnsupportedEncodingException e) {
                            return null;
                        }
                    }
                }
            }
            return null;
        }
    }




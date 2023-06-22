package com.hrms.hrpitch.jwt.utils;

import com.hrms.hrpitch.common.dao.Login;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Date;


@Component
@Slf4j
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165627416488L;

    @Value("${jwt.validity}")
    private Integer validity;

    public long JWT_TOKEN_VALIDITY;

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @PostConstruct
    private void setTokenValidityInMillis(){
        JWT_TOKEN_VALIDITY = validity * 60 * 60 * 1000;
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            log.error("JWT expired", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("Token is null, empty or only whitespace", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            log.error("JWT is not supported", ex);
        }

        return false;
    }

//    public String getUsername(String token) {
//        return parseClaims(token).getSubject();
//    }

    public String generateAccessToken(Login user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .claim("roles", user.getRoles().toString())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}

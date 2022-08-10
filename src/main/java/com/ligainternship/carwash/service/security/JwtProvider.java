package com.ligainternship.carwash.service.security;

import com.ligainternship.carwash.dto.AppUserDto;
import com.ligainternship.carwash.exception.AuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {


    private final String accessTokenSecretKey;
    private final String refreshTokenSecretKey;

    @Value("${jwt.expiration-time.access-token}")
    private int accessExpirationTimeInMinutes;
    @Value("${jwt.expiration-time.refresh-token}")
    private int refreshExpirationTimeInDays;

    public JwtProvider(@Value("${jwt.secret-key.access-token}") String accessTokenSecretKey,
                       @Value("${jwt.secret-key.refresh-token}") String refreshTokenSecretKey) {
        this.accessTokenSecretKey = accessTokenSecretKey;
        this.refreshTokenSecretKey = refreshTokenSecretKey;
    }

    public String generateAccessToken(AppUserDto user) {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMinutes(accessExpirationTimeInMinutes);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(Date.from(expiredAt.toInstant(ZoneOffset.ofHours(3))))
                .signWith(generateKey(accessTokenSecretKey.getBytes()))
                .claim("roles", user.getAuthorities())
                .compact();
    }

    public String generateRefreshToken(AppUserDto user) {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusDays(refreshExpirationTimeInDays);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(Date.from(expiredAt.toInstant(ZoneOffset.ofHours(3))))
                .signWith(generateKey(refreshTokenSecretKey.getBytes()))
                .claim("roles", user.getAuthorities())
                .compact();
    }

    public Claims getAccessClaims(String accessToken) {
        if (accessToken == null) {
            String message = "Access token doesn't exist";
            log.error(message);
            throw new AuthenticationException(message);
        }
        return getClaims(accessToken, accessTokenSecretKey);
    }

    public Claims getRefreshClaims(String refreshToken) {
        if (refreshToken == null) {
            String message = "Refresh token doesn't exist";
            log.error(message);
            throw new AuthenticationException(message);
        }
        return getClaims(refreshToken, refreshTokenSecretKey);
    }

    public Claims getClaims(String token, String secret) {
        if (token == null) {
            throw new AuthenticationException("Token doesn't exist");
        }
        return Jwts.parserBuilder()
                .setSigningKey(generateKey(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isAccessTokenValid(String accessToken) {
        if (accessToken == null) {
            String message = "Access token doesn't exist";
            log.error(message);
            throw new AuthenticationException(message);
        }
        return validateToken(accessToken, accessTokenSecretKey);
    }

    public boolean isRefreshTokenValid(String refreshToken) {
        if (refreshToken == null) {
            String message = "Refresh token doesn't exist";
            log.error(message);
            throw new AuthenticationException(message);
        }
        return validateToken(refreshToken, refreshTokenSecretKey);
    }

    public boolean validateToken(String token, String secret) {
        if (token == null) {
            throw new AuthenticationException("Token doesn't exist");
        }
        try {
            Jwts.parserBuilder()
                    .setSigningKey(generateKey(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException exception) {
            log.error("Invalid JWT signature: {}", exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.error("Invalid JWT token: {}", exception.getMessage());
        } catch (ExpiredJwtException exception) {
            log.error("JWT token is expired: {}", exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.error("JWT token is unsupported: {}", exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.error("JWT claims string is empty: {}", exception.getMessage());
        }
        return false;
    }

    private SecretKey generateKey(byte[] bytes) {
        return Keys.hmacShaKeyFor(bytes);
    }
}

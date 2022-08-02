package ru.internship.mvc.service.security.jwt.filter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.internship.mvc.dto.security.AppUserDto;
import ru.internship.mvc.exception.AuthenticationException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret-key}")
    private String tokenSecretKey;
    @Value("${jwt.expiration-time.access-token}")
    private int accessTokenExpirationTime;
    @Value("${jwt.expiration-time.refresh-token}")
    private int refreshTokenExpirationTime;

    public String generateAccessToken(AppUserDto user) {
        return generateToken(user, accessTokenExpirationTime);
    }

    public String generateRefreshToken(AppUserDto user) {
        return generateToken(user, refreshTokenExpirationTime);
    }

    public String generateToken(AppUserDto user, int expirationTime) {
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        Instant expiration = issuedAt.plus(expirationTime, ChronoUnit.MILLIS);
        return Jwts.builder()
                .setSubject(user.getName())
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiration))
                .claim("role", user.getAuthorities())
                .signWith(Keys.hmacShaKeyFor(tokenSecretKey.getBytes()))
                .compact();
    }

    public Claims getClaims(String token) {
        if (token == null) {
            throw new AuthenticationException("Token doesn't exist");
        }
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(tokenSecretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        if (token == null) {
            throw new AuthenticationException("Token doesn't exist");
        }
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(tokenSecretKey.getBytes()))
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
}

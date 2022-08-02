package ru.internship.mvc.service.security.jwt;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.internship.mvc.dto.security.AppUserDto;
import ru.internship.mvc.dto.security.requests.LoginRequest;
import ru.internship.mvc.dto.security.requests.RefreshRequest;
import ru.internship.mvc.dto.security.responses.JwtResponse;
import ru.internship.mvc.exception.AuthenticationException;
import ru.internship.mvc.service.UserService;
import ru.internship.mvc.service.security.jwt.filter.JwtUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final Map<String, String> refreshTokens = new HashMap<>();

    public JwtResponse authUser(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (BadCredentialsException exception) {
            throw new AuthenticationException("Wrong username or password");
        }
        AppUserDto user = userService.loadUserByUsername(loginRequest.getUsername());
        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);
        refreshTokens.put(user.getName(), refreshToken);
        return new JwtResponse(accessToken, refreshToken);
    }

    public JwtResponse refreshToken(RefreshRequest refreshRequest) {
        String refreshToken = refreshRequest.getRefreshToken();
        if (jwtUtils.validateToken(refreshToken)) {
            Claims claims = jwtUtils.getClaims(refreshToken);
            String username = claims.getSubject();
            String refreshTokenFromMap = refreshTokens.get(username);
            if (refreshTokenFromMap != null && refreshTokenFromMap.equals(refreshToken)) {
                AppUserDto user = userService.loadUserByUsername(username);
                String newAccessToken = jwtUtils.generateAccessToken(user);
                return new JwtResponse(newAccessToken, refreshToken);
            }
        }
        throw new AuthenticationException("Invalid refresh token");
    }


}

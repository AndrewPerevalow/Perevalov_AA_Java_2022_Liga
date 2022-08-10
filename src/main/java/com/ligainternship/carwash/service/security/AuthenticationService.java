package com.ligainternship.carwash.service.security;

import com.ligainternship.carwash.dto.AppUserDto;
import com.ligainternship.carwash.dto.request.security.LoginDto;
import com.ligainternship.carwash.dto.request.security.RefreshDto;
import com.ligainternship.carwash.dto.response.security.JwtResponse;
import com.ligainternship.carwash.exception.AuthenticationException;
import com.ligainternship.carwash.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final Map<String, String> refreshTokens = new HashMap<>();

    public JwtResponse login(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getLogin(),
                loginDto.getPassword());
        try {
            authenticationManager.authenticate(token);
        } catch (BadCredentialsException exception) {
            throw new AuthenticationException("Wrong login or password");
        }
        AppUserDto user = userService.loadUserByUsername(loginDto.getLogin());
        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);
        refreshTokens.put(user.getLogin(), refreshToken);
        return new JwtResponse(accessToken, refreshToken);
    }

    public JwtResponse getNewAccessToken(RefreshDto refreshDto) {
        String refreshToken = refreshDto.getRefreshToken();
        if (jwtProvider.isRefreshTokenValid(refreshToken)) {
            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            String login = claims.getSubject();
            String refreshTokenFromMap = refreshTokens.get(login);
            if (refreshTokenFromMap != null && refreshTokenFromMap.equals(refreshToken)) {
                AppUserDto user = userService.loadUserByUsername(login);
                String accessToken = jwtProvider.generateAccessToken(user);
                String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshTokens.put(user.getLogin(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthenticationException("Invalid refresh token");
    }
}

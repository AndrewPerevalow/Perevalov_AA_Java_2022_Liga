package ru.internship.mvc.service.security.jwt.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenValidator extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final static String REQUEST_HEADER = "Authorization";
    private final static String HEADER_START = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = parseToken(request);
            if (token != null && jwtUtils.validateToken(token)) {
                Claims claims = jwtUtils.getClaims(token);
                String username = claims.getSubject();
                Set<SimpleGrantedAuthority> authorities = getAuthorities(claims);
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtException e) {
            throw new MalformedJwtException(String.format("Can not authorize token: %s", request.getHeader(REQUEST_HEADER)));
        }
        filterChain.doFilter(request,response);
    }

    private String parseToken(HttpServletRequest request) {
        String headerAuth = request.getHeader(REQUEST_HEADER);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(HEADER_START)) {
            return headerAuth.substring(7);
        }
        return null;
    }

    private Set<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        var role = (List<Map<String, String>>) claims.get("role");

        return role.stream()
                .map(map -> new SimpleGrantedAuthority(map.get("authority")))
                .collect(Collectors.toSet());

    }
}

package com.ligainternship.carwash.dto.response.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {

    private String accessToken;
    private String refreshToken;

}

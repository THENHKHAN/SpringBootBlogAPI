package com.springboot.blogApp.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse {
    private String access_token;

//    @Value("${app-jwt-expiration-millisecond}")
    private long expires_in;

    private String token_type = "Bearer";
}

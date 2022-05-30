package com.example.finalprojectaozcann.utils;

import com.example.finalprojectaozcann.security.CustomJWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Component
public class JWTDecodeUtil {

    private final CustomJWTAuthenticationFilter customJWTAuthenticationFilter;

    public Long findUserIdFromJwt(HttpServletRequest httpServletRequest) {
        return customJWTAuthenticationFilter
                .findUserId(customJWTAuthenticationFilter.findToken(httpServletRequest));
    }

}

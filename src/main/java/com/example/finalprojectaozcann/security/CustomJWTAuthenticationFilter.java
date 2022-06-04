package com.example.finalprojectaozcann.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.finalprojectaozcann.config.Constants;
import com.example.finalprojectaozcann.config.Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomJWTAuthenticationFilter extends OncePerRequestFilter {

    private final Properties properties;
    private final CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = findToken(request);
            if (StringUtils.hasLength(jwt) && validate(jwt)) {
                Long username = findUsername(jwt);
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username.toString());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    public String findToken(HttpServletRequest request) {
        String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(Constants.Security.BEARER)) {
            return headerAuth.substring(7);
        }
        return Strings.EMPTY;
    }

    public Long findUsername(String token) {
        if (!StringUtils.hasLength(token)) {
            throw new IllegalArgumentException(Constants.SecurityErrorMessage.TOKEN_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        return JWT.decode(token).getClaim(Constants.Security.IDENTITY_NUMBER).asLong();
    }

    public Long findUserId(String token) {
        if (!StringUtils.hasLength(token)) {
            throw new IllegalArgumentException(Constants.SecurityErrorMessage.TOKEN_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        return JWT.decode(token).getClaim(Constants.Security.USER_ID).asLong();
    }


    public boolean validate(String token) {
        try {
            JWT.require(Algorithm.HMAC512(properties.getSecretKey()))
                    .build()
                    .verify(token);
            return true;
        } catch (AlgorithmMismatchException algorithmMismatchException) {
            log.error("JWT Token AlgorithmMismatchException occurred!");
        } catch (SignatureVerificationException signatureVerificationException) {
            log.error("JWT Token SignatureVerificationException occurred!");
        } catch (TokenExpiredException tokenExpiredException) {
            log.error("JWT Token TokenExpiredException occurred!");
        } catch (InvalidClaimException invalidClaimException) {
            log.error("JWT Token InvalidClaimException occurred!");
        }
        return false;
    }
}

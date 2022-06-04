package com.example.finalprojectaozcann.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
@Getter
@Setter
public class Properties {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.expires-in}")
    private long expiresIn;

}

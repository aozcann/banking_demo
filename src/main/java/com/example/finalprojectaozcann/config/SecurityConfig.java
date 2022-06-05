package com.example.finalprojectaozcann.config;

import com.example.finalprojectaozcann.security.CustomJWTAuthenticationEntryPoint;
import com.example.finalprojectaozcann.security.CustomJWTAuthenticationFilter;
import com.example.finalprojectaozcann.security.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService customUserDetailService;
    private final CustomJWTAuthenticationEntryPoint customJWTAuthenticationEntryPoint;
    private final CustomJWTAuthenticationFilter customJWTAuthenticationFilter;

    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService((UserDetailsService) this.customUserDetailService)
                .passwordEncoder(bcryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private static final String[] AUTH_SWAGGER_URLS = {
            "/v2/api-docs",
            "/configuration/**",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars*/**",
            "/swagger*/**",
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling().authenticationEntryPoint(customJWTAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/login").permitAll()
                .antMatchers("/api/auth/logout").permitAll()
                .antMatchers("/api/users/admin").permitAll()
                .antMatchers(AUTH_SWAGGER_URLS).permitAll()
                .anyRequest()
                .authenticated();
        http.addFilterBefore(customJWTAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .requestMatchers((matchers) -> matchers.antMatchers("/swagger-ui.html"))
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll())
                .requestCache().disable()
                .securityContext().disable()
                .sessionManagement().disable();
    }

}

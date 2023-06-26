package com.test.test.configuration;

import com.test.test.repository.WebuserRepository;
import com.test.test.service.LogoutService;
import com.test.test.util.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    LogoutService logoutService;

    private final String[] AUTH_WHITELIST = {
            "/auth/register/user",
            "auth/register/user",
            "/auth/register/admin",
            "auth/register/admin",
            "/auth/**",
            "auth/**",
            "/auth/authenticate",
            "auth/authenticate",
            "/swagger-ui/**",
            "swagger-ui/**",
            "/sw/**",
            "sw/**",
            "/sc/**",
            "sc/**",
            "/v3/api-docs/**",
            "v3/api-docs/**"
    };

    private final String[] AUTH_BLACKLIST = {
            "/employee",
            "employee",
            "/company",
            "company"
    };



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth -> auth.requestMatchers(AUTH_WHITELIST).permitAll()
                                                    .requestMatchers(HttpMethod.POST, AUTH_BLACKLIST).hasAuthority("ADMIN")
                                                    .anyRequest().authenticated()))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout.addLogoutHandler(logoutService));
        return http.build();
    }

}

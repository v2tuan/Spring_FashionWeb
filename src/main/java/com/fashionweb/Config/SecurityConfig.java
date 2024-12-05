package com.fashionweb.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINTS = {"/account",
            "/auth/token", "/auth/introspect"
    };

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, "/home").permitAll()
                        .requestMatchers(HttpMethod.GET, "/home/user-login", "/home/user-register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/home/shop").permitAll()
                        .requestMatchers(HttpMethod.POST, "/account").permitAll()
                        .requestMatchers(HttpMethod.POST, "/account/signup", "/account/login","/account/verify").permitAll()
                        .requestMatchers("/assets/**", "/cdn.jsdelivr.net/**", "/cdnjs.cloudflare.com/**").permitAll() // Cho phép truy cập tài nguyên tĩnh
                .anyRequest().permitAll());

        httpSecurity.oauth2ResourceServer(oauth2 ->
             oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
        );

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // Cấu hình form login
        httpSecurity.formLogin(form -> form.loginPage("/home/user-login")
                .defaultSuccessUrl("/home", true)  // Khi đăng nhập thành công sẽ chuyển hướng đến trang chủ
                .failureUrl("/home/user-login?error=true")  // Khi đăng nhập thất bại
                .permitAll());

        return httpSecurity.build();
    }

    @Bean
    JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }
}

package com.fashionweb.Config;

import com.fashionweb.Enum.Role;
import com.fashionweb.filter.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableWebMvc
public class WebSecurityConfig {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests(requests -> {
                    requests
                            .requestMatchers("/assets/**", "/cdn.jsdelivr.net/**", "/cdnjs.cloudflare.com/**").permitAll()
                            .requestMatchers("/register", "/login/**").permitAll()
                            .requestMatchers("/api/**").permitAll() // Cho phép tất cả truy cập vào các API
                            .requestMatchers("/home/**").permitAll()
                            .requestMatchers("/home/**").permitAll()
                            .requestMatchers("/forgotpassword/**").permitAll()
                            .requestMatchers("/vnpay-payment").permitAll()
                            .requestMatchers("/error/**").permitAll()
                            .requestMatchers("/admin/discountlist").hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name())
                            .requestMatchers("/admin/adddiscount/**").hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name())
                            .requestMatchers("/admin/editdiscount/**").hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name())
                            .requestMatchers("/admin/updatediscount/**").hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name())
                            .requestMatchers("/admin/deletediscount/**").hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name())
                            .requestMatchers("/account/cart-items/add").hasAnyRole(Role.USER.name(), Role.ADMIN.name(), Role.MANAGER.name())
                            .requestMatchers("/admin/order-detail/**").hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name())
                            .requestMatchers("/admin/updateorder/**").hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name())
                            .requestMatchers("/admin/dashboard").hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name())
                            .requestMatchers("/admin/**").hasAnyRole(Role.ADMIN.name())
                            .requestMatchers("/manager/**").hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name())
                            .requestMatchers("/account/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name(), Role.MANAGER.name())
                            .requestMatchers("/address/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name(), Role.MANAGER.name())
                            .requestMatchers("/user/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name(), Role.MANAGER.name())
                            .anyRequest().hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name());
                });
        return http.build();
    }
}
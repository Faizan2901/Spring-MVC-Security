package com.codemind.springboot.DemoSecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoSecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){

        UserDetails abdul= User.builder()
                .username("abdul")
                .password("{noop}abdul@123")
                .roles("EMPLOYEE")
                .build();
        UserDetails sakib= User.builder()
                .username("sakib")
                .password("{noop}sakib@123")
                .roles("EMPLOYEE","MANAGER")
                .build();
        UserDetails faizan= User.builder()
                .username("faizan")
                .password("{noop}faizan@123")
                .roles("EMPLOYEE","MANAGER","ADMIN")
                .build();

        return new InMemoryUserDetailsManager(abdul,sakib,faizan);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(configurer->
                configurer
                        .anyRequest().authenticated()
        )
        .formLogin(form->
                form
                        .loginPage("/showLoginPage")
                        .loginProcessingUrl("/authenticateTheUser")
                        .permitAll()
        );

        return http.build();
    }
}

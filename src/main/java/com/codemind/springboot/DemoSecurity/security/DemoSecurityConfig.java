package com.codemind.springboot.DemoSecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

    /*@Bean
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
    }*/

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager theJdbcUserDetailsManager=new JdbcUserDetailsManager(dataSource);

        theJdbcUserDetailsManager.setUsersByUsernameQuery("SELECT user_id,pw,active FROM members WHERE user_id=?");
        theJdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT user_id,role FROM roles WHERE user_id=?");

        return theJdbcUserDetailsManager;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(configurer->
                configurer
                        .requestMatchers("/").hasRole("EMPLOYEE")
                        .requestMatchers("/leaders/**").hasRole("MANAGER")
                        .requestMatchers("/systems/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
        )
        .formLogin(form->
                form
                        .loginPage("/showLoginPage")
                        .loginProcessingUrl("/authenticateTheUser")
                        .permitAll()

        )
        .logout(logout ->
                        logout.permitAll()

        )
        .exceptionHandling(configurer->
                configurer.accessDeniedPage("/access-denied")
        );

        return http.build();
    }
}

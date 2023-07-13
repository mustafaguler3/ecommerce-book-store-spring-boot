package com.example.demo.config;


import com.example.demo.service.UserSecurityService;
import com.example.demo.utilities.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserSecurityService userSecurityService;

    private static final String[] PUBLIC_MATCHERS = {
            "/css/**",
            "/js/**",
            "/image/**",
            "/",
            "/login",
            "/newUser",
            "/forgetPassword",
            "/bookDetail",
            "/fonts/**",
            "/bookshelf"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated();

        http.csrf().disable().cors().disable()
                .formLogin().failureUrl("/login?error").defaultSuccessUrl("/")
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/?logout")
                .deleteCookies("remember-me").permitAll()
                .and()
                .rememberMe();
    }



    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService).passwordEncoder(SecurityUtility.passwordEncoder());
    }
}
























package com.konovalov.foto.config;

import com.konovalov.foto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

//куда какой доступ по страницам
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .authorizeRequests()   // без авторизации нечего нельзя делать
                    .antMatchers("/", "/registration").permitAll() // перейти на эту страницу может только пользователи групп(все)
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                .and()
                    .logout()
                    .permitAll();
    }

//ходить по бд  табличка юзер и их роли
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService) //подключаем dataSource для  ходьбы по бд
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
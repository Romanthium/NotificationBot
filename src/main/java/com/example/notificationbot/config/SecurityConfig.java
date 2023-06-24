package com.example.notificationbot.config;

import com.example.notificationbot.model.UserRole;
import com.example.notificationbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        httpSecurity
//                .csrf()
//                .disable()
                .authenticationManager(authenticationManager)
                .authorizeHttpRequests()
                .requestMatchers("/users/activate/**").permitAll()
                                .requestMatchers("/users/new").permitAll()
                                .requestMatchers("/users").permitAll()   //.hasRole(UserRole.ADMIN.name())
                                .requestMatchers("/users/login/**").permitAll()

                                .requestMatchers("/tasks").authenticated()
                                .requestMatchers("/tasks/**").authenticated()
                .anyRequest().authenticated()
                .and()
                    .formLogin().loginPage("/users/login")
                    .loginProcessingUrl("/process_login")
                    .defaultSuccessUrl("/users/success", true)
                    .failureUrl("/users/login?error")
                .and()
                    .logout().logoutUrl("/logout")
                    .logoutSuccessUrl("/users/login")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID");

        return httpSecurity.build();
    }

//        httpSecurity// //csrf().disable()
//                .authorizeHttpRequests((authorize) ->
//                        authorize.requestMatchers("/users/activate/**").permitAll()
//                                .requestMatchers("/users/new").permitAll()
//                                .requestMatchers("/users").hasRole(UserRole.ADMIN.name())
//
//                                //.requestMatchers("/users/login/**").permitAll()
//                                .requestMatchers("/tasks").authenticated()
//                                .requestMatchers("/tasks/**").authenticated()
//
//                ).formLogin(
//                        form -> form
//                                .loginPage("/users/login").permitAll()
//                                .loginProcessingUrl("/tasks")
//                                .defaultSuccessUrl("/tasks", true)
//                                .failureUrl("/users/login?error")
//                                .permitAll()
//                ).logout(
//                        logout -> logout
//                                .logoutUrl("/logout")
//                                .logoutSuccessUrl("/users/login")
////                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
////                                .invalidateHttpSession(true)
////                                .clearAuthentication(true)
////                                .deleteCookies("JSESSIONID")
////
//                );
//        return httpSecurity.build();
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userService
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

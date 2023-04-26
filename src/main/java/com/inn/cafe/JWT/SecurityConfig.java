package com.inn.cafe.JWT;

import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomerUsersDetailsService customerUsersDetailsService;

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests((authz) -> {
//                try {
//                    authz
//                        .shouldFilterAllDispatcherTypes(true)
//                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
//    //                                        .requestMatchers("/").hasAnyRole("EMPLOYEE", "HR", "MANAGER")
//    //                                        .requestMatchers("/hr_info").hasRole("HR")
//    //                                        .requestMatchers("/manager_info/**").hasRole("MANAGER")
//    //                                        .and().formLogin();
//                        .requestMatchers("/user/login", "/user/signup", "/user/forgotPassword")
////                        .anyRequest()
//                        .authenticated()
//                        .and().exceptionHandling()
//                        .and().sessionManagement()
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//                    }
//                );
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .csrf().disable()
                .authorizeHttpRequests((authz) -> {
                    try {
                        authz
                            .shouldFilterAllDispatcherTypes(true)
                            .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
//                            .requestMatchers("/").hasAnyRole("EMPLOYEE", "HR", "MANAGER")
//                            .requestMatchers("/hr_info").hasRole("HR")
//                            .requestMatchers("/manager_info/**").hasRole("MANAGER")
//                            .and().formLogin();
                            .requestMatchers("/user/login", "/user/signup", "/user/forgotPassword")
                            .permitAll()
                            .anyRequest()
                            .authenticated()
                            .and().exceptionHandling()
                            .and().sessionManagement()
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public UserDetailsManager authenticateUsers() {
//        return new JdbcUserDetailsManager(customerUsersDetailsService);
//    }
}

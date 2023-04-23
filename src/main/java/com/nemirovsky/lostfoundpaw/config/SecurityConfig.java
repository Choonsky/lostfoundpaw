package com.nemirovsky.lostfoundpaw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    // Test user added
    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User
                .withUsername("user1")
                .password(passwordEncoder().encode("password1"))
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }

    /*
        @Bean
        public ServerLogoutSuccessHandler logoutSuccessHandler(){
            RedirectServerLogoutSuccessHandler handler = new RedirectServerLogoutSuccessHandler();
            handler.setLogoutSuccessUrl(URI.create("/main"));
            return handler;
        }
    */
    @Bean
    SecurityWebFilterChain http(ServerHttpSecurity http) throws Exception {

        // main ServerHttpSecurity config
        http
                .csrf().disable()
                .authorizeExchange()
//                .anyExchange().authenticated()
                .anyExchange().permitAll()
                .and()
                .httpBasic().and()
                .formLogin();
//                .and()
//                .logout().logoutSuccessHandler(logoutSuccessHandler());
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

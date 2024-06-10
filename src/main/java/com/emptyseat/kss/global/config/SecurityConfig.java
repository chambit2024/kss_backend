//package com.emptyseat.kss.global.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private static final String[] PERMIT_URLS = {
//            /* apis */
//            "/api/**"
//    };
//
////    private final JwtTokenProvider jwtTokenProvider;
//
//    @Bean
//    public DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(Customizer.withDefaults())
//                .csrf(csrf -> csrf.disable())
//                .httpBasic(httpBasic -> httpBasic.disable())
//                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(authorize -> authorize.requestMatchers(PERMIT_URLS).permitAll().anyRequest().authenticated());
////                .exceptionHandling(exceptionConfig -> exceptionConfig.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
////                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
////                .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);
//        return http.build();
//    }
//}

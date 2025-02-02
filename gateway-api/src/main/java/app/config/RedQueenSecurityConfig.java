//package app.config;
//
//
//import app.security.HandlerAccessDenied;
//import app.security.JwtGrantedAuthoritiesConverterFlux;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
//
//
//@Order(5)
//@Configuration
//@EnableWebFluxSecurity
//@EnableMethodSecurity(
//        securedEnabled = true,
//        jsr250Enabled = true
//)
//public class RedQueenSecurityConfig
//{
//    @Bean
//    SecurityWebFilterChain securityRedQueenFilterChain(ServerHttpSecurity http)
//    {
//        return http
//                .securityMatcher(
//                        new PathPatternParserServerWebExchangeMatcher("/red_queen/**")
//                )
//                .authorizeExchange(authz -> authz
//                        .pathMatchers("/*/*/delete/**").hasRole("ADMIN")
//                        .pathMatchers("/*/*/add/**").hasRole("ADMIN")
//                        .pathMatchers("/*/*/create/**").hasRole("ADMIN")
//                        .pathMatchers("/*/*/edit/**").hasRole("ADMIN")
//                        .pathMatchers("*/scheduler-bot/**").hasRole("ADMIN")
//                        .pathMatchers("/*/*/scheduler-bot/**").hasRole("ADMIN")
//                        .pathMatchers("/*/scripts/**", "/*/images/**").permitAll()
//                        .anyExchange().authenticated()
//                )
//                .cors(ServerHttpSecurity.CorsSpec::disable)
//                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt
//                                .jwtAuthenticationConverter(
//                                        new JwtGrantedAuthoritiesConverterFlux("red-queen"))
//                        )
//                )
//                .build();
//    }
//}
//

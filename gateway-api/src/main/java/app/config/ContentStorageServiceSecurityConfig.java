//package app.config;
//
//
//
//import app.security.JwtGrantedAuthoritiesConverterFlux;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
//
//
//@Order(2)
//@Configuration
//@EnableWebFluxSecurity
//@EnableMethodSecurity(
//        securedEnabled = true,
//        jsr250Enabled = true
//)
//public class ContentStorageServiceSecurityConfig
//{
//
//    @Bean
//    SecurityWebFilterChain securityContentStorageServiceFilterChain(ServerHttpSecurity http)
//    {
//        return http
//                .securityMatcher(
//                        new PathPatternParserServerWebExchangeMatcher("/content_storage_service/**")
//                )
//                .authorizeExchange(authz -> authz
//                        .anyExchange().hasRole("ADMIN")
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt
//                                .jwtAuthenticationConverter(
//                                        new JwtGrantedAuthoritiesConverterFlux("content-storage-service"))
//                        )
//                )
//                .build();
//    }
//}
//

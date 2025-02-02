//package app.config;
//
//
//import app.security.JwtGrantedAuthoritiesConverterFlux;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
//
//@Order(3)
//@Configuration
//@EnableWebFluxSecurity
//@EnableMethodSecurity(
//        securedEnabled = true,
//        jsr250Enabled = true
//)
//public class ImgGeneratorSecurityConfig
//{
//    @Bean
//    SecurityWebFilterChain securityImgGeneratorFilterChain(ServerHttpSecurity http)
//    {
//        return http
//                .securityMatcher(
//                        new PathPatternParserServerWebExchangeMatcher("/img_generator/**")
//                )
//                .authorizeExchange(authz -> authz
//                        .pathMatchers("/*/scripts/**", "/*/images/**").permitAll()
//                        .anyExchange().hasRole("ADMIN")
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt
//                                .jwtAuthenticationConverter(
//                                        new JwtGrantedAuthoritiesConverterFlux("img-generator"))
//                        )
//                )
//                .build();
//    }
//}

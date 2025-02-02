//package app.security;
//
//import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.jwt.Jwt;
//import reactor.core.publisher.Mono;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//public class JwtGrantedAuthoritiesConverterFlux implements Converter<Jwt, Mono<AbstractAuthenticationToken>>
//{
//    private final String resource;
//
//    public JwtGrantedAuthoritiesConverterFlux(String resource) {
//        this.resource = resource;
//    }
//
//    @Override
//    public Mono<AbstractAuthenticationToken> convert(Jwt jwt)
//    {
//        return Mono.defer(() ->
//        {
//            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
//            String username =  jwt.getClaim("preferred_username");
//            Object client = resourceAccess.get(resource);
//            LinkedTreeMap<String, List<String>> clientRoleMap = (LinkedTreeMap<String, List<String>>) client;
//            List<String> clientRoles = new ArrayList<>(clientRoleMap.get("roles"));
//            List<GrantedAuthority> authorities = clientRoles.stream()
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(Collectors.toList());
//
//            return Mono.just(new UsernamePasswordAuthenticationToken(
//                    jwt.getSubject(),
//                    username,
//                    authorities)
//            );
//        });
//    }
//}
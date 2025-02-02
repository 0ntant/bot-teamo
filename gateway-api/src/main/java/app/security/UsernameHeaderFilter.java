//package app.security;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//public class UsernameHeaderFilter implements GlobalFilter
//{
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
//    {
//        return ReactiveSecurityContextHolder.getContext()
//                .map(securityContext -> securityContext.getAuthentication())
//                .filter(authentication -> authentication != null)
//                .map(authentication -> {
//                    exchange.getRequest().mutate().header("X-User-Name",String.valueOf(authentication.getCredentials())).build();
//                    return exchange;
//                })
//                .defaultIfEmpty(exchange)
//                .flatMap(chain::filter);
//    }
//}
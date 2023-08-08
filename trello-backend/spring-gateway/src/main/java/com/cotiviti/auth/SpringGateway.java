package com.cotiviti.auth;

import filters.AuthenticationFilter;
import filters.RouterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class SpringGateway {

    public static void main(String[] args) {
        SpringApplication.run(SpringGateway.class, args);
    }

    @Bean
    public AuthenticationFilter authenticationFilter(){
        return new AuthenticationFilter(webClientBuilder());
    }


    @Bean
    public RouterValidator routerValidator(){
        return new RouterValidator();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("socket-service", r -> r
                        .path("/socket/**")
                        .filters(f->f.stripPrefix(1))
                        .uri("lb://socket-service")
                ).route("board-service", r -> r
                        .path("/board/**")
                        .filters(f->f.stripPrefix(1).filter(authenticationFilter()))
                        .uri("lb://board-service")
                ).route("list-service", r -> r
                        .path("/list/**")
                        .filters(f->f.stripPrefix(1).filter(authenticationFilter()))
                        .uri("lb://list-service")
                ).route("auth-service", r -> r
                        .path("/auth/**")
                        .filters(f->f.stripPrefix(1))
                        .uri("lb://auth-service")
                )
                .build();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

package org.ejavaexample.cartservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import feign.RequestInterceptor;

//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ShoppingCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartApplication.class, args);
	}

	@Bean
    public RequestInterceptor requestTokenBearerInterceptor() {
        return requestTemplate -> {
            JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext()
                    .getAuthentication();

            requestTemplate.header("Authorization", "Bearer " + token.getToken().getTokenValue());
        };
    }

//    @Bean
//    public ExecutorService traceableExecutorService() {
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        return new TraceableExecutorService(beanFactory, executorService);
//    }
}

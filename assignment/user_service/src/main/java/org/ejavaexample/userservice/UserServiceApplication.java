package org.ejavaexample.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import org.ejavaexample.userservice.repository.UserEntity;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Component
	class ExposeEntityIdRestMvcConfiguration implements RepositoryRestConfigurer {

		@Override
		public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
			config.exposeIdsFor(UserEntity.class);
		}

	}

}

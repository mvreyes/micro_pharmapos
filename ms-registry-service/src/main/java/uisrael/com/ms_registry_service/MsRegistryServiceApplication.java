package uisrael.com.ms_registry_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MsRegistryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRegistryServiceApplication.class, args);
	}

}

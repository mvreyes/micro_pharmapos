package uisrael.com.ms_auth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAuthServiceApplication.class, args);
	}

}

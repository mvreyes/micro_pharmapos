package uisrael.com.ms_agencia_usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsAgenciaUsuarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAgenciaUsuarioApplication.class, args);
	}

}

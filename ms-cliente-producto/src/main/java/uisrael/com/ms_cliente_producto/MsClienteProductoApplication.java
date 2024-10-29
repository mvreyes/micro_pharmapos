package uisrael.com.ms_cliente_producto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsClienteProductoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsClienteProductoApplication.class, args);
	}

}

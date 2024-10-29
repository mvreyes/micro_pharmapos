package uisrael.com.ms_inventario_factura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsInventarioFacturaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsInventarioFacturaApplication.class, args);
	}

}

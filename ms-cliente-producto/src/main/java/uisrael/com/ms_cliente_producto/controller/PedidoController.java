package uisrael.com.ms_cliente_producto.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uisrael.com.ms_cliente_producto.entity.Pedidos;
import uisrael.com.ms_cliente_producto.service.PedidoService;

@RestController
@RequestMapping("/api/v1")
public class PedidoController {
	@Autowired
	private PedidoService pedidosService;
	
	 @CrossOrigin
	 @GetMapping("/pedidos")
	 public Map<String, List<Pedidos>> listaPedidos(){
		 return Collections.singletonMap("pedidos", pedidosService.listaPedidos());
	 }
}

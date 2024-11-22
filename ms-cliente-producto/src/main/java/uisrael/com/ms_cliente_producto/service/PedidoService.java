package uisrael.com.ms_cliente_producto.service;

import java.util.List;

import uisrael.com.ms_cliente_producto.entity.Pedidos;

public interface PedidoService {
	List<Pedidos> listaPedidos();
	Pedidos crearPedidos(Pedidos pedidos);
	Pedidos actualizarPedidos(Pedidos pedidos);	
}
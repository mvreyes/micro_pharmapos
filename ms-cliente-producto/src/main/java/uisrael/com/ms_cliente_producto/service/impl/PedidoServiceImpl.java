package uisrael.com.ms_cliente_producto.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uisrael.com.ms_cliente_producto.entity.Pedidos;
import uisrael.com.ms_cliente_producto.repository.PedidoRepository;
import uisrael.com.ms_cliente_producto.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {
	@Autowired
	private PedidoRepository pedidoRepository;
	
	 @Override
	    public List<Pedidos> listaPedidos(){
	        return pedidoRepository.findAll(Sort.by(Sort.Direction.DESC, "idPedido"));
	    }
	
	@Override
    @Transactional
    public Pedidos crearPedidos(Pedidos pedidos) {
        return pedidoRepository.save(pedidos);
    }
	
	 @Override
	 @Transactional
	 public Pedidos actualizarPedidos(Pedidos pedidos) {
		 return pedidoRepository.save(pedidos);
	 }
}

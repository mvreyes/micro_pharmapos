package uisrael.com.ms_inventario_factura.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import uisrael.com.ms_inventario_factura.entity.CabeceraPedido;
import uisrael.com.ms_inventario_factura.entity.DetallePedido;
import uisrael.com.ms_inventario_factura.model.Agencia;
import uisrael.com.ms_inventario_factura.model.Cliente;
import uisrael.com.ms_inventario_factura.model.Producto;
import uisrael.com.ms_inventario_factura.repository.DetallePedidoRepository;
import uisrael.com.ms_inventario_factura.service.DetallePedidoService;

import java.util.List;
import java.util.UUID;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<DetallePedido> listaDetallesPedidos(){
        return detallePedidoRepository.findAll(Sort.by(Sort.Direction.DESC, "idDetallePedido"));
    }

    @Override
    public List<DetallePedido> listaDetallesPedido(String idCabeceraPedido) {
        List<DetallePedido> detallesPedidos = detallePedidoRepository.findDetallesPedidosByIdCabeceraPedido(idCabeceraPedido);
        for (DetallePedido detallePedido : detallesPedidos) {
            Producto producto = restTemplate.getForObject("http://localhost:8002/productos/"+detallePedido.getIdProducto(), Producto.class);
            detallePedido.setProducto(producto);
        }
        return detallesPedidos;
    }

    @Override
    @Transactional
    public DetallePedido crearDetallePedido(DetallePedido detallePedido) {
        String randomDetallePedido = UUID.randomUUID().toString();
        detallePedido.setIdDetallePedido(randomDetallePedido);
        return detallePedidoRepository.save(detallePedido);
    }
}

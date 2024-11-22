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
import uisrael.com.ms_inventario_factura.repository.CabeceraPedidoRepository;
import uisrael.com.ms_inventario_factura.repository.DetallePedidoRepository;
import uisrael.com.ms_inventario_factura.service.CabeceraPedidoService;

import java.util.*;
import java.security.SecureRandom;

@Service
public class CabeceraPedidoServiceImpl implements CabeceraPedidoService {
    @Autowired
    private CabeceraPedidoRepository cabeceraPedidoRepository;
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<CabeceraPedido> listaCabecerasPedidos(){
        List<CabeceraPedido> cabecerasPedidos = cabeceraPedidoRepository.findAll(Sort.by(Sort.Direction.DESC, "numeroPedido"));
        for (CabeceraPedido cabeceraPedido : cabecerasPedidos) {
            Cliente cliente = restTemplate.getForObject("http://localhost:8002/clientes/"+cabeceraPedido.getIdCliente(), Cliente.class);
            cabeceraPedido.setCliente(cliente);
            Agencia agencia = restTemplate.getForObject("http://localhost:8001/agencias/"+cabeceraPedido.getIdAgencia(), Agencia.class);
            cabeceraPedido.setAgencia(agencia);
        }
        return cabecerasPedidos;
    }

    @Override
    public CabeceraPedido obtenerPorId(String idCabeceraPedido) {
        CabeceraPedido cabeceraPedido = cabeceraPedidoRepository.findById(idCabeceraPedido).orElse(null);
        if (cabeceraPedido != null){
            Cliente cliente = restTemplate.getForObject("http://localhost:8002/clientes/"+cabeceraPedido.getIdCliente(), Cliente.class);
            cabeceraPedido.setCliente(cliente);
            Agencia agencia = restTemplate.getForObject("http://localhost:8001/agencias/"+cabeceraPedido.getIdAgencia(), Agencia.class);
            cabeceraPedido.setAgencia(agencia);
            List<DetallePedido> detallesPedidos = detallePedidoRepository.findDetallesPedidosByIdCabeceraPedido(idCabeceraPedido);
            for (DetallePedido detallePedido : detallesPedidos) {
                Producto producto = restTemplate.getForObject("http://localhost:8002/productos/"+detallePedido.getIdProducto(), Producto.class);
                detallePedido.setProducto(producto);
            }
            cabeceraPedido.setDetallesPedidos(detallesPedidos);
        }
        return cabeceraPedido;
    }

    @Override
    @Transactional
    public CabeceraPedido crearCabeceraPedido(CabeceraPedido cabeceraPedido) {
        String randomCabeceraPedido = generarRandom();
        cabeceraPedido.setIdCabeceraPedido(randomCabeceraPedido);
        return cabeceraPedidoRepository.save(cabeceraPedido);
    }

    @Override
    public Long contarPedidos() {
        return cabeceraPedidoRepository.count();
    }

    public String generarRandom() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(15);

        for (int i = 0; i < 15; i++) {
            int index = random.nextInt(caracteres.length());
            sb.append(caracteres.charAt(index));
        }

        return sb.toString();
    }
}

package uisrael.com.ms_inventario_factura.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import uisrael.com.ms_inventario_factura.entity.DetalleFactura;
import uisrael.com.ms_inventario_factura.entity.DetalleInventario;
import uisrael.com.ms_inventario_factura.model.Cliente;
import uisrael.com.ms_inventario_factura.model.Producto;
import uisrael.com.ms_inventario_factura.model.StockProducto;
import uisrael.com.ms_inventario_factura.repository.DetalleInventarioRepository;
import uisrael.com.ms_inventario_factura.service.DetalleInventarioService;

import java.util.List;
import java.util.UUID;

@Service
public class DetalleInventarioServiceImpl implements DetalleInventarioService {
    @Autowired
    private DetalleInventarioRepository detalleInventarioRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<DetalleInventario> listaDetallesInventarios(){
        return detalleInventarioRepository.findAll(Sort.by(Sort.Direction.DESC, "idDetalleInventario"));
    }

    @Override
    public List<DetalleInventario> listaDetallesInventario(String idCabeceraInventario) {
        List<DetalleInventario> detallesInventarios = detalleInventarioRepository.findDetallesInventariosByIdCabeceraInventario(idCabeceraInventario);
        for (DetalleInventario detalleInventario : detallesInventarios) {
            Producto producto = restTemplate.getForObject("http://localhost:8002/productos/"+detalleInventario.getIdProducto(), Producto.class);
            detalleInventario.setProducto(producto);
        }
        return detallesInventarios;
    }

    @Override
    @Transactional
    public DetalleInventario crearDetalleInventario(DetalleInventario detalleInventario) {
        String randomDetalleInventario = UUID.randomUUID().toString();
        detalleInventario.setIdDetalleInventario(randomDetalleInventario);
        return detalleInventarioRepository.save(detalleInventario);
    }
}

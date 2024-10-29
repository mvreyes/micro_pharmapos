package uisrael.com.ms_inventario_factura.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import uisrael.com.ms_inventario_factura.entity.CabeceraFactura;
import uisrael.com.ms_inventario_factura.entity.DetalleFactura;
import uisrael.com.ms_inventario_factura.model.Agencia;
import uisrael.com.ms_inventario_factura.model.Cliente;
import uisrael.com.ms_inventario_factura.model.Producto;
import uisrael.com.ms_inventario_factura.repository.DetalleFacturaRepository;
import uisrael.com.ms_inventario_factura.service.DetalleFacturaService;

import java.util.List;
import java.util.UUID;

@Service
public class DetalleFacturaServiceImpl implements DetalleFacturaService {
    @Autowired
    private DetalleFacturaRepository detalleFacturaRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<DetalleFactura> listaDetallesFacturas(){
        return detalleFacturaRepository.findAll(Sort.by(Sort.Direction.DESC, "idDetalleFactura"));
    }

    @Override
    public List<DetalleFactura> listaDetallesFactura(String idCabeceraFactura) {
        List<DetalleFactura> detallesFacturas = detalleFacturaRepository.findDetallesFacturasByIdCabeceraFactura(idCabeceraFactura);
        for (DetalleFactura detalleFactura : detallesFacturas) {
            Producto producto = restTemplate.getForObject("http://localhost:8002/productos/"+detalleFactura.getIdProducto(), Producto.class);
            detalleFactura.setProducto(producto);
        }
        return detallesFacturas;
    }

    @Override
    @Transactional
    public DetalleFactura crearDetalleFactura(DetalleFactura detalleFactura) {
        String randomDetalleFactura = UUID.randomUUID().toString();
        detalleFactura.setIdDetalleFactura(randomDetalleFactura);
        return detalleFacturaRepository.save(detalleFactura);
    }
}

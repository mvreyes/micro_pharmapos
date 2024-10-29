package uisrael.com.ms_cliente_producto.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uisrael.com.ms_cliente_producto.entity.Producto;
import uisrael.com.ms_cliente_producto.repository.ProductoRepository;
import uisrael.com.ms_cliente_producto.service.ProductoService;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> listaProductos(){
        return productoRepository.findAll(Sort.by(Sort.Direction.DESC, "idProducto"));
    }

    @Override
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public Producto actualizarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public Producto estadoProducto(Long id) {
        Producto productodb = productoRepository.findById(id).orElse(null);
        if(productodb != null) {
            if(productodb.getEstado() == 1) {
                productodb.setEstado(0);
            }else {
                productodb.setEstado(1);
            }
            return productoRepository.save(productodb);
        }
        return null;
    }

    @Override
    public List<Producto> findByCodigoPrincipalOrNombre(String term) {
        return productoRepository.findByCodigoPrincipalOrNombre(term);
    }
}

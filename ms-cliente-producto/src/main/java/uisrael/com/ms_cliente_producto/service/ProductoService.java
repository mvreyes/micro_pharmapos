package uisrael.com.ms_cliente_producto.service;

import uisrael.com.ms_cliente_producto.entity.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> listaProductos();
    Producto obtenerPorId(Long id);
    Producto crearProducto(Producto producto);
    Producto actualizarProducto(Producto producto);
    Producto estadoProducto(Long id);
    List<Producto> findByCodigoPrincipalOrNombre(String term);
}

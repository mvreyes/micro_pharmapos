package uisrael.com.ms_cliente_producto.service;

import uisrael.com.ms_cliente_producto.entity.StockProducto;

public interface StockProductoService {
    StockProducto findByIdProductoAndIdAgencia(Long idProducto, Long idAgencia);
    StockProducto generarStockProducto(Long idProducto, Long idAgencia, int cantidad, String tipo);
}

package uisrael.com.ms_cliente_producto.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uisrael.com.ms_cliente_producto.entity.StockProducto;
import uisrael.com.ms_cliente_producto.repository.StockProductoRepository;
import uisrael.com.ms_cliente_producto.service.StockProductoService;

@Service
public class StockProductoServiceImpl implements StockProductoService {
    @Autowired
    private StockProductoRepository stockProductoRepository;

    @Override
    public StockProducto findByIdProductoAndIdAgencia(Long idProducto, Long idAgencia) {
        return stockProductoRepository.findByIdProductoAndIdAgencia(idProducto, idAgencia);
    }

    @Override
    @Transactional
    public StockProducto generarStockProducto(Long idProducto, Long idAgencia, int cantidad, String tipo) {
        StockProducto stockProductodb = stockProductoRepository.findByIdProductoAndIdAgencia(idProducto, idAgencia);
        if(stockProductodb != null) {
            int nuevaCantidad = 0;
            if(tipo.equals("I")){
                nuevaCantidad = stockProductodb.getCantidad() + cantidad;
            }else{
                nuevaCantidad = stockProductodb.getCantidad() - cantidad;
            }
            stockProductodb.setCantidad(nuevaCantidad);
            return stockProductoRepository.save(stockProductodb);
        }else{
            StockProducto dbstockProducto = new StockProducto();
            dbstockProducto.setIdAgencia(idAgencia);
            dbstockProducto.setIdProducto(idProducto);
            dbstockProducto.setCantidad(cantidad);
            return stockProductoRepository.save(dbstockProducto);
        }
    }
}

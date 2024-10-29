package uisrael.com.ms_cliente_producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uisrael.com.ms_cliente_producto.entity.StockProducto;

@Repository
public interface StockProductoRepository extends JpaRepository<StockProducto, Long> {
    StockProducto findByIdProductoAndIdAgencia(Long idProducto, Long idAgencia);
}

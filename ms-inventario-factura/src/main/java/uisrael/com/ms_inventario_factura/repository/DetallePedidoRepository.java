package uisrael.com.ms_inventario_factura.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uisrael.com.ms_inventario_factura.entity.DetallePedido;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends MongoRepository<DetallePedido, String> {
    List<DetallePedido> findDetallesPedidosByIdCabeceraPedido(String idCabeceraPedido);
}

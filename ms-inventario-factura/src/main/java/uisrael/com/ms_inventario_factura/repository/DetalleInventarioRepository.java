package uisrael.com.ms_inventario_factura.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uisrael.com.ms_inventario_factura.entity.DetalleInventario;

import java.util.List;

@Repository
public interface DetalleInventarioRepository extends MongoRepository<DetalleInventario, String> {
    List<DetalleInventario> findDetallesInventariosByIdCabeceraInventario(String idCabeceraInventario);
}

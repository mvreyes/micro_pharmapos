package uisrael.com.ms_inventario_factura.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uisrael.com.ms_inventario_factura.entity.CabeceraInventario;

@Repository
public interface CabeceraInventarioRepository extends MongoRepository<CabeceraInventario, String> {
}

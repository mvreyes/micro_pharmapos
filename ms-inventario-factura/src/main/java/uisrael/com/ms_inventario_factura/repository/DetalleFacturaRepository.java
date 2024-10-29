package uisrael.com.ms_inventario_factura.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uisrael.com.ms_inventario_factura.entity.DetalleFactura;

import java.util.List;

@Repository
public interface DetalleFacturaRepository extends MongoRepository<DetalleFactura, String> {
    List<DetalleFactura> findDetallesFacturasByIdCabeceraFactura(String idCabeceraFactura);
}

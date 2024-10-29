package uisrael.com.ms_inventario_factura.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import uisrael.com.ms_inventario_factura.entity.CabeceraFactura;
import uisrael.com.ms_inventario_factura.entity.DetalleFactura;

import java.util.Date;
import java.util.List;

@Repository
public interface CabeceraFacturaRepository extends MongoRepository<CabeceraFactura, String> {
    List<CabeceraFactura> findByFechaEmisionBetweenOrderByFechaEmisionDesc(Date fechaInicio, Date fechaFin);
    List<CabeceraFactura> findByFechaEmisionBetweenAndIdAgenciaOrderByFechaEmisionDesc(Date fechaInicio, Date fechaFin, Long idAgencia);
}

package uisrael.com.ms_cliente_producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uisrael.com.ms_cliente_producto.entity.Producto;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    @Query("select p from Producto p where p.codigoPrincipal ilike %?1% or p.nombre ilike %?1% order by idProducto desc")
    public List<Producto> findByCodigoPrincipalOrNombre(String term);
}

package uisrael.com.ms_cliente_producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uisrael.com.ms_cliente_producto.entity.Pedidos;

@Repository
public interface PedidoRepository extends JpaRepository<Pedidos, Long> {

}

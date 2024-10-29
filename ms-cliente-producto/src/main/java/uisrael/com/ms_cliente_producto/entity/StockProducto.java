package uisrael.com.ms_cliente_producto.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="stock_producto")
public class StockProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_stock_producto")
    private Long idStockProducto;
    @Column(name="id_producto", columnDefinition = "bigint", nullable=false)
    private Long idProducto;
    @Column(name="id_agencia", columnDefinition = "bigint", nullable=false)
    private Long idAgencia;
    @Column(name="cantidad", columnDefinition = "integer", nullable=false)
    private int cantidad;

    @ManyToOne
    //@JsonManagedReference
    @JoinColumn(name="id_producto", insertable=false, updatable=false)
    private Producto producto;
}

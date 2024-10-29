package uisrael.com.ms_inventario_factura.entity;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import uisrael.com.ms_inventario_factura.model.Cliente;
import uisrael.com.ms_inventario_factura.model.Producto;
import uisrael.com.ms_inventario_factura.model.StockProducto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "detalle_inventario")
public class DetalleInventario {
    @Id
    private String idDetalleInventario;
    private String idCabeceraInventario;
    private Long idProducto;
    private int cantidad;

    @Transient
    private Producto producto = new Producto();
}

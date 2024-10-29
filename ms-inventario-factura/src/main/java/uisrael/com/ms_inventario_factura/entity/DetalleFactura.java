package uisrael.com.ms_inventario_factura.entity;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import uisrael.com.ms_inventario_factura.model.Producto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "detalle_factura")
public class DetalleFactura {
    @Id
    private String idDetalleFactura;
    private String idCabeceraFactura;
    private Long idProducto;
    private int cantidad;
    private double precio;
    private double total;

    @Transient
    private Producto producto = new Producto();
}

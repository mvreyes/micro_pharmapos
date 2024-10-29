package uisrael.com.ms_inventario_factura.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StockProducto {
    private Long idStockProducto;
    private Long idProducto;
    private Long idAgencia;
    private int cantidad;
}

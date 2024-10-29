package uisrael.com.ms_inventario_factura.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Producto {
    private Long idProducto;
    private String codigoPrincipal;
    private String nombre;
    private String categoria;
    private double precio;
    private String observacion;
    private int estado;
}

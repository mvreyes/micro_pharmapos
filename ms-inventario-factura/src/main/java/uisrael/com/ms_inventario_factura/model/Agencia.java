package uisrael.com.ms_inventario_factura.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Agencia {
    private Long idAgencia;
    private String nombre;
    private String direccion;
    private int estado;
}

package uisrael.com.ms_inventario_factura.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cliente {
    private Long idCliente;
    private String nombre;
    private String apellido;
    private String cedulaRuc;
    private String email;
    private String direccion;
    private String telefono;
    private int estado;
}

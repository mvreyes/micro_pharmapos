package uisrael.com.ms_inventario_factura.entity;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import uisrael.com.ms_inventario_factura.model.Agencia;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "cabecera_inventario")
public class CabeceraInventario {
    @Id
    private String idCabeceraInventario;
    private Long idAgencia;
    private Date fechaInventario;
    private String numeroInventario;
    private String estado = "P";

    @Transient
    private Agencia agencia = new Agencia();
    @Transient
    private List<DetalleInventario> detallesInventarios;
}

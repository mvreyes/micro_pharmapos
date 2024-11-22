package uisrael.com.ms_cliente_producto.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cliente")
    private Long idCliente;
    @Column(name="nombre", columnDefinition = "varchar(100)", nullable=false)
    private String nombre;
    @Column(name="apellido", columnDefinition = "varchar(100)", nullable=true)
    private String apellido;
    @Column(name="cedula_ruc", columnDefinition = "varchar(15)", nullable=false)
    private String cedulaRuc;
    @Column(name="email", columnDefinition = "varchar(100)", nullable=false)
    private String email;
    @Column(name="direccion", columnDefinition = "varchar(200)", nullable=true)
    private String direccion;
    @Column(name="telefono", columnDefinition = "varchar(15)", nullable=true)
    private String telefono;
    @Column(name="estado", columnDefinition = "integer default 1", nullable=false)
    private int estado = 1;
}

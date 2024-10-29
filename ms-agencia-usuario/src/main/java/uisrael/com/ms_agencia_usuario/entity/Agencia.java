package uisrael.com.ms_agencia_usuario.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="agencia")
public class Agencia {
    @Id
    @Column(name="id_agencia")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgencia;
    @Column(name="nombre", columnDefinition = "varchar(200)", nullable=false)
    private String nombre;
    @Column(name="direccion", columnDefinition = "varchar(200)", nullable=false)
    private String direccion;
    @Column(name="estado", columnDefinition = "integer default 1", nullable=false)
    private int estado = 1;

    //@OneToMany(mappedBy = "agencia", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JsonBackReference
    //private List<CabeceraFactura> cabecerasFacturas;

    //@OneToMany(mappedBy = "agencia", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JsonBackReference
    //private List<CabeceraInventario> cabecerasInventarios;

    //@OneToMany(mappedBy = "agencia", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JsonBackReference
    //private List<StockProducto> stocksProductos;

    @OneToMany
    //@JsonBackReference
    private List<AgenciaUsuario> agenciasUsuarios;
}

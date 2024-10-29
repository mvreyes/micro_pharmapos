package uisrael.com.ms_cliente_producto.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_producto")
    private Long idProducto;
    @Column(name="codigo_principal", columnDefinition = "varchar(20)", nullable=false)
    private String codigoPrincipal;
    @Column(name="nombre", columnDefinition = "varchar(200)", nullable=false)
    private String nombre;
    @Column(name="categoria", columnDefinition = "varchar(50)", nullable=true)
    private String categoria;
    @Column(name="precio", columnDefinition = "numeric(16, 4)", nullable=false)
    private double precio;
    @Column(name="observacion", columnDefinition = "varchar(500)", nullable=true)
    private String observacion;
    @Column(name="estado", columnDefinition = "integer default 1", nullable=false)
    private int estado = 1;

    @OneToMany
    //@JsonBackReference
    private List<StockProducto> stocksProductos;
}

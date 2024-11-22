package uisrael.com.ms_cliente_producto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "Pedido")
public class Pedidos {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_pedido")
	private Long idPedido;
	@Column(name="Num_pedido", columnDefinition = "integer default 0", nullable=false)
    private String NumPedido;
	@Column(name="Imagen", columnDefinition = "varchar(100)", nullable=false)
    private String Imagen;
	@Column(name="estado", columnDefinition = "integer default 0", nullable=false)
    private int estado = 0;
}

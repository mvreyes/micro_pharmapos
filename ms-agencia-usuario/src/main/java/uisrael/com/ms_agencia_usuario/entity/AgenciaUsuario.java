package uisrael.com.ms_agencia_usuario.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="agencia_usuario")
public class AgenciaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_agencia_usuario")
    private Long idAgenciaUsuario;
    @Column(name="id_agencia", columnDefinition = "bigint", nullable=false)
    private Long idAgencia;
    @Column(name="id_usuario", columnDefinition = "bigint", nullable=false)
    private Long idUsuario;
    @Column(name="estado", columnDefinition = "integer default 1", nullable=false)
    private int estado = 1;
    @Column(name="seleccionada", columnDefinition = "varchar(4) default 'N'", nullable=true)
    private String seleccionada = "N";

    @ManyToOne
    //@JsonManagedReference
    @JoinColumn(name="id_usuario", insertable=false, updatable=false)
    private Usuario usuario;

    @ManyToOne
    //@JsonManagedReference
    @JoinColumn(name="id_agencia", insertable=false, updatable=false)
    private Agencia agencia;
}

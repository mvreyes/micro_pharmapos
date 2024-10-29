package uisrael.com.ms_agencia_usuario.entity;

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
@Table(name="usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario")
    private Long idUsuario;
    @Column(name="nombre", columnDefinition = "varchar(200)", nullable=false)
    private String nombre;
    @Column(name="apellido", columnDefinition = "varchar(200)", nullable=false)
    private String apellido;
    @Column(name="rol", columnDefinition = "varchar(20)", nullable=false)
    private String rol;
    @Column(name="email", columnDefinition = "varchar(100)", nullable=false)
    private String email;
    @Column(name="password", columnDefinition = "text", nullable=false)
    private String password;
    @Column(name="estado", columnDefinition = "integer default 1", nullable=false)
    private int estado = 1;

    @OneToMany
    //@JsonBackReference
    private List<AgenciaUsuario> agenciasUsuarios;
}

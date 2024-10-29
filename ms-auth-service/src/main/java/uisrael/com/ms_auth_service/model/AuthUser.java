package uisrael.com.ms_auth_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthUser {
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String rol;
    private String email;
    private String password;
    private int estado = 1;
}

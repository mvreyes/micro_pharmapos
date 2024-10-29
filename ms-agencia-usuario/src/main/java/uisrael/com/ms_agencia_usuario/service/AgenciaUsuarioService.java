package uisrael.com.ms_agencia_usuario.service;

import uisrael.com.ms_agencia_usuario.entity.AgenciaUsuario;

import java.util.List;

public interface AgenciaUsuarioService {
    List<AgenciaUsuario> listaAgenciasUsuarios();
    List<AgenciaUsuario> listaAgenciasUsuario(Long id);
    AgenciaUsuario crearAgenciaUsuario(AgenciaUsuario agenciaUsuario);
    AgenciaUsuario estadoAgenciaUsuario(Long id);
    AgenciaUsuario agregarUsuarioAgencia(Long idUsuario, Long idAgencia, AgenciaUsuario agenciaUsuario);
    AgenciaUsuario seleccionarUsuarioAgencia(Long idUsuario, Long idAgencia);
    AgenciaUsuario inactivarSeleccionAgencia(Long id);
    AgenciaUsuario buscarAgenciaSeleccionada(Long idUsuario);
}

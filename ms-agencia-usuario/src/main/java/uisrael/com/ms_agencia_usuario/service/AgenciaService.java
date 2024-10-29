package uisrael.com.ms_agencia_usuario.service;

import uisrael.com.ms_agencia_usuario.entity.Agencia;

import java.util.List;

public interface AgenciaService {
    List<Agencia> listaAgencias();
    Agencia obtenerPorId(Long id);
    Agencia crearAgencia(Agencia agencia);
    Agencia actualizarAgencia(Long id, Agencia agencia);
    Agencia estadoAgencia(Long id);
}

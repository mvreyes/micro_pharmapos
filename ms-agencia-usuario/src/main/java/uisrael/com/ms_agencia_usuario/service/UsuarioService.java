package uisrael.com.ms_agencia_usuario.service;

import uisrael.com.ms_agencia_usuario.entity.Usuario;

import java.util.List;

public interface UsuarioService {
    List<Usuario> listaUsuarios();
    Usuario obtenerPorId(Long id);
    Usuario crearUsuario(Usuario usuario);
    Usuario actualizarUsuario(Long id, Usuario usuario);
    Usuario estadoUsuario(Long id);
    Usuario buscarUsuarioEmail(String email);
}

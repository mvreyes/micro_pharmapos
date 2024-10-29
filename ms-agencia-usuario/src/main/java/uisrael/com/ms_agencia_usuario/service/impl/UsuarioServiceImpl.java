package uisrael.com.ms_agencia_usuario.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uisrael.com.ms_agencia_usuario.entity.Usuario;
import uisrael.com.ms_agencia_usuario.repository.UsuarioRepository;
import uisrael.com.ms_agencia_usuario.service.UsuarioService;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listaUsuarios(){
        return usuarioRepository.findAll(Sort.by(Sort.Direction.DESC, "idUsuario"));
    }

    @Override
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public Usuario estadoUsuario(Long id) {
        Usuario usuariodb = usuarioRepository.findById(id).orElse(null);
        if(usuariodb != null) {
            if(usuariodb.getEstado() == 1) {
                usuariodb.setEstado(0);
            }else {
                usuariodb.setEstado(1);
            }
            return usuarioRepository.save(usuariodb);
        }
        return null;
    }

    @Override
    public Usuario buscarUsuarioEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }
}

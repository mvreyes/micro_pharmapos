package uisrael.com.ms_agencia_usuario.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uisrael.com.ms_agencia_usuario.entity.AgenciaUsuario;
import uisrael.com.ms_agencia_usuario.repository.AgenciaUsuarioRepository;
import uisrael.com.ms_agencia_usuario.service.AgenciaUsuarioService;

import java.util.List;

@Service
public class AgenciaUsuarioServiceImpl implements AgenciaUsuarioService {
    @Autowired
    private AgenciaUsuarioRepository agenciaUsuarioRepository;

    @Override
    public List<AgenciaUsuario> listaAgenciasUsuarios(){
        return agenciaUsuarioRepository.findAll(Sort.by(Sort.Direction.DESC, "idAgencia"));
    }

    @Override
    public List<AgenciaUsuario> listaAgenciasUsuario(Long idUsuario){
        return agenciaUsuarioRepository.findByIdUsuario(idUsuario);
    }

    @Override
    @Transactional
    public AgenciaUsuario crearAgenciaUsuario(AgenciaUsuario agenciaUsuario) {
        return agenciaUsuarioRepository.save(agenciaUsuario);
    }

    @Override
    @Transactional
    public AgenciaUsuario estadoAgenciaUsuario(Long id) {
        AgenciaUsuario agenciaUsuariodb = agenciaUsuarioRepository.findById(id).orElse(null);
        if(agenciaUsuariodb != null) {
            if(agenciaUsuariodb.getEstado() == 1) {
                agenciaUsuariodb.setEstado(0);
            }else {
                agenciaUsuariodb.setEstado(1);
            }
            return agenciaUsuarioRepository.save(agenciaUsuariodb);
        }
        return null;
    }

    @Override
    @Transactional
    public AgenciaUsuario agregarUsuarioAgencia(Long idUsuario, Long idAgencia, AgenciaUsuario agenciaUsuario) {
        AgenciaUsuario agenciaUsuariodb = agenciaUsuarioRepository.findByIdUsuarioAndIdAgencia(idUsuario, idAgencia);
        if(agenciaUsuariodb == null) {
            return agenciaUsuarioRepository.save(agenciaUsuario);
        }
        return null;
    }

    @Override
    @Transactional
    public AgenciaUsuario inactivarSeleccionAgencia(Long idAgenciaUsuario) {
        AgenciaUsuario agenciaUsuariodb = agenciaUsuarioRepository.findById(idAgenciaUsuario).orElse(null);
        if(agenciaUsuariodb != null) {
            agenciaUsuariodb.setSeleccionada("N");
            return agenciaUsuarioRepository.save(agenciaUsuariodb);
        }
        return null;
    }

    @Override
    @Transactional
    public AgenciaUsuario seleccionarUsuarioAgencia(Long idUsuario, Long idAgencia) {
        AgenciaUsuario agenciaUsuariodb = agenciaUsuarioRepository.findByIdUsuarioAndIdAgencia(idUsuario, idAgencia);
        if(agenciaUsuariodb != null) {
            agenciaUsuariodb.setSeleccionada("S");
            return agenciaUsuarioRepository.save(agenciaUsuariodb);
        }
        return null;
    }

    @Override
    public AgenciaUsuario buscarAgenciaSeleccionada(Long idUsuario) {
        return agenciaUsuarioRepository.findByIdUsuarioAgenciaSeleccionada(idUsuario);
    }
}

package uisrael.com.ms_agencia_usuario.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uisrael.com.ms_agencia_usuario.entity.Agencia;
import uisrael.com.ms_agencia_usuario.repository.AgenciaRepository;
import uisrael.com.ms_agencia_usuario.service.AgenciaService;

import java.util.List;

@Service
public class AgenciaServiceImpl implements AgenciaService {
    @Autowired
    private AgenciaRepository agenciaRepository;

    @Override
    public List<Agencia> listaAgencias(){
        return agenciaRepository.findAll(Sort.by(Sort.Direction.DESC, "idAgencia"));
    }

    @Override
    public Agencia obtenerPorId(Long id) {
        return agenciaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Agencia crearAgencia(Agencia agencia) {
        return agenciaRepository.save(agencia);
    }

    @Override
    @Transactional
    public Agencia actualizarAgencia(Long id, Agencia agencia) {
        Agencia agenciadb = agenciaRepository.findById(id).orElse(null);
        if(agenciadb != null) {
            agenciadb.setNombre(agencia.getNombre());
            agenciadb.setDireccion(agencia.getDireccion());
            return agenciaRepository.save(agenciadb);
        }
        return null;
    }

    @Override
    @Transactional
    public Agencia estadoAgencia(Long id) {
        Agencia agenciadb = agenciaRepository.findById(id).orElse(null);
        if(agenciadb != null) {
            if(agenciadb.getEstado() == 1) {
                agenciadb.setEstado(0);
            }else {
                agenciadb.setEstado(1);
            }
            return agenciaRepository.save(agenciadb);
        }
        return null;
    }
}

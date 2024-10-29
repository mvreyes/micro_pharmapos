package uisrael.com.ms_agencia_usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uisrael.com.ms_agencia_usuario.entity.AgenciaUsuario;

import java.util.List;

@Repository
public interface AgenciaUsuarioRepository  extends JpaRepository<AgenciaUsuario, Long> {
    List<AgenciaUsuario> findByIdUsuario(Long idUsuario);

    AgenciaUsuario findByIdUsuarioAndIdAgencia(Long idUsuario, Long idAgencia);

    @Query("select au from AgenciaUsuario au where au.idUsuario = ?1 and seleccionada = 'S'")
    public AgenciaUsuario findByIdUsuarioAgenciaSeleccionada(Long idUsuario);
}
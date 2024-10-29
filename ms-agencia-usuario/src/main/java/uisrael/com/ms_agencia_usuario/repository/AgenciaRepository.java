package uisrael.com.ms_agencia_usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uisrael.com.ms_agencia_usuario.entity.Agencia;

@Repository
public interface AgenciaRepository extends JpaRepository<Agencia, Long> {
}

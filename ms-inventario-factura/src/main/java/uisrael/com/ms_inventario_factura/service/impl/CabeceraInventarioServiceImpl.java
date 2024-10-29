package uisrael.com.ms_inventario_factura.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import uisrael.com.ms_inventario_factura.entity.CabeceraInventario;
import uisrael.com.ms_inventario_factura.model.Agencia;
import uisrael.com.ms_inventario_factura.repository.CabeceraInventarioRepository;
import uisrael.com.ms_inventario_factura.repository.DetalleInventarioRepository;
import uisrael.com.ms_inventario_factura.service.CabeceraInventarioService;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class CabeceraInventarioServiceImpl implements CabeceraInventarioService {
    @Autowired
    private CabeceraInventarioRepository cabeceraInventarioRepository;
    @Autowired
    private DetalleInventarioRepository detalleInventarioRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<CabeceraInventario> listaCabecerasInventarios(){
        List<CabeceraInventario> cabecerasInventarios = cabeceraInventarioRepository.findAll(Sort.by(Sort.Direction.DESC, "numeroInventario"));
        for (CabeceraInventario cabeceraInventario : cabecerasInventarios) {
            Agencia agencia = restTemplate.getForObject("http://localhost:8001/agencias/"+cabeceraInventario.getIdAgencia(), Agencia.class);
            cabeceraInventario.setAgencia(agencia);
        }
        return cabecerasInventarios;
    }

    @Override
    public CabeceraInventario obtenerPorId(String idCabeceraInventario) {
        CabeceraInventario cabeceraInventario = cabeceraInventarioRepository.findById(idCabeceraInventario).orElse(null);
        if (cabeceraInventario != null){
            Agencia agencia = restTemplate.getForObject("http://localhost:8001/agencias/"+cabeceraInventario.getIdAgencia(), Agencia.class);
            cabeceraInventario.setAgencia(agencia);
            cabeceraInventario.setDetallesInventarios(detalleInventarioRepository.findDetallesInventariosByIdCabeceraInventario(idCabeceraInventario));
        }
        return cabeceraInventario;
    }

    @Override
    @Transactional
    public CabeceraInventario crearCabeceraInventario(CabeceraInventario cabeceraInventario) {
        String randomCabeceraInventario = generarRandom();
        cabeceraInventario.setIdCabeceraInventario(randomCabeceraInventario);
        return cabeceraInventarioRepository.save(cabeceraInventario);
    }

    @Override
    public Long contarInventarios() {
        return cabeceraInventarioRepository.count();
    }

    public String generarRandom() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(15);

        for (int i = 0; i < 15; i++) {
            int index = random.nextInt(caracteres.length());
            sb.append(caracteres.charAt(index));
        }

        return sb.toString();
    }
}

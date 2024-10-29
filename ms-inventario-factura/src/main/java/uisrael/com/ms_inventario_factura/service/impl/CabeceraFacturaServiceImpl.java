package uisrael.com.ms_inventario_factura.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import uisrael.com.ms_inventario_factura.entity.CabeceraFactura;
import uisrael.com.ms_inventario_factura.model.Agencia;
import uisrael.com.ms_inventario_factura.model.Cliente;
import uisrael.com.ms_inventario_factura.repository.CabeceraFacturaRepository;
import uisrael.com.ms_inventario_factura.repository.DetalleFacturaRepository;
import uisrael.com.ms_inventario_factura.service.CabeceraFacturaService;
import uisrael.com.ms_inventario_factura.service.DetalleFacturaService;

import java.util.*;
import java.security.SecureRandom;

@Service
public class CabeceraFacturaServiceImpl implements CabeceraFacturaService {
    @Autowired
    private CabeceraFacturaRepository cabeceraFacturaRepository;
    @Autowired
    private DetalleFacturaRepository detalleFacturaRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<CabeceraFactura> listaCabecerasFacturas(){
        List<CabeceraFactura> cabecerasFacturas = cabeceraFacturaRepository.findAll(Sort.by(Sort.Direction.DESC, "numeroFactura"));
        for (CabeceraFactura cabeceraFactura : cabecerasFacturas) {
            Cliente cliente = restTemplate.getForObject("http://localhost:8002/clientes/"+cabeceraFactura.getIdCliente(), Cliente.class);
            cabeceraFactura.setCliente(cliente);
            Agencia agencia = restTemplate.getForObject("http://localhost:8001/agencias/"+cabeceraFactura.getIdAgencia(), Agencia.class);
            cabeceraFactura.setAgencia(agencia);
        }
        return cabecerasFacturas;
    }

    @Override
    public CabeceraFactura obtenerPorId(String idCabeceraFactura) {
        CabeceraFactura cabeceraFactura = cabeceraFacturaRepository.findById(idCabeceraFactura).orElse(null);
        if (cabeceraFactura != null){
            Cliente cliente = restTemplate.getForObject("http://localhost:8002/clientes/"+cabeceraFactura.getIdCliente(), Cliente.class);
            cabeceraFactura.setCliente(cliente);
            Agencia agencia = restTemplate.getForObject("http://localhost:8001/agencias/"+cabeceraFactura.getIdAgencia(), Agencia.class);
            cabeceraFactura.setAgencia(agencia);
            cabeceraFactura.setDetallesFacturas(detalleFacturaRepository.findDetallesFacturasByIdCabeceraFactura(idCabeceraFactura));
        }
        return cabeceraFactura;
    }

    @Override
    @Transactional
    public CabeceraFactura crearCabeceraFactura(CabeceraFactura cabeceraFactura) {
        String randomCabeceraFactura = generarRandom();
        cabeceraFactura.setIdCabeceraFactura(randomCabeceraFactura);
        return cabeceraFacturaRepository.save(cabeceraFactura);
    }

    @Override
    public Long contarFacturas() {
        return cabeceraFacturaRepository.count();
    }

    @Override
    public List<CabeceraFactura> listaVentaDetallado(Date fechaDesde, Date fechaHasta, Long idAgencia){
        List<CabeceraFactura> cabecerasFacturas = new ArrayList<>();
        if(idAgencia == null) {
            cabecerasFacturas = cabeceraFacturaRepository.findByFechaEmisionBetweenOrderByFechaEmisionDesc(fechaDesde, fechaHasta);
        }else {
            cabecerasFacturas = cabeceraFacturaRepository.findByFechaEmisionBetweenAndIdAgenciaOrderByFechaEmisionDesc(fechaDesde, fechaHasta, idAgencia);
        }
        for (CabeceraFactura cabeceraFactura : cabecerasFacturas) {
            Cliente cliente = restTemplate.getForObject("http://localhost:8002/clientes/"+cabeceraFactura.getIdCliente(), Cliente.class);
            cabeceraFactura.setCliente(cliente);
            Agencia agencia = restTemplate.getForObject("http://localhost:8001/agencias/"+cabeceraFactura.getIdAgencia(), Agencia.class);
            cabeceraFactura.setAgencia(agencia);
        }

        return cabecerasFacturas;
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

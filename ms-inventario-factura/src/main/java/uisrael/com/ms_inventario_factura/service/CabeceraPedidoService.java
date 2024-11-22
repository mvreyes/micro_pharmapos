package uisrael.com.ms_inventario_factura.service;

import uisrael.com.ms_inventario_factura.entity.CabeceraPedido;

import java.util.Date;
import java.util.List;

public interface CabeceraPedidoService {
    List<CabeceraPedido> listaCabecerasPedidos();
    CabeceraPedido obtenerPorId(String idCabeceraPedido);
    CabeceraPedido crearCabeceraPedido(CabeceraPedido cabeceraPedido);
    Long contarPedidos();
}
